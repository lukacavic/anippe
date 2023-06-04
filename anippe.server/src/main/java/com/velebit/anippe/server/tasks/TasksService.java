package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.ITasksService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import com.velebit.anippe.shared.tasks.TasksTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class TasksService implements ITasksService {
    @Override
    public TasksTablePageData getTasksTableData(SearchFilter filter) {
        TasksTablePageData pageData = new TasksTablePageData();

        TaskRequest request = new TaskRequest();
        List<Task> tasks = BEANS.get(TaskDao.class).get(request);

        if(CollectionUtility.isEmpty(tasks)) return pageData;

        for(Task task : tasks) {
            TasksTablePageData.TasksTableRowData row = pageData.addRow();
            row.setTask(task);
            row.setName(task.getTitle());
            row.setPriority(task.getPriorityId());
            row.setStartAt(task.getStartAt());
            row.setDeadlineAt(task.getDeadlineAt());
            row.setStatus(task.getStatusId());
        }

        return pageData;
    }
}
