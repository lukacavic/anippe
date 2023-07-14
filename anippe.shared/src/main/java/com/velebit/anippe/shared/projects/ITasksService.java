package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITasksService extends IService {
    TasksFormData prepareCreate(TasksFormData formData);

    TasksFormData create(TasksFormData formData);

}
