package com.velebit.anippe.shared.email;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadEmailPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadEmailPermission() {
        super("ReadEmailPermission");
    }
}
