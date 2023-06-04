package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.security.AbstractPermission;

public class CreateTicketPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public CreateTicketPermission() {
        super("CreateTicketPermission");
    }
}
