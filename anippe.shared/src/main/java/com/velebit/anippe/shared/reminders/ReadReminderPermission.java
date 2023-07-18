package com.velebit.anippe.shared.reminders;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadReminderPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadReminderPermission() {
        super("ReadReminderPermission");
    }
}
