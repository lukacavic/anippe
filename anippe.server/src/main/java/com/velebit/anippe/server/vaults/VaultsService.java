package com.velebit.anippe.server.vaults;

import com.velebit.anippe.shared.vaults.IVaultsService;
import com.velebit.anippe.shared.vaults.VaultsFormData;

public class VaultsService implements IVaultsService {
    @Override
    public VaultsFormData prepareCreate(VaultsFormData formData) {
        return formData;
    }

    @Override
    public VaultsFormData create(VaultsFormData formData) {
        return formData;
    }

    @Override
    public VaultsFormData load(VaultsFormData formData) {
        return formData;
    }

    @Override
    public VaultsFormData store(VaultsFormData formData) {
        return formData;
    }
}
