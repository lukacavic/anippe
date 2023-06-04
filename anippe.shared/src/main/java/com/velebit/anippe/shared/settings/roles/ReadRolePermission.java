package com.velebit.anippe.shared.settings.roles;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadRolePermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadRolePermission() {
        super("ReadRolePermission");
    }
}
