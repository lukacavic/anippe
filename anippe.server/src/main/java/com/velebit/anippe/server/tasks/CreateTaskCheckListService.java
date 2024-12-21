package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.tasks.CreateTaskCheckListFormData;
import com.velebit.anippe.shared.tasks.ICreateTaskCheckListService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class CreateTaskCheckListService extends AbstractService implements ICreateTaskCheckListService {
    @Override
    public CreateTaskCheckListFormData prepareCreate(CreateTaskCheckListFormData formData) {
        return formData;
    }

    @Override
    public CreateTaskCheckListFormData create(CreateTaskCheckListFormData formData) {
        SQL.insert("INSERT INTO task_checklists (task_id, name, created_at, user_created_id) VALUES (:taskId, :name, NOW(), :userId)", formData, new NVPair("userId", getCurrentUserId()));

        return formData;
    }

    @Override
    public CreateTaskCheckListFormData load(CreateTaskCheckListFormData formData) {
        return formData;
    }

    @Override
    public CreateTaskCheckListFormData store(CreateTaskCheckListFormData formData) {
        return formData;
    }
}
