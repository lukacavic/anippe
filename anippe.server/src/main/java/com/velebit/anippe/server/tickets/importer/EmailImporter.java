package com.velebit.anippe.server.tickets.importer;

import com.velebit.anippe.server.contacts.ContactDao;
import com.velebit.anippe.server.tickets.TicketDao;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketDepartment;
import com.velebit.anippe.shared.tickets.TicketRequest;
import com.velebit.anippe.shared.utilities.ArrayUtility;
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
import java.util.List;
import java.util.Map;

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

    private String getSenderNameFromMessage(Message message) {
        try {
            InternetAddress[] sender = (InternetAddress[]) message.getFrom();
            if (ArrayUtility.isValidIndex(sender, 0)) {
                if (sender[0].getPersonal() != null) return sender[0].getPersonal();

            }

            return null;

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

        //Contact informations..
        Map<String, String> contactInfo = parseSenderForContactName(message);
        Contact contact = BEANS.get(ContactDao.class).findOrCreateContactByEmail(sender, contactInfo.get("firstName"), contactInfo.get("lastName"));

        //Insert ticket to database
        TicketRequest request = new TicketRequest();
        request.setSubject(subject);
        request.setContent(content);
        request.setProjectId(ticketDepartment.getProjectId());
        request.setContactId(contact.getId());
        request.setDepartmentId(ticketDepartment.getId());
        request.setAttachments(attachments);

        BEANS.get(TicketDao.class).create(request);
    }

    private Map<String, String> parseSenderForContactName(Message message) {
        Map<String, String> map = CollectionUtility.emptyHashMap();

        String senderEmail = getEmailFromMessage(message);
        String senderName = getSenderNameFromMessage(message);

        String firstName = senderEmail;
        String lastName = "";

        if (!StringUtility.isNullOrEmpty(senderName)) {
            String[] senderArray = senderName.split(" ");

            if (ArrayUtility.isValidIndex(senderArray, 0)) {
                firstName = senderArray[0];
            }

            if (ArrayUtility.isValidIndex(senderArray, 1)) {
                lastName = senderArray[1];
            }

        }

        map.put("firstName", firstName);
        map.put("lastName", lastName);

        return map;
    }

    private void processAddTicketReply(String ticketCode, Message message, TicketDepartment ticketDepartment, List<BinaryResource> attachments) throws MessagingException, IOException {
        String content = getContentFromEmailMessage(message);

        TicketRequest request = new TicketRequest();
        request.setCode(ticketCode);
        request.setDepartmentId(ticketDepartment.getId());

        Ticket ticket = BEANS.get(TicketDao.class).find(request);

        if (ticket == null || ticket.getId() == null) return;

        String email = getEmailFromMessage(message);

        Map<String, String> contactInfo = parseSenderForContactName(message);
        Contact contact = BEANS.get(ContactDao.class).findOrCreateContactByEmail(email, contactInfo.get("firstName"), contactInfo.get("lastName"));

        //Add reply to ticket
        Integer replyId = BEANS.get(TicketDao.class).addReply(ticket.getId(), content, null, contact.getId(), attachments);

        //Update status to answered.
        BEANS.get(TicketDao.class).updateStatusAndLastReply(ticket.getId());

        BEANS.get(TicketDao.class).saveAttachmentsForTicketReply(replyId, attachments);
    }

    private List<BinaryResource> fetchMessageAttachments(Message message) throws IOException, MessagingException {
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
