package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IManageUsersService extends IService {
    ManageUsersFormData prepareCreate(ManageUsersFormData formData);

    ManageUsersFormData create(ManageUsersFormData formData);

}
