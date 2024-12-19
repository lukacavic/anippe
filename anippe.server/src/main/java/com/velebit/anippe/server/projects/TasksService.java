package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.TasksFormData;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class TasksService implements ITasksService {
    @Override
    public TasksFormData prepareCreate(TasksFormData formData) {
        return formData;
    }

    @Override
    public TasksFormData create(TasksFormData formData) {
        return formData;
    }

    @Override
    public List<TasksFormData.TasksTable.TasksTableRowData> fetchTasks(TaskRequest request) {
        List<Task> tasks = BEANS.get(TaskDao.class).get(request);

        List<TasksFormData.TasksTable.TasksTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tasks)) return CollectionUtility.emptyArrayList();

        for (Task task : tasks) {
            TasksFormData.TasksTable.TasksTableRowData row = new TasksFormData.TasksTable.TasksTableRowData();
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
    public void updateStatus(Integer taskId, Integer statusId) {
        SQL.update("UPDATE tasks SET status_id = :statusId WHERE id = :taskId", new NVPair("statusId", statusId), new NVPair("taskId", taskId));
    }

    @Override
    public void updatePriority(Integer taskId, Integer priorityId) {
        SQL.update("UPDATE tasks SET priority_id = :priorityId WHERE id = :taskId", new NVPair("priorityId", priorityId), new NVPair("taskId", taskId));
    }

}
