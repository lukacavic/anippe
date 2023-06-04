package com.velebit.anippe.shared.settings.leads;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadLeadStatusPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadLeadStatusPermission() {
        super("ReadLeadStatusPermission");
    }
}
