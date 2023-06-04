package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITaskService extends IService {
    TaskFormData prepareCreate(TaskFormData formData);

    TaskFormData create(TaskFormData formData);

    TaskFormData load(TaskFormData formData);

    TaskFormData store(TaskFormData formData);

    void delete(Integer taskId);
}
