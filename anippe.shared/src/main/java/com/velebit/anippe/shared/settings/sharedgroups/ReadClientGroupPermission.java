package com.velebit.anippe.shared.settings.sharedgroups;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadClientGroupPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadClientGroupPermission() {
        super("ReadClientGroupPermission");
    }
}
