package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.AbstractCheckListGroupBoxData.SubTasksTable.SubTasksTableRowData;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.TaskStatus;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskViewFormData;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;
import java.util.List;

public class TaskViewService extends AbstractService implements ITaskViewService {
    @Override
    public TaskViewFormData prepareCreate(TaskViewFormData formData) {
        return formData;
    }

    @Override
    public TaskViewFormData create(TaskViewFormData formData) {
        return formData;
    }

    @Override
    public Integer activeTimeId(Integer taskId) {
        IntegerHolder holder = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT id ");
        varname1.append("FROM   task_timers ");
        varname1.append("WHERE  task_id = :taskId ");
        varname1.append("       AND end_at IS NULL ");
        varname1.append("       AND start_at IS NOT NULL ");
        varname1.append("       AND user_id = :userId ");
        varname1.append("       AND deleted_at IS NULL ");
        varname1.append("       LIMIT 1 ");
        varname1.append("INTO   :holder");
        SQL.selectInto(varname1.toString(),
                new NVPair("userId", getCurrentUserId()),
                new NVPair("holder", holder),
                new NVPair("taskId", taskId)
        );

        return holder.getValue();
    }

    @Override
    public TaskViewFormData load(TaskViewFormData formData) {
        Task task = BEANS.get(TaskDao.class).find(formData.getTaskId());

        formData.setTask(task);
        formData.getDescription().setValue(task.getDescription());

        formData.setActiveTimerId(activeTimeId(formData.getTaskId()));

        //Load activity log
        List<ActivityLogTableRowData> activityLogRows = fetchComments(formData.getTaskId(), false);
        formData.getActivityLogTable().setRows(activityLogRows.toArray(new ActivityLogTableRowData[0]));

        return formData;
    }

    @Override
    public TaskViewFormData store(TaskViewFormData formData) {
        return formData;
    }

    @Override
    public void markAsCompleted(Integer taskId, boolean completed) {
        SQL.update("UPDATE tasks SET completed_at = :completedAt, status_id = :statusId WHERE id = :taskId",
                new NVPair("taskId", taskId),
                new NVPair("completedAt", completed ? new Date() : null),
                new NVPair("statusId", completed ? TaskStatus.COMPLETED : TaskStatus.CREATED)
        );
    }

    @Override
    public Task find(Integer taskId) {
        return BEANS.get(TaskDao.class).find(taskId);
    }

    @Override
    public void addComment(Integer taskId, String comment) {
        SQL.insert("INSERT INTO task_activity_log (task_id, user_id, content, organisation_id) VALUES (:taskId, :userId, :activity, :organisationId)",
                new NVPair("userId", getCurrentUserId()),
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("activity", comment),
                new NVPair("taskId", taskId)
        );
    }


    @Override
    public Integer toggleTimer(Integer taskId, Integer activeTimerId) {
        if (activeTimerId != null) {
            BEANS.get(TaskDao.class).stopTimer(activeTimerId);

            return null;
        }

        return BEANS.get(TaskDao.class).startTimer(taskId);
    }

    @Override
    public void deleteTaskCheckListItem(Integer childTaskId) {
        SQL.update("UPDATE task_checklist_items SET deleted_at = now() WHERE id = :childTaskId", new NVPair("childTaskId", childTaskId));
    }

    @Override
    public Integer updateTaskCheckListItem(Integer checkListId, Integer childTaskId, String content) {
        if (childTaskId != null) {
            SQL.update("UPDATE task_checklist_items SET description = :description, updated_at = now() WHERE id = :childTaskId", new NVPair("description", content), new NVPair("childTaskId", childTaskId));
            return null;
        }

        return createChildTask(checkListId, content);
    }

    @Override
    public void updateTaskCheckListItemAsCompleted(Integer childTaskId, Boolean completed) {
        SQL.update("UPDATE task_checklist_items SET completed_at = :completedAt WHERE id = :childTaskId",
                new NVPair("childTaskId", childTaskId),
                new NVPair("completedAt", completed ? new Date() : null)
        );
    }

