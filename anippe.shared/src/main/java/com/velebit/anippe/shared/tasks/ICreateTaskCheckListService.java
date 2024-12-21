package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ICreateTaskCheckListService extends IService {
    CreateTaskCheckListFormData prepareCreate(CreateTaskCheckListFormData formData);

    CreateTaskCheckListFormData create(CreateTaskCheckListFormData formData);

    CreateTaskCheckListFormData load(CreateTaskCheckListFormData formData);

    CreateTaskCheckListFormData store(CreateTaskCheckListFormData formData);
}
