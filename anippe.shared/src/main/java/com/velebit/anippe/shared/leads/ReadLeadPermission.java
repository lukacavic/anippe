package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadLeadPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadLeadPermission() {
        super("ReadLeadPermission");
    }
}
