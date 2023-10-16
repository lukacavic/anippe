package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.attachments.AbstractAttachmentsBoxData.AttachmentsTable.AttachmentsTableRowData;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.AttachmentRequest;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.LeadFormData;
import com.velebit.anippe.shared.tasks.TaskFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LeadService implements ILeadService {
    @Override
    public LeadFormData prepareCreate(LeadFormData formData) {
        return formData;
    }

    @Override
    public LeadFormData create(LeadFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO leads ");
        varname1.append("            (name, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :leadId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        saveAttachments(formData);

        return formData;
    }

    @Override
    public LeadFormData load(LeadFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       company ");
        varname1.append("FROM   leads ");
        varname1.append("WHERE  id = :leadId ");
        varname1.append("INTO   :Name, :Company");
        SQL.selectInto(varname1.toString(), formData);

        // Load attachments when loading task.
        List<AttachmentsTableRowData> attachments = fetchAttachments(formData);
        formData.getAttachmentsBox().getAttachmentsTable().setRows(attachments.toArray(new AttachmentsTableRowData[0]));

        return formData;
    }

    private List<AttachmentsTableRowData> fetchAttachments(LeadFormData formData) {
        AttachmentRequest request = new AttachmentRequest();
        request.setRelatedId(formData.getLeadId());
        request.setRelatedType(Related.LEAD);

        List<Attachment> attachments = BEANS.get(IAttachmentService.class).fetchAttachments(request);
        List<AttachmentsTableRowData> rows = new ArrayList<>();

        for (Attachment attachment : attachments) {
            AttachmentsTableRowData row = new AttachmentsTableRowData();
            row.setAttachment(attachment);
            row.setAttachmentId(attachment.getId());
            row.setFormat(attachment.getFileExtension());
            row.setSize(attachment.getFileSize());
            row.setName(attachment.getName());
            rows.add(row);
        }

        return rows;
    }

    @Override
    public LeadFormData store(LeadFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE leads ");
        varname1.append("SET name    = :Name, ");
        varname1.append("    company = :Company ");
        varname1.append("WHERE id = :leadId");
        SQL.update(varname1.toString(), formData);

        saveAttachments(formData);

        return formData;
    }

    private void saveAttachments(LeadFormData formData) {
        AttachmentsTableRowData[] rows = formData.getAttachmentsBox().getAttachmentsTable().getRows();

        for (AttachmentsTableRowData rowData : rows) {

            if (rowData.getRowState() == ITableBeanRowHolder.STATUS_INSERTED) {
                BinaryResource binaryResource = (BinaryResource) rowData.getBinaryResource();

                Attachment attachment = mapRowDataToAttachment(formData, binaryResource);

                BEANS.get(IAttachmentService.class).saveAttachment(attachment);
            }

            if (rowData.getRowState() == ITableBeanRowHolder.STATUS_DELETED) {
                List<Integer> attachmentIds = new ArrayList<>(Arrays.asList(rows)).stream().map(AttachmentsTableRowData::getAttachmentId).collect(Collectors.toList());

                BEANS.get(IAttachmentService.class).deleteAttachments(attachmentIds);
            }

        }
    }

    public Attachment mapRowDataToAttachment(LeadFormData formData, BinaryResource binaryResource) {
        Attachment attachment = new Attachment();
        attachment.setAttachment((binaryResource).getContent());
        attachment.setCreatedAt(new Date());
        attachment.setFileName(binaryResource.getFilename());
        attachment.setFileExtension(binaryResource.getContentType());
        attachment.setFileSize(binaryResource.getContentLength());
        attachment.setRelatedId(formData.getLeadId());
        attachment.setRelatedTypeId(Constants.Related.LEAD);
        attachment.setName(binaryResource.getFilename());
        return attachment;
    }
}
