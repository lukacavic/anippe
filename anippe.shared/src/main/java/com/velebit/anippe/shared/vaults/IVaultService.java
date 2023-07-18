package com.velebit.anippe.shared.vaults;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IVaultService extends IService {
    VaultFormData prepareCreate(VaultFormData formData);

    VaultFormData create(VaultFormData formData);

    VaultFormData load(VaultFormData formData);

    VaultFormData store(VaultFormData formData);
}
