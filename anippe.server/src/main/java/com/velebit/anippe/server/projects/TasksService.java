package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class TasksService implements ITasksService {
    @Override
    public TasksFormData prepareCreate(TasksFormData formData) {
        if (!ACCESS.check(new CreateTasksPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public TasksFormData create(TasksFormData formData) {
        if (!ACCESS.check(new CreateTasksPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public TasksFormData load(TasksFormData formData) {
        if (!ACCESS.check(new ReadTasksPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public TasksFormData store(TasksFormData formData) {
        if (!ACCESS.check(new UpdateTasksPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }
}
