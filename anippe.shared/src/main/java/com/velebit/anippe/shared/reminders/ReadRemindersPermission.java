package com.velebit.anippe.shared.reminders;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadRemindersPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadRemindersPermission() {
        super("ReadRemindersPermission");
    }
}
