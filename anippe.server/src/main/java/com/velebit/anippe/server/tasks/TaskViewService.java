package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.TaskViewFormData;

public class TaskViewService implements ITaskViewService {
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
        return formData;
    }

    @Override
    public TaskViewFormData store(TaskViewFormData formData) {
        return formData;
    }
}
