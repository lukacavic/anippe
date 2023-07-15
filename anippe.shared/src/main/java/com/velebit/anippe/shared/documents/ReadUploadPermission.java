package com.velebit.anippe.shared.documents;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadUploadPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadUploadPermission() {
        super("ReadUploadPermission");
    }
}
