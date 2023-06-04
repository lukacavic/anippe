package com.velebit.anippe.server.settings.roles;

import com.velebit.anippe.shared.settings.roles.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class RoleService implements IRoleService {
    @Override
    public RoleFormData prepareCreate(RoleFormData formData) {
        return formData;
    }

    @Override
    public RoleFormData create(RoleFormData formData) {
        return formData;
    }

    @Override
    public RoleFormData load(RoleFormData formData) {
        return formData;
    }

    @Override
    public RoleFormData store(RoleFormData formData) {
        return formData;
    }
}
