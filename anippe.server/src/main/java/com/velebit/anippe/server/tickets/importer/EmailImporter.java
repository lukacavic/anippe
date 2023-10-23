package com.velebit.anippe.server.tickets.importer;

import com.velebit.anippe.server.tickets.TicketDao;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.contacts.IContactService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketDepartment;
import org.apache.commons.mail.util.MimeMessageParser;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Bean
public class EmailImporter {

    Logger LOG = LoggerFactory.getLogger(getClass());

    //Ticket subject will contain [CS10030] which is ticket code.
    private String findTicketCode(String subject) {
        String[] subjectArray = subject.split("[\\[\\]]");

        if (1 < subjectArray.length) {
            return subjectArray[1];
        }

        return null;
    }

    public boolean run(Message message, TicketDepartment ticketDepartment) throws EmailImporterException {
        try {
            String subject = message.getSubject();

            List<BinaryResource> attachments = fetchMessageAttachments(message);

            String ticketCode = findTicketCode(subject);

            //If ticket code is not found, create new ticket.
            if (StringUtility.isNullOrEmpty(ticketCode)) {
                processCreateNewTicket(message, ticketDepartment, attachments);

                return true;
            }

            //If ticket code is found, find that ticket and add reply.
            if (!StringUtility.isNullOrEmpty(ticketCode)) {
                processAddTicketReply(ticketCode, message, ticketDepartment, attachments);

                return true;
            }

            return false;

        } catch (MessagingException | IOException e) {
            throw new EmailImporterException(e.getMessage());
        }

    }

    private String getEmailFromMessage(Message message) {
        try {
            InternetAddress sender = (InternetAddress) message.getFrom()[0];

            return sender.getAddress();
        } catch (MessagingException e) {
            LOG.error("Error getting email from IMAP. {}", e.getMessage());

            return null;
        }
    }

    private String getContentFromEmailMessage(Message message) {
        try {
            MimeMessageParser parse = (new MimeMessageParser((MimeMessage) message)).parse();
            if (parse.hasHtmlContent()) {
                Document document = Jsoup.parse(parse.getHtmlContent());
                return document.body().html();
            }
            return parse.getPlainContent();

        } catch (Exception e) {
            return null;
        }
    }


    private void processCreateNewTicket(Message message, TicketDepartment ticketDepartment, List<BinaryResource> attachments) throws MessagingException, IOException {
        String subject = message.getSubject();
        String content = getContentFromEmailMessage(message);
        String sender = getEmailFromMessage(message);

        Contact contact = findOrCreateContact(sender, ticketDepartment);

        //Insert ticket to database
        Integer ticketId = BEANS.get(EmailImapImportDao.class).insertTicket(subject, content, contact);

        addReply(ticketId, content, ticketDepartment, contact, attachments);
    }

    private void processAddTicketReply(String ticketCode, Message message, TicketDepartment ticketDepartment, List<BinaryResource> attachments) throws MessagingException, IOException {
        String content = getContentFromEmailMessage(message);

        Ticket ticket = BEANS.get(TicketDao.class).findTicketByCode(ticketCode, ticketDepartment.getId());
        if (ticket == null || ticket.getId() == null) return;

        String email = getEmailFromMessage(message);
        Contact contact = findOrCreateContact(email, ticketDepartment);

        //Add reply to ticket
        Integer replyId = BEANS.get(EmailImapImportDao.class).insertTicketReply(ticketDepartment, ticket.getId(), contact, content);

        saveAttachments(attachments, replyId);
    }

    private void saveAttachments(List<BinaryResource> attachments, Integer ticketReplyId) {
        if (!CollectionUtility.isEmpty(attachments)) {
            for (BinaryResource binaryResource : attachments) {
                Attachment attachment = new Attachment();
                attachment.setAttachment((binaryResource).getContent());
                attachment.setCreatedAt(new Date());
                attachment.setFileName(binaryResource.getFilename());
                attachment.setFileExtension(binaryResource.getContentType());
                attachment.setFileSize(binaryResource.getContentLength());
                attachment.setRelatedId(ticketReplyId);
                attachment.setRelatedTypeId(Constants.Related.TICKET_REPLY);
                attachment.setName(binaryResource.getFilename());

                BEANS.get(IAttachmentService.class).saveAttachment(attachment, null, "test");
            }
        }
    }

    private void addReply(Integer ticketId, String content, TicketDepartment ticketDepartment, Contact contact, List<BinaryResource> attachments) {
        Integer ticketReplyId = BEANS.get(EmailImapImportDao.class).insertTicketReply(ticketDepartment, ticketId, contact, content);

        BEANS.get(EmailImapImportDao.class).updateTicketLastReply(ticketId);

        saveAttachments(attachments, ticketReplyId);
    }

    private Contact findOrCreateContact(String email, TicketDepartment ticketDepartment) {
        Contact contact = BEANS.get(IContactService.class).findContactByEmail(email, ticketDepartment.getOrganisationId());

        if (contact == null) {
            return BEANS.get(EmailImapImportDao.class).createContact(email, ticketDepartment.getOrganisationId());
        }

        return contact;
    }

    public List<BinaryResource> fetchMessageAttachments(Message message) throws IOException, MessagingException {
        List<BinaryResource> attachments = CollectionUtility.emptyArrayList();
        try {

            MimeMessageParser parse = (new MimeMessageParser((MimeMessage) message)).parse();
            List<DataSource> attachmentList = parse.getAttachmentList();

            if (CollectionUtility.isEmpty(attachmentList)) return attachments;

            for (DataSource list : attachmentList) {
                byte[] content = IOUtility.readBytes(list.getInputStream());

                attachments.add(new BinaryResource(list.getName(), content));
            }

        } catch (Exception e) {
            return attachments;
        }

        return attachments;
    }
}
