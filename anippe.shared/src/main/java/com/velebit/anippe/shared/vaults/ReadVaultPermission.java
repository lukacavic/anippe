package com.velebit.anippe.shared.vaults;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadVaultPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadVaultPermission() {
        super("ReadVaultPermission");
    }
}
