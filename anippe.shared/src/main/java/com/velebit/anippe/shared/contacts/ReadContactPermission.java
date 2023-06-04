package com.velebit.anippe.shared.contacts;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadContactPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadContactPermission() {
        super("ReadContactPermission");
    }
}
