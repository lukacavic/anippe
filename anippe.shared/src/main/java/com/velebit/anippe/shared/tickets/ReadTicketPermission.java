package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadTicketPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadTicketPermission() {
        super("ReadTicketPermission");
    }
}
