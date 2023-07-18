package com.velebit.anippe.shared.vaults;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IVaultsService extends IService {
    VaultsFormData prepareCreate(VaultsFormData formData);

    VaultsFormData create(VaultsFormData formData);

    VaultsFormData load(VaultsFormData formData);

    VaultsFormData store(VaultsFormData formData);
}
