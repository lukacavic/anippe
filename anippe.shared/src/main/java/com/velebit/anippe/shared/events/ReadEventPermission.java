package com.velebit.anippe.shared.events;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadEventPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadEventPermission() {
        super("ReadEventPermission");
    }
}
