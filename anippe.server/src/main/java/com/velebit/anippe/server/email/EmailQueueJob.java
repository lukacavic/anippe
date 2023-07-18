package com.velebit.anippe.server.email;

import com.velebit.anippe.server.utilities.CryptHelper;
import com.velebit.anippe.shared.email.Email;
import com.velebit.anippe.shared.organisations.EmailSettings;
import org.eclipse.scout.rt.mail.*;
import org.eclipse.scout.rt.mail.smtp.SmtpHelper;
import org.eclipse.scout.rt.mail.smtp.SmtpServerConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailQueueJob implements IPlatformListener {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void stateChanged(PlatformEvent event) {
        if (event.getState() == State.BeanManagerValid) {

            Jobs.schedule(
                this::sendQueueEmails,
                Jobs.newInput()
                    .withName("SendQueueEmails")
                    .withRunContext(RunContexts.empty())
                    .withExecutionTrigger(Jobs.newExecutionTrigger().withStartIn(1, TimeUnit.MINUTES).withSchedule(FixedDelayScheduleBuilder.repeatForever(5, TimeUnit.MINUTES)))
                    .withExceptionHandling(new ExceptionHandler() {
                        @Override
                        public void handle(Throwable t) {
                            LOG.error("Exception in email queue job.", t);
                        }
                    }, true));
        }
    }

    private SmtpServerConfig getSmtpOrganisationSettings(List<EmailSettings> settings, Integer organisationId) {
        EmailSettings emailSettings = settings.stream().filter(v -> v.getOrganisationId().equals(organisationId)).findFirst().orElse(null);

        if (emailSettings == null)
            return null;

        return BEANS.get(SmtpServerConfig.class)
            .withHost(emailSettings.getSmtpServer())
            .withPort(emailSettings.getSmtpPort())
            .withUsername(emailSettings.getSmtpUsername())
            .withPassword(CryptHelper.decrypt(emailSettings.getSmtpPassword()))
            .withUseAuthentication(true)
            .withUseSmtps(emailSettings.getSmtpProtocol().equals(1L))
            .withUseStartTls(emailSettings.getSmtpProtocol().equals(2L));

    }

    private void sendQueueEmails() {
        List<Email> emailsToSend = BEANS.get(EmailDao.class).findEmailsToSend();

        LOG.info("Starting EmailQueueJob. Remaining emails to send: {}", emailsToSend.size());

        List<EmailSettings> emailSettings = BEANS.get(EmailDao.class).findAllOrganisationsEmailSettings();

        for (Email email : emailsToSend) {

            SmtpServerConfig serverConfig = getSmtpOrganisationSettings(emailSettings, email.getOrganisationId());

            if (serverConfig == null)
                continue;

            MailMessage message = prepareEmail(email);
            CharsetSafeMimeMessage mimmeMessage = BEANS.get(MailHelper.class).createMimeMessage(message);

            try {
                BEANS.get(SmtpHelper.class).sendMessage(serverConfig, mimmeMessage);
                BEANS.get(EmailDao.class).processSentMail(email);
            } catch (ProcessingException e) {
                BEANS.get(EmailDao.class).markAsNotSent(email, e.getCause().getLocalizedMessage());
            }
        }

    }

    private MailMessage prepareEmail(Email email) {

        if (email == null)
            return null;

        return BEANS.get(MailMessage.class)
            .withSender(BEANS.get(MailParticipant.class).withName(email.getSenderName()).withEmail(email.getSenderEmail()))
            .addToRecipients(fetchRecipients(email))
            .addCcRecipients(fetchCCRecipients(email))
            .addBccRecipients(fetchBCCRecipients(email))
            .withAttachments(fetchAttachments(email))
            .withSubject(email.getSubject())
            .withBodyHtml(email.getContent());
    }

    private Collection<MailAttachment> fetchAttachments(Email email) {
        List<MailAttachment> attachments = new ArrayList<>();

        if (email.getAttachments() == null)
            return attachments;

        for (BinaryResource attachment : email.getAttachments()) {
            attachments.add(new MailAttachment(attachment));
        }

        return attachments;
    }

    private Collection<MailParticipant> fetchRecipients(Email email) {
        List<MailParticipant> receivers = new ArrayList<MailParticipant>();

        if (email.formattedReceivers() == null)
            return null;

        for (String recipient : email.formattedReceivers()) {
            receivers.add(BEANS.get(MailParticipant.class).withName(recipient).withEmail(recipient));
        }

        return receivers;
    }

    private Collection<MailParticipant> fetchCCRecipients(Email email) {
        List<MailParticipant> receivers = new ArrayList<MailParticipant>();

        if (email.formattedCCReceivers() == null)
            return null;

        for (String recipient : email.formattedCCReceivers()) {
            receivers.add(BEANS.get(MailParticipant.class).withName(recipient).withEmail(recipient));
        }

        return receivers;
    }

    private Collection<MailParticipant> fetchBCCRecipients(Email email) {
        List<MailParticipant> receivers = new ArrayList<MailParticipant>();

        if (email.formattedBCCReceivers() == null)
            return null;

        for (String recipient : email.formattedBCCReceivers()) {
            receivers.add(BEANS.get(MailParticipant.class).withName(recipient).withEmail(recipient));
        }

        return receivers;
    }

}
