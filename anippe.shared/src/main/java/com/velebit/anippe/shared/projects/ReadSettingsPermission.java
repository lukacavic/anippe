package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadSettingsPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadSettingsPermission() {
        super("ReadSettingsPermission");
    }
}
