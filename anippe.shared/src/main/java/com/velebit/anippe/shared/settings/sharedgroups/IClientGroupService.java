package com.velebit.anippe.shared.settings.sharedgroups;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IClientGroupService extends IService {
    ClientGroupFormData prepareCreate(ClientGroupFormData formData);

    ClientGroupFormData create(ClientGroupFormData formData);

    ClientGroupFormData load(ClientGroupFormData formData);

    ClientGroupFormData store(ClientGroupFormData formData);
}
