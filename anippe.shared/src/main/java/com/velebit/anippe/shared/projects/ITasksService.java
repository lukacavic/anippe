package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ITasksService extends IService {
    TasksFormData prepareCreate(TasksFormData formData);

    TasksFormData create(TasksFormData formData);

    List<TasksFormData.TasksTable.TasksTableRowData> fetchTasks(Integer relatedType, Integer relatedId);

    void updateStatus(Integer taskId, Integer statusId);

    void updatePriority(Integer taskId, Integer priorityId);
}
