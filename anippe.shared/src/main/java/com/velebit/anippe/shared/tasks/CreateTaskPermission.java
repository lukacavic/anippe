package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.security.AbstractPermission;

public class CreateTaskPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public CreateTaskPermission() {
        super("CreateTaskPermission");
    }
}
