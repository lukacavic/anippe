package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.TasksFormData;

public class TasksService implements ITasksService {
    @Override
    public TasksFormData prepareCreate(TasksFormData formData) {
        return formData;
    }

    @Override
    public TasksFormData create(TasksFormData formData) {
        return formData;
    }

}
