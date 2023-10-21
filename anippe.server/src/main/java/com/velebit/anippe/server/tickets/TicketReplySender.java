package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.utilities.CryptHelper;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.email.Email;
import com.velebit.anippe.shared.organisations.EmailSettings;
import org.eclipse.scout.rt.mail.*;
import org.eclipse.scout.rt.mail.smtp.SmtpHelper;
import org.eclipse.scout.rt.mail.smtp.SmtpServerConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Bean
public class TicketReplySender {

    private String subject;
    private String body;
    private String recipient;
    private String ccRecipient;
    private List<BinaryResource> attachments = CollectionUtility.emptyArrayList();

    public List<BinaryResource> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<BinaryResource> attachments) {
        this.attachments = attachments;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCcRecipient() {
        return ccRecipient;
    }

    public void setCcRecipient(String ccRecipient) {
        this.ccRecipient = ccRecipient;
    }

    private Collection<MailAttachment> fetchAttachments() {
        List<MailAttachment> mailAttachments = new ArrayList<>();

        if (CollectionUtility.isEmpty(attachments))
            return mailAttachments;

        for (BinaryResource attachment : attachments) {
            mailAttachments.add(new MailAttachment(attachment));
        }

        return mailAttachments;
    }


    private MailMessage prepareEmail() {
        User user = ServerSession.get().getCurrentUser();

        return BEANS.get(MailMessage.class)
                .withSender(BEANS.get(MailParticipant.class).withName(user.getFullName()).withEmail(user.getEmail()))
                .addToRecipients(CollectionUtility.arrayList(BEANS.get(MailParticipant.class).withName(recipient).withEmail(recipient)))
                .addCcRecipients(CollectionUtility.arrayList(BEANS.get(MailParticipant.class).withName(ccRecipient).withEmail(ccRecipient)))
                .withAttachments(fetchAttachments())
                .withSubject(subject)
                .withBodyHtml(body);
    }

    public void send() throws ProcessingException {
        SmtpServerConfig serverConfig = getSmtpOrganisationSettings();

        if (serverConfig == null)
            return;

        MailMessage message = prepareEmail();
        CharsetSafeMimeMessage mimmeMessage = BEANS.get(MailHelper.class).createMimeMessage(message);

        BEANS.get(SmtpHelper.class).sendMessage(serverConfig, mimmeMessage);
    }

    public EmailSettings findEmailSmtpSettings() {
        EmailSettings settings = new EmailSettings();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT os.organisation_id, ");
        varname1.append("       os.email_smtp_server, ");
        varname1.append("       os.email_smtp_username, ");
        varname1.append("       os.email_smtp_port, ");
        varname1.append("       os.email_smtp_password, ");
        varname1.append("       os.email_smtp_protocol, ");
        varname1.append("       os.email_smtp_email ");
        varname1.append("FROM   organisation_settings os ");
        varname1.append("WHERE  os.id = :organisationId ");
        varname1.append("INTO ");
        varname1.append(":{rows.organisationId}, :{rows.smtpServer}, :{rows.smtpUsername}, :{rows.smtpPort}, :{rows.smtpPassword}, :{rows.smtpProtocol}, :{rows.smtpEmail} ");
        SQL.selectInto(varname1.toString(), new NVPair("rows", settings), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        try {
            settings.setSmtpPassword(CryptHelper.decrypt(settings.getSmtpPassword()));
        } catch (ProcessingException ignored) {
        }
        return settings.getSmtpUsername() != null ? settings : null;
    }

    private SmtpServerConfig getSmtpOrganisationSettings() {
        EmailSettings emailSettings = findEmailSmtpSettings();

        if (emailSettings == null)
            return null;

        return BEANS.get(SmtpServerConfig.class)
                .withHost(emailSettings.getSmtpServer())
                .withPort(emailSettings.getSmtpPort())
                .withUsername(emailSettings.getSmtpUsername())
                .withPassword(emailSettings.getSmtpPassword())
                .withUseAuthentication(true)
                .withUseSmtps(false)
                .withUseStartTls(false);

    }
}
