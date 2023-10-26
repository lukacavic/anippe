package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.notes.NoteDao;
import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.attachments.AbstractAttachmentsBoxData.AttachmentsTable.AttachmentsTableRowData;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.AttachmentRequest;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadFormData;
import com.velebit.anippe.shared.leads.LeadFormData.ActivityLogTable.ActivityLogTableRowData;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LeadService extends AbstractService implements ILeadService {
    @Override
    public LeadFormData prepareCreate(LeadFormData formData) {
        return formData;
    }

    @Override
    public LeadFormData create(LeadFormData formData) {
        if (formData.getLeadId() != null) {
            return store(formData);
        }

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO leads ");
        varname1.append("            (name, ");
        varname1.append("             company, ");
        varname1.append("             position, ");
        varname1.append("             description, ");
        varname1.append("             address, ");
        varname1.append("             city, ");
        varname1.append("             postal_code, ");
        varname1.append("             country_id, ");
        varname1.append("             status_id, ");
        varname1.append("             source_id, ");
        varname1.append("             assigned_user_id, ");
        varname1.append("             email, ");
        varname1.append("             website, ");
        varname1.append("             phone, ");
        varname1.append("             created_at, ");
        varname1.append("             project_id, ");
        varname1.append("             last_contact_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Company, ");
        varname1.append("             :Position, ");
        varname1.append("             :Description, ");
        varname1.append("             :Address, ");
        varname1.append("             :City, ");
        varname1.append("             :PostalCode, ");
        varname1.append("             :Country, ");
        varname1.append("             :Status, ");
        varname1.append("             :Source, ");
        varname1.append("             :AssignedUser, ");
        varname1.append("             :Email, ");
        varname1.append("             :Website, ");
        varname1.append("             :Phone, ");
        varname1.append("             now(), ");
        varname1.append("             :projectId, ");
        varname1.append("             :LastContactAt, ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :leadId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        saveAttachments(formData);

        // Link temporary notes if any
        if (!formData.getNotesBox().getTemporaryNoteIds().isEmpty()) {
            BEANS.get(NoteDao.class).linkTemporaryNotesWithEntity(formData.getNotesBox().getTemporaryNoteIds(), Related.LEAD, formData.getLeadId());
        }

        return formData;
    }

    @Override
    public LeadFormData load(LeadFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       company, ");
        varname1.append("       position, ");
        varname1.append("       client_id, ");
        varname1.append("       lost, ");
        varname1.append("       description, ");
        varname1.append("       address, ");
        varname1.append("       city, ");
        varname1.append("       postal_code, ");
        varname1.append("       country_id, ");
        varname1.append("       status_id, ");
        varname1.append("       source_id, ");
        varname1.append("       assigned_user_id, ");
        varname1.append("       project_id, ");
        varname1.append("       email, ");
        varname1.append("       website, ");
        varname1.append("       phone, ");
        varname1.append("       last_contact_at ");
        varname1.append("FROM   leads ");
        varname1.append("WHERE  id = :leadId ");
        varname1.append("INTO   :Name, ");
        varname1.append("       :Company, ");
        varname1.append("       :Position, ");
        varname1.append("       :clientId, ");
        varname1.append("       :lost, ");
        varname1.append("       :Description, ");
        varname1.append("       :Address, ");
        varname1.append("       :City, ");
        varname1.append("       :PostalCode, ");
        varname1.append("       :Country, ");
        varname1.append("       :Status, ");
        varname1.append("       :Source, ");
        varname1.append("       :AssignedUser, ");
        varname1.append("       :projectId, ");
        varname1.append("       :Email, ");
        varname1.append("       :Website, ");
        varname1.append("       :Phone, ");
        varname1.append("       :LastContactAt ");
        SQL.selectInto(varname1.toString(), formData);

        // Load attachments when loading task.
        List<AttachmentsTableRowData> attachments = fetchAttachments(formData);
        formData.getAttachmentsBox().getAttachmentsTable().setRows(attachments.toArray(new AttachmentsTableRowData[0]));

        //Load tasks
        List<TasksTableRowData> tasks = fetchTasks(formData.getLeadId());
        formData.getTasksBox().getTasksTable().setRows(tasks.toArray(new TasksTableRowData[0]));

        //Load activity log
        List<ActivityLogTableRowData> activityLof = fetchActivityLog(formData.getLeadId());
        formData.getActivityLogTable().setRows(activityLof.toArray(new ActivityLogTableRowData[0]));

        // Fetch notes
        formData.getNotesBox().setRelatedId(formData.getLeadId());
        formData.getNotesBox().setRelatedType(Related.LEAD);

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
        varname1.append("    company = :Company, ");
        varname1.append("    position = :Position, ");
        varname1.append("    description = :Description, ");
        varname1.append("    address = :Address, ");
        varname1.append("    city = :City, ");
        varname1.append("    postal_code = :PostalCode, ");
        varname1.append("    country_id = :Country, ");
        varname1.append("    status_id = :Status, ");
        varname1.append("    source_id = :Source, ");
        varname1.append("    assigned_user_id = :AssignedUser, ");
        varname1.append("    email = :Email, ");
        varname1.append("    website = :Website, ");
        varname1.append("    phone = :Phone, ");
        varname1.append("    last_contact_at = :LastContactAt ");
        varname1.append("WHERE id = :leadId");
        SQL.update(varname1.toString(), formData);

        saveAttachments(formData);

        return formData;
    }

    @Override
    public void markAsLost(Integer leadId, boolean lost) {
        SQL.update("UPDATE leads SET lost = :isLost WHERE id = :leadId", new NVPair("leadId", leadId), new NVPair("isLost", lost));
    }

    @Override
    public List<TasksTableRowData> fetchTasks(Integer leadId) {
        TaskRequest request = new TaskRequest();
        request.setRelatedId(leadId);
        request.setRelatedType(Related.LEAD);

        List<Task> tasks = BEANS.get(TaskDao.class).get(request);

        List<TasksTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tasks)) return CollectionUtility.emptyArrayList();

        for (Task task : tasks) {
            TasksTableRowData row = new TasksTableRowData();
            row.setTask(task);
            row.setName(task.getTitle());
            row.setPriority(task.getPriorityId());
            row.setStartAt(task.getStartAt());
            row.setDeadlineAt(task.getDeadlineAt());
            row.setStatus(task.getStatusId());
            rows.add(row);
        }

        return rows;
    }

    @Override
    public Lead find(Integer leadId) {
        return BEANS.get(LeadDao.class).find(leadId);
    }

    @Override
    public List<ActivityLogTableRowData> fetchActivityLog(Integer leadId) {
        BeanArrayHolder<ActivityLogTableRowData> holder = new BeanArrayHolder<>(ActivityLogTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   al.id, ");
        varname1.append("         al.content, ");
        varname1.append("         u.first_name ");
        varname1.append("                  || ' ' ");
        varname1.append("                  || u.last_name, ");
        varname1.append("         al.created_at ");
        varname1.append("FROM     lead_activity_log al, ");
        varname1.append("         users u ");
        varname1.append("WHERE    al.user_id = u.id ");
        varname1.append("AND      al.deleted_at IS NULL ");
        varname1.append("AND      al.lead_id = :leadId ");
        varname1.append("AND      al.organisation_id = :organisationId ");
        varname1.append("ORDER BY al.created_at DESC ");
        varname1.append("into     :{rows.ActivityLogId}, ");
        varname1.append("         :{rows.Content}, ");
        varname1.append("         :{rows.User}, ");
        varname1.append("         :{rows.CreatedAt}");
        SQL.selectInto(varname1.toString(), new NVPair("rows", holder), new NVPair("leadId", leadId), new NVPair("organisationId", getCurrentOrganisationId()));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void deleteActivityLog(Integer activityLogId) {
        SQL.update("UPDATE lead_activity_log SET deleted_at = now() WHERE id = :activityId", new NVPair("activityId", activityLogId));
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
