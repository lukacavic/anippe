package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.constants.Constants.TaskStatus;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskViewFormData;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import com.velebit.anippe.shared.tasks.TaskViewFormData.SubTasksTable.SubTasksTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
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
    public TaskViewFormData load(TaskViewFormData formData) {
        Task task = BEANS.get(TaskDao.class).find(formData.getTaskId());

        formData.setTask(task);
        formData.getDescription().setValue(task.getDescription());

        //Load child tasks
        List<SubTasksTableRowData> childTasksRows = fetchChildTasks(formData.getTaskId());
        formData.getSubTasksTable().setRows(childTasksRows.toArray(new SubTasksTableRowData[0]));

        //Load activity log
        List<ActivityLogTableRowData> activityLogRows = fetchComments(formData.getTaskId());
        formData.getActivityLogTable().setRows(activityLogRows.toArray(new ActivityLogTableRowData[0]));

        return formData;
    }

    public List<SubTasksTableRowData> fetchChildTasks(Integer taskId) {
        BeanArrayHolder<SubTasksTableRowData> holder = new BeanArrayHolder<>(SubTasksTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   tc.id, ");
        varname1.append("         tc.completed_at, ");
        varname1.append("         CASE WHEN tc.completed_at IS NULL THEN FALSE ELSE TRUE END, ");
        varname1.append("         tc.description, ");
        varname1.append("         u.first_name ");
        varname1.append("                  || ' ' ");
        varname1.append("                  || u.last_name ");
        varname1.append("FROM     task_checklists tc, ");
        varname1.append("         users u ");
        varname1.append("WHERE    tc.user_created_id = u.id ");
        varname1.append("AND      tc.deleted_at IS NULL ");
        varname1.append("AND      tc.task_id = :taskId ");
        varname1.append("ORDER BY tc.created_at DESC ");
        varname1.append("into     :{holder.ChildTaskId}, ");
        varname1.append("         :{holder.CompletedAt}, ");
        varname1.append("         :{holder.Completed}, ");
        varname1.append("         :{holder.Task}, ");
        varname1.append("         :{holder.CreatedBy}");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("taskId", taskId));

        return CollectionUtility.arrayList(holder.getBeans());
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
        if(activeTimerId != null) {
            BEANS.get(TaskDao.class).stopTimer(activeTimerId);

            return null;
        }

        return BEANS.get(TaskDao.class).startTimer(taskId);
    }

    @Override
    public void deleteChildTask(Integer childTaskId) {
        SQL.update("UPDATE task_checklists SET deleted_at = now() WHERE id = :childTaskId", new NVPair("childTaskId", childTaskId));
    }

    @Override
    public Integer updateChildTask(Integer taskId, Integer childTaskId, String content) {
        if (childTaskId != null) {
            SQL.update("UPDATE task_checklists SET description = :description WHERE id = :childTaskId", new NVPair("description", content), new NVPair("childTaskId", childTaskId));
            return null;
        }

        return createChildTask(taskId, content);
    }

    @Override
    public void updateCompleted(Integer childTaskId, Boolean completed) {
        SQL.update("UPDATE task_checklists SET completed_at = :completedAt WHERE id = :childTaskId",
                new NVPair("childTaskId", childTaskId),
                new NVPair("completedAt", completed ? new Date() : null)
        );
    }

    @Override
    public List<ActivityLogTableRowData> fetchComments(Integer taskId) {
        BeanArrayHolder<ActivityLogTableRowData> holder = new BeanArrayHolder<>(ActivityLogTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   tal.id, ");
        varname1.append("         tal.content, ");
        varname1.append("         u.first_name ");
        varname1.append("                  || ' ' ");
        varname1.append("                  || u.last_name, ");
        varname1.append("         tal.created_at ");
        varname1.append("FROM     task_activity_log tal, ");
        varname1.append("         users u ");
        varname1.append("WHERE    tal.user_id = u.id ");
        varname1.append("AND      tal.deleted_at IS NULL ");
        varname1.append("AND      tal.task_id = :taskId ");
        varname1.append("ORDER BY tal.created_at DESC ");
        varname1.append("into     :{holder.ActivityLogId}, ");
        varname1.append("         :{holder.ActivityLog}, ");
        varname1.append("         :{holder.CreatedBy}, ");
        varname1.append("         :{holder.CreatedAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("taskId", taskId));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    private Integer createChildTask(Integer taskId, String content) {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("INSERT INTO task_checklists (task_id, description, user_created_id) VALUES (:taskId, :description, :createdId) RETURNING id INTO :holder",
                new NVPair("createdId", getCurrentUserId()),
                new NVPair("taskId", taskId),
                new NVPair("holder", holder),
                new NVPair("description", content)
        );

        return holder.getValue();
    }
}
