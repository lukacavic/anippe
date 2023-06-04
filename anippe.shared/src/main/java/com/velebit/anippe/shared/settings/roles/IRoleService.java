package com.velebit.anippe.shared.settings.roles;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IRoleService extends IService {
    RoleFormData prepareCreate(RoleFormData formData);

    RoleFormData create(RoleFormData formData);

    RoleFormData load(RoleFormData formData);

    RoleFormData store(RoleFormData formData);
}
