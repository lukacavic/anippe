package com.velebit.anippe.shared.settings.users;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadUserPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadUserPermission() {
        super("ReadUserPermission");
    }
}