    @Override
    public List<ActivityLogTableRowData> fetchComments(Integer taskId, boolean withSystemLog) {
        BeanArrayHolder<ActivityLogTableRowData> holder = new BeanArrayHolder<>(ActivityLogTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   tal.id, ");
        varname1.append("         tal.content, ");
        varname1.append("         u.id, ");
        varname1.append("         u.first_name ");
        varname1.append("                  || ' ' ");
        varname1.append("                  || u.last_name, ");
        varname1.append("         tal.created_at ");
        varname1.append("FROM     task_activity_log tal, ");
        varname1.append("         users u ");
        varname1.append("WHERE    tal.user_id = u.id ");
        varname1.append("AND      tal.deleted_at IS NULL ");

        if (!withSystemLog) {
            varname1.append("AND      system_created IS FALSE ");
        }

        varname1.append("AND      tal.task_id = :taskId ");
        varname1.append("ORDER BY tal.created_at DESC ");
        varname1.append("into     :{holder.ActivityLogId}, ");
        varname1.append("         :{holder.ActivityLog}, ");
        varname1.append("         :{holder.CreatedById}, ");
        varname1.append("         :{holder.CreatedBy}, ");
        varname1.append("         :{holder.CreatedAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("taskId", taskId));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void updateActivityLog(Integer activityLogId, String content) {
        SQL.update("UPDATE task_activity_log SET content = :content WHERE id = :activityLogId", new NVPair("content", content), new NVPair("activityLogId", activityLogId));
    }

    @Override
    public void deleteActivityLog(Integer activityLogId) {
        SQL.update("UPDATE task_activity_log SET deleted_at = now() WHERE id = :activityLogId", new NVPair("activityLogId", activityLogId));
    }

    @Override
    public void deleteTask(Integer taskId) {
        SQL.update("UPDATE tasks SET deleted_at = now() WHERE id = :taskId", new NVPair("taskId", taskId));
    }

    @Override
    public void archiveTask(Integer taskId, boolean b) {
        SQL.update("UPDATE tasks SET archived_at = :archivedAt WHERE id = :taskId", new NVPair("taskId", taskId), new NVPair("archivedAt", b ? new Date() : null));
    }

    @Override
    public void assignToMe(Integer taskId) {
        SQL.insert("INSERT INTO link_task_users (task_id, user_id) VALUES (:taskId, :userId)", new NVPair("userId", getCurrentUserId()), new NVPair("taskId", taskId));
    }

    @Override
    public void addAttachments(Integer taskId, List<BinaryResource> attachments) {
        for (BinaryResource binaryResource : attachments) {
            Attachment attachment = new Attachment();
            attachment.setAttachment((binaryResource).getContent());
            attachment.setCreatedAt(new Date());
            attachment.setFileName(binaryResource.getFilename());
            attachment.setFileExtension(binaryResource.getContentType());
            attachment.setFileSize(binaryResource.getContentLength());
            attachment.setRelatedId(taskId);
            attachment.setRelatedTypeId(Constants.Related.TASK);
            attachment.setName(binaryResource.getFilename());

            BEANS.get(IAttachmentService.class).saveAttachment(attachment);
        }
    }

    @Override
    public void changeStatus(Integer taskId, Integer statusId) {
        SQL.update("UPDATE tasks SET status_id = :statusId WHERE id = :taskId", new NVPair("statusId", statusId), new NVPair("taskId", taskId));
    }

    @Override
    public void deleteCheckList(Integer checkListId) {
        SQL.update("UPDATE task_checklists SET deleted_at = now() WHERE id = :checkListId", new NVPair("checkListId", checkListId));
    }

    @Override
    public List<SubTasksTableRowData> fetchCheckListItems(Integer checkListId) {
        BeanArrayHolder<SubTasksTableRowData> holder = new BeanArrayHolder<>(SubTasksTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT tci.id, ");
        varname1.append("       tci.description, ");
        varname1.append("       tci.created_at, ");
        varname1.append("       u.first_name ");
        varname1.append("              || ' ' ");
        varname1.append("              || u.last_name, ");
        varname1.append("       CASE WHEN tci.completed_at IS NULL THEN false ELSE true END, tci.completed_at ");
        varname1.append("FROM   task_checklist_items tci, ");
        varname1.append("       users u ");
        varname1.append("WHERE  tci.deleted_at IS NULL ");
        varname1.append("AND    u.id = tci.user_created_id ");
        varname1.append("AND    tci.task_checklist_id = :checkListId ");
        varname1.append("into   :{holder.ChildTaskId}, ");
        varname1.append("       :{holder.Task}, ");
        varname1.append("       :{holder.CreatedAt}, ");
        varname1.append("       :{holder.CreatedBy}, ");
        varname1.append("       :{holder.Completed}, ");
        varname1.append("       :{holder.CompletedAt}");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("checkListId", checkListId));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    private Integer createChildTask(Integer checkListId, String content) {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("INSERT INTO task_checklist_items (task_checklist_id, description, user_created_id) VALUES (:checkListId, :description, :createdId) RETURNING id INTO :holder",
                new NVPair("createdId", getCurrentUserId()),
                new NVPair("checkListId", checkListId),
                new NVPair("holder", holder),
                new NVPair("description", content)
        );

        return holder.getValue();
    }
}
