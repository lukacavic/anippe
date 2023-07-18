package com.velebit.anippe.server.settings.settings;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.utilities.CryptHelper;
import com.velebit.anippe.shared.settings.settings.EmailSettingsFormData;
import com.velebit.anippe.shared.settings.settings.IEmailSettingsService;
import org.eclipse.scout.rt.mail.CharsetSafeMimeMessage;
import org.eclipse.scout.rt.mail.MailHelper;
import org.eclipse.scout.rt.mail.MailMessage;
import org.eclipse.scout.rt.mail.MailParticipant;
import org.eclipse.scout.rt.mail.smtp.SmtpHelper;
import org.eclipse.scout.rt.mail.smtp.SmtpServerConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class EmailSettingsService extends AbstractService implements IEmailSettingsService {
    @Override
    public EmailSettingsFormData load(EmailSettingsFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT email_smtp_server, ");
        varname1.append("       email_smtp_username, ");
        varname1.append("       email_smtp_port, ");
        varname1.append("       email_smtp_password, ");
        varname1.append("       email_smtp_protocol, ");
        varname1.append("       email_smtp_email, ");
        varname1.append("       email_global_bcc, ");
        varname1.append("       email_predefined_header_footer ");
        varname1.append("FROM   organisation_settings ");
        varname1.append("WHERE  organisation_id = :organisationId ");
        varname1.append("INTO ");
        varname1.append(":SmtpServer, ");
        varname1.append(":SmtpUsername, ");
        varname1.append(":SmtpPort, ");
        varname1.append(":SmtpPassword, ");
        varname1.append(":SmtpProtocolRadioBox, ");
        varname1.append(":SmtpEmail, ");
        varname1.append(":BCCAllEmails, ");
        varname1.append(":predefinedHeaderFooterJson ");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        try {
            formData.getSmtpPassword().setValue(CryptHelper.decrypt(formData.getSmtpPassword().getValue()));
        } catch (ProcessingException ignored) {
        }

        return formData;
    }

    @Override
    public EmailSettingsFormData store(EmailSettingsFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE organisation_settings ");
        varname1.append("SET    email_smtp_server = :SmtpServer, ");
        varname1.append("       email_smtp_username = :SmtpUsername, ");
        varname1.append("       email_smtp_password = :encryptedPassword, ");
        varname1.append("       email_smtp_port = :SmtpPort, ");
        varname1.append("       email_smtp_protocol = :SmtpProtocolRadioBox, ");
        varname1.append("       email_smtp_email = :SmtpEmail, ");
        varname1.append("       email_global_bcc = :BCCAllEmails, ");
        varname1.append("       email_predefined_header_footer = :predefinedHeaderFooterJson ");
        varname1.append("WHERE  organisation_id = :organisationId");
        SQL.update(varname1.toString(), formData, new NVPair("encryptedPassword", CryptHelper.encrypt(formData.getSmtpPassword().getValue())), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return formData;
    }

    @Override
    public String sendTestEmail(EmailSettingsFormData formData) {
        // Prepare mail configuration
        SmtpServerConfig smtpServerConfig = BEANS.get(SmtpServerConfig.class)
                .withHost(formData.getSmtpServer().getValue())
                .withPort(formData.getSmtpPort().getValue())
                .withUsername(formData.getSmtpUsername().getValue())
                .withPassword(formData.getSmtpPassword().getValue())
                .withUseAuthentication(true)
                .withUseSmtps(formData.getSmtpProtocolRadioBox().getValue().equals("SSL"))
                .withUseStartTls(formData.getSmtpProtocolRadioBox().getValue().equals("TLS"));

        String appName = CONFIG.getPropertyValue(PlatformConfigProperties.ApplicationNameProperty.class);
        String organisationName = getCurrentOrganisation().getName();
        String message = "Ovo je automatska poruka poslana sa " + appName + ". This is automated message sent from " + appName;

        // Prepare message
        MailMessage mailMessage = BEANS.get(MailMessage.class)
                .withSender(BEANS.get(MailParticipant.class).withName(ServerSession.get().getCurrentUser().getFullName()).withEmail(formData.getRecipient().getValue()))
                .addToRecipient(BEANS.get(MailParticipant.class).withName(formData.getRecipient().getValue()).withEmail(formData.getRecipient().getValue()))
                .withSubject("TEST - " + organisationName)
                .withBodyPlainText(message);

        CharsetSafeMimeMessage mimeMessage = BEANS.get(MailHelper.class).createMimeMessage(mailMessage);

        // Send message
        try {
            BEANS.get(SmtpHelper.class).sendMessage(smtpServerConfig, mimeMessage);
        } catch (ProcessingException e) {
            throw new VetoException(TEXTS.get("EmailSendingError", e.getCause().getLocalizedMessage()));
        }
        return TEXTS.get("EmailSent");
    }
}
