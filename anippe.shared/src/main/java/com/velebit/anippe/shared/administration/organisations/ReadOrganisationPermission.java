package com.velebit.anippe.shared.administration.organisations;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadOrganisationPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadOrganisationPermission() {
        super("ReadOrganisationPermission");
    }
}
