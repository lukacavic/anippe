package com.velebit.anippe.server.email;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.email.EmailFormData;
import com.velebit.anippe.shared.email.IEmailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.eclipse.scout.rt.mail.MailHelper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.resource.MimeType;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EmailService extends AbstractService implements IEmailService {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public EmailFormData prepareCreate(EmailFormData formData) {
        if (!CollectionUtility.isEmpty(formData.getAttachments())) {

            List<EmailFormData.AttachmentsTable.AttachmentsTableRowData> rows = new ArrayList<EmailFormData.AttachmentsTable.AttachmentsTableRowData>();

            for (BinaryResource br : formData.getAttachments()) {
                EmailFormData.AttachmentsTable.AttachmentsTableRowData rowData = new EmailFormData.AttachmentsTable.AttachmentsTableRowData();
                rowData.setExtension(MimeType.convertToMimeType(br.getContentType()).getFileExtension());
                rowData.setName(br.getFilename());
                rowData.setAttachment(br);
                rowData.setSize(br.getContentLength());

                rows.add(rowData);
            }

            formData.getAttachmentsTable().setRows(rows.toArray(new EmailFormData.AttachmentsTable.AttachmentsTableRowData[rows.size()]));
        }

        return formData;
    }

    @Override
    public EmailFormData create(EmailFormData formData) {
        if (StringUtility.isNullOrEmpty(formData.getMessage().getValue())) {
            throw new VetoException(TEXTS.get("EmailContentIsEmpty")).withTitle(TEXTS.get("Error")).withSeverity(IStatus.ERROR);
        }

        queueEmailForSending(formData);

        return formData;
    }

    private void queueEmailForSending(EmailFormData formData) {
        IntegerHolder emailId = new IntegerHolder();

        String organisationName = ServerSession.get().getCurrentOrganisation().getName();

        // Filter to exclude null
        CollectionUtils.filter(formData.getReceiver().getValue(), PredicateUtils.notNullPredicate());
        CollectionUtils.filter(formData.getBCC().getValue(), PredicateUtils.notNullPredicate());
        CollectionUtils.filter(formData.getCC().getValue(), PredicateUtils.notNullPredicate());

        String receivers = String.join(",", formData.getReceiver().getValue());
        String bccReceivers = formData.getBCC().getValue() != null ? String.join(",", formData.getBCC().getValue()) : null;
        String ccReceivers = formData.getCC().getValue() != null ? String.join(",", formData.getCC().getValue()) : null;

        if (formData.getSubject().getValue() != null) {
            formData.getSubject().setValue(StringUtility.join(" - ", organisationName, formData.getSubject().getValue()));
        }

        // TODO: Extract to EmailDao.insert
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO emails ");
        varname1.append("            (created_at, ");
        varname1.append("             subject, ");
        varname1.append("             message, ");
        varname1.append("             status_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             user_id, ");
        varname1.append("             sender_name, ");
        varname1.append("             sender_email, ");
        varname1.append("             receivers, ");
        varname1.append("             cc_receivers, ");
        varname1.append("             bcc_receivers, ");
        varname1.append("             client_id) ");
        varname1.append("VALUES      (now(), ");
        varname1.append("             :Subject, ");
        varname1.append("             :body, ");
        varname1.append("             :statusPrepareSend, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :userId, ");
        varname1.append("             :senderName, ");
        varname1.append("             :senderEmail, ");
        varname1.append("             :receivers, ");
        varname1.append("             :ccReceivers, ");
        varname1.append("             :bccReceivers, ");
        varname1.append("             :clientId) ");
        varname1.append("RETURNING id INTO :emailId");
        SQL.selectInto(
                varname1.toString(),
                formData,
                new NVPair("emailId", emailId),
                new NVPair("body", formData.getMessage().getValue()),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("userId", ServerSession.get().getCurrentUser().getId()),
                new NVPair("senderName", ServerSession.get().getCurrentUser().getFullName()),
                new NVPair("senderEmail", formData.getSender().getValue()),
                new NVPair("receivers", receivers),
                new NVPair("ccReceivers", ccReceivers),
                new NVPair("userId", ServerSession.get().getCurrentUser().getId()),
                new NVPair("bccReceivers", bccReceivers),
                new NVPair("statusPrepareSend", Constants.EmailStatus.PREPARE_SEND));

        // Save attachments for email queue if any.
        if (!CollectionUtility.isEmpty(Arrays.asList(formData.getAttachmentsTable().getRows()))) {
            saveAttachments(formData, emailId.getValue());
        }

    }

    private void saveAttachments(EmailFormData formData, Integer emailId) {
        for (EmailFormData.AttachmentsTable.AttachmentsTableRowData row : formData.getAttachmentsTable().getRows()) {

            BinaryResource attachment = (BinaryResource) row.getAttachment();

            StringBuffer varname1 = new StringBuffer();
            varname1.append("INSERT INTO email_attachments ");
            varname1.append("            (email_id, ");
            varname1.append("             content, ");
            varname1.append("             file_name, ");
            varname1.append("             file_size, ");
            varname1.append("             file_type) ");
            varname1.append("VALUES      (:emailId, ");
            varname1.append("             :content, ");
            varname1.append("             :fileName, ");
            varname1.append("             :fileSize, ");
            varname1.append("             :fileType)");
            SQL.insert(
                    varname1.toString(),
                    formData,
                    new NVPair("content", attachment.getContent()),
                    new NVPair("fileName", attachment.getFilename()),
                    new NVPair("fileSize", attachment.getContentLength()),
                    new NVPair("fileType", attachment.getContentType()),
                    new NVPair("emailId", emailId));
        }
    }

    @Override
    public boolean isEmailValid(String email) {
        return BEANS.get(MailHelper.class).isEmailAddressValid(email);
    }

    @Override
    public boolean isEmailValid(Set<String> rawValue) {
        for (String email : rawValue) {
            if (!isEmailValid(email)) {
                return false;
            }
        }

        return true;
    }

}
