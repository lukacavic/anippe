package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.TasksFormData;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import com.velebit.anippe.shared.tasks.TasksTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class TasksService implements ITasksService {
    @Override
    public TasksFormData prepareCreate(TasksFormData formData) {
        TaskRequest request = new TaskRequest();
        request.setRelatedId(formData.getProject().getId());
        request.setRelatedType(Constants.Related.PROJECT);

        List<Task> tasks = BEANS.get(TaskDao.class).get(request);

        if (CollectionUtility.isEmpty(tasks)) return formData;

        for (Task task : tasks) {
            TasksFormData.TasksTable.TasksTableRowData row = formData.getTasksTable().addRow();
            row.setTask(task);
            row.setName(task.getTitle());
            row.setPriority(task.getPriorityId());
            row.setStartAt(task.getStartAt());
            row.setDeadlineAt(task.getDeadlineAt());
            row.setStatus(task.getStatusId());
        }

        return formData;
    }

    @Override
    public TasksFormData create(TasksFormData formData) {
        return formData;
    }

    @Override
    public List<TasksTablePageData.TasksTableRowData> fetchTasks() {
        return CollectionUtility.emptyArrayList();
    }

}
