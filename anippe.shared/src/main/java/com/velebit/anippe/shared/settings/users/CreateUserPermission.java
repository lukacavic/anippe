package com.velebit.anippe.shared.settings.users;

import org.eclipse.scout.rt.security.AbstractPermission;

public class CreateUserPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public CreateUserPermission() {
        super("CreateUserPermission");
    }
}
