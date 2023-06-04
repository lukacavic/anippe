package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadTaskPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadTaskPermission() {
        super("ReadTaskPermission");
    }
}
