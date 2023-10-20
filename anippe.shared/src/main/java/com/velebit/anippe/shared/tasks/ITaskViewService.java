package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITaskViewService extends IService {
    TaskViewFormData prepareCreate(TaskViewFormData formData);

    TaskViewFormData create(TaskViewFormData formData);

    TaskViewFormData load(TaskViewFormData formData);

    TaskViewFormData store(TaskViewFormData formData);
}
