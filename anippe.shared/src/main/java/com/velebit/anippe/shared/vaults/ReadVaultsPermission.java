package com.velebit.anippe.shared.vaults;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadVaultsPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadVaultsPermission() {
        super("ReadVaultsPermission");
    }
}
