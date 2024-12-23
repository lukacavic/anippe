package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.AbstractCheckListGroupBoxData.SubTasksTable.SubTasksTableRowData;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.TaskStatus;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskActivityLog;
import com.velebit.anippe.shared.tasks.TaskViewFormData;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.BooleanHolder;
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
        formData.setFollowingTask(calculateIsFollowing(formData.getTaskId()));

        //Load activity log
        List<ActivityLogTableRowData> activityLogRows = fetchTaskActivityLog(formData.getTaskId(), false);
        formData.getActivityLogTable().setRows(activityLogRows.toArray(new ActivityLogTableRowData[0]));

        return formData;
    }

    private boolean calculateIsFollowing(Integer taskId) {
        BooleanHolder holder = new BooleanHolder();

        SQL.selectInto("SELECT EXISTS(SELECT * FROM task_followers WHERE user_id = :userId AND task_id = :taskId) INTO :holder", new NVPair("userId", getCurrentUserId()), new NVPair("holder", holder), new NVPair("taskId", taskId));

        return holder.getValue();
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

        if (completed) {
            SQL.update("UPDATE task_timers SET end_at = now() WHERE task_id = :taskId", new NVPair("taskId", taskId));
            SQL.update("UPDATE task_checklist_items SET completed_at = now() WHERE completed_at IS NULL AND task_checklist_id IN (SELECT id FROM task_checklists WHERE task_id = :taskId)", new NVPair("taskId", taskId));
        }
    }

    @Override
    public Task find(Integer taskId) {
        return BEANS.get(TaskDao.class).find(taskId);
    }

    @Override
    public void addComment(Integer taskId, String comment, List<BinaryResource> attachments) {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("INSERT INTO task_activity_log (task_id, user_id, content, organisation_id) VALUES (:taskId, :userId, :activity, :organisationId) RETURNING id INTO :holder",
                new NVPair("userId", getCurrentUserId()),
                new NVPair("holder", holder),
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("activity", comment),
                new NVPair("taskId", taskId)
        );

        if (!CollectionUtility.isEmpty(attachments)) {
            for (BinaryResource binaryResource : attachments) {
                Attachment attachment = new Attachment();
                attachment.setAttachment((binaryResource).getContent());
                attachment.setCreatedAt(new Date());
                attachment.setFileName(binaryResource.getFilename());
                attachment.setFileExtension(binaryResource.getContentType());
                attachment.setFileSize(binaryResource.getContentLength());
                attachment.setRelatedId(holder.getValue());
                attachment.setRelatedTypeId(Constants.Related.TASK_ACTIVITY_LOG);
                attachment.setName(binaryResource.getFilename());

                BEANS.get(IAttachmentService.class).saveAttachment(attachment);
            }
        }
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
    public Integer updateTaskCheckListItem(Integer checkListId, Integer childTaskId, String content, Long userId) {
        if (childTaskId != null) {
            SQL.update("UPDATE task_checklist_items SET description = :description, assigned_user_id = :userId, updated_at = now() WHERE id = :childTaskId", new NVPair("description", content), new NVPair("childTaskId", childTaskId), new NVPair("userId", userId));
            return null;
        }

        return createChildTask(checkListId, content, userId);
    }

    @Override
    public void updateTaskCheckListItemAsCompleted(Integer childTaskId, Boolean completed) {
        SQL.update("UPDATE task_checklist_items SET completed_at = :completedAt WHERE id = :childTaskId",
                new NVPair("childTaskId", childTaskId),
                new NVPair("completedAt", completed ? new Date() : null)
        );
    }

    @Override
    public List<ActivityLogTableRowData> fetchTaskActivityLog(Integer taskId, boolean withSystemLog) {
        List<TaskActivityLog> logs = BEANS.get(TaskActivityLogDao.class).get(taskId, withSystemLog);

        if (CollectionUtility.isEmpty(logs)) return CollectionUtility.emptyArrayList();

        List<ActivityLogTableRowData> rows = CollectionUtility.emptyArrayList();

        for (TaskActivityLog log : logs) {
            ActivityLogTableRowData row = new ActivityLogTableRowData();
            row.setActivityLog(log);
            row.setActivityLogId(log.getId());
            row.setCreatedAt(log.getCreatedAt());
            row.setCreatedBy(log.getUser().getFullName());
            row.setCreatedById(log.getUser().getId());

            rows.add(row);
        }

        return rows;
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
        varname1.append("       tci.assigned_user_id, ");
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
        varname1.append("       :{holder.User}, ");
        varname1.append("       :{holder.CreatedAt}, ");
        varname1.append("       :{holder.CreatedBy}, ");
        varname1.append("       :{holder.Completed}, ");
        varname1.append("       :{holder.CompletedAt}");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("checkListId", checkListId));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void followTask(Integer taskId, boolean follow) {
        if (follow) {
            StringBuffer varname1 = new StringBuffer();
            varname1.append("INSERT INTO task_followers (user_id, task_id) ");
            varname1.append("VALUES (:userId, :taskId) ");
            varname1.append("ON CONFLICT (user_id, task_id) DO NOTHING");
            SQL.insert(varname1.toString(), new NVPair("userId", getCurrentUserId()), new NVPair("taskId", taskId));
        } else {
            StringBuffer varname1 = new StringBuffer();
            varname1.append("DELETE FROM task_followers ");
            varname1.append("WHERE user_id = :userId ");
            varname1.append("AND   task_id = :taskId ");
            SQL.update(varname1.toString(), new NVPair("userId", getCurrentUserId()), new NVPair("taskId", taskId));
        }

    }

    @Override
    public void updateDescription(String description, Integer taskId) {
        SQL.update("UPDATE tasks SET description = :description WHERE id = :taskId", new NVPair("description", description), new NVPair("taskId", taskId));
    }

    private Integer createChildTask(Integer checkListId, String content, Long userId) {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("INSERT INTO task_checklist_items (task_checklist_id, description, user_created_id, assigned_user_id) VALUES (:checkListId, :description, :createdId, :userId) RETURNING id INTO :holder",
                new NVPair("createdId", getCurrentUserId()),
                new NVPair("checkListId", checkListId),
                new NVPair("holder", holder),
                new NVPair("userId", userId),
                new NVPair("description", content)
        );

        return holder.getValue();
    }
}
