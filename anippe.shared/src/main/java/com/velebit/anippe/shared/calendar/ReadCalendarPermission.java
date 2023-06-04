package com.velebit.anippe.shared.calendar;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadCalendarPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadCalendarPermission() {
        super("ReadCalendarPermission");
    }
}
