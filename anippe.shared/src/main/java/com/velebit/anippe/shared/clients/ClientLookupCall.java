package com.velebit.anippe.shared.clients;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class ClientLookupCall extends LookupCall<Long> {
    private static final long serialVersionUID = 1L;

    @Override
    protected Class<? extends ILookupService<Long>> getConfiguredService() {
        return IClientLookupService.class;
    }
}
