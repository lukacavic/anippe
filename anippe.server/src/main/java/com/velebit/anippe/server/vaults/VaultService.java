package com.velebit.anippe.server.vaults;

import com.velebit.anippe.shared.vaults.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class VaultService implements IVaultService {
    @Override
    public VaultFormData prepareCreate(VaultFormData formData) {
        return formData;
    }

    @Override
    public VaultFormData create(VaultFormData formData) {
        return formData;
    }

    @Override
    public VaultFormData load(VaultFormData formData) {
        return formData;
    }

    @Override
    public VaultFormData store(VaultFormData formData) {
        return formData;
    }
}
