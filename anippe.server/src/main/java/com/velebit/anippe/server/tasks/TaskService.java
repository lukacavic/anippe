package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class TaskService extends AbstractService implements ITaskService {

    @Override
    public TaskFormData prepareCreate(TaskFormData formData) {
        return formData;
    }

    @Override
    public TaskFormData create(TaskFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO tasks ");
        varname1.append("            (NAME, ");
        varname1.append("             description, ");
        varname1.append("             user_id, ");
        varname1.append("             related_id, ");
        varname1.append("             related_type, ");
        varname1.append("             priority_id, ");
        varname1.append("             status_id, ");
        varname1.append("             start_at, ");
        varname1.append("             deadline_at, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Description, ");
        varname1.append("             :userId, ");
        varname1.append("             :relatedId, ");
        varname1.append("             :relatedType, ");
        varname1.append("             :Priority, ");
        varname1.append("             :statusCreated, ");
        varname1.append("             :StartAt, ");
        varname1.append("             :DeadlineAt, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :taskId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("statusCreated", Constants.TaskStatus.CREATED), new NVPair("userId", ServerSession.get().getCurrentUser().getId()));

        saveAttachments(formData);
        saveFollowers(formData);
        saveAssignedUsers(formData);

        emitModuleEvent(Task.class, new Task(), ChangeStatus.INSERTED);
        return formData;
    }

    private void saveFollowers(TaskFormData formData) {
        //deleteFollowers(formData);

        //if (CollectionUtility.isEmpty(formData.getFollowersBox().getValue())) return;

        /*for (Long userId : formData.getFollowersBox().getValue()) {
            String stmt = "INSERT INTO link_task_followers (user_id, task_id) VALUES (:userId, :taskId)";
            SQL.insert(stmt, formData, new NVPair("userId", userId));
        }*/
    }

    private void deleteFollowers(TaskFormData formData) {
        String stmt = "DELETE FROM link_task_followers WHERE task_id = :taskId";
        SQL.update(stmt, new NVPair("taskId", formData.getTaskId()));
    }

    private void saveAttachments(TaskFormData formData) {

    }

    private void saveAssignedUsers(TaskFormData formData) {
        /*deleteAssignedUsers(formData);

        if (CollectionUtility.isEmpty(formData.getAssignedUsersBox().getValue())) return;

        for (Long userId : formData.getAssignedUsersBox().getValue()) {
            String stmt = "INSERT INTO link_task_users (user_id, task_id) VALUES (:userId, :taskId)";
            SQL.insert(stmt, formData, new NVPair("userId", userId));
        }*/
    }

    private void deleteAssignedUsers(TaskFormData formData) {
        String stmt = "DELETE FROM link_task_users WHERE task_id = :taskId";
        SQL.update(stmt, new NVPair("taskId", formData.getTaskId()));
    }

    @Override
    public TaskFormData load(TaskFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       description, ");
        varname1.append("       start_at, ");
        varname1.append("       deadline_at, ");
        varname1.append("       related_id, ");
        varname1.append("       related_type, ");
        varname1.append("       status_id, ");
        varname1.append("       priority_id ");
        varname1.append("FROM   tasks ");
        varname1.append("WHERE  id = :taskId ");
        varname1.append("INTO   :Name, :Description, :StartAt, :DeadlineAt, ");
        varname1.append(":relatedId, :relatedType, :statusId, :Priority");
        SQL.selectInto(varname1.toString(), formData);

        fetchAssignedUsers(formData);
        fetchFollowers(formData);

        return formData;
    }

    private void fetchAssignedUsers(TaskFormData formData) {
        SQL.selectInto("SELECT user_id FROM link_task_users WHERE task_id = :taskId INTO :AssignedUsersBox", formData);
    }

    private void fetchFollowers(TaskFormData formData) {
        SQL.selectInto("SELECT user_id FROM link_task_followers WHERE task_id = :taskId INTO :FollowersBox", formData);
    }

    @Override
    public TaskFormData store(TaskFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE tasks SET ");
        varname1.append("name = :Name, ");
        varname1.append("description = :Description, ");
        varname1.append("priority_id = :Priority, ");
        varname1.append("start_at = :StartAt, ");
        varname1.append("deadline_at = :DeadlineAt, ");
        varname1.append("updated_at = now() ");
        varname1.append("WHERE id = :taskId");
        SQL.update(varname1.toString(), formData);

        saveAttachments(formData);
        saveFollowers(formData);
        saveAssignedUsers(formData);

        emitModuleEvent(Task.class, new Task(), ChangeStatus.UPDATED);

        return formData;
    }

    @Override
    public void delete(Integer taskId) {
        SQL.update("UPDATE tasks SET deleted_at = now() WHERE id = :taskId", new NVPair("taskId", taskId));
    }
}
