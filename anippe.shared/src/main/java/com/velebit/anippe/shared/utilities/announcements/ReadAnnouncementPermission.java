package com.velebit.anippe.shared.utilities.announcements;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadAnnouncementPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadAnnouncementPermission() {
        super("ReadAnnouncementPermission");
    }
}
