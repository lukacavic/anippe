package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.constants.Constants.TaskStatus;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskViewFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;

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
