package com.velebit.anippe.shared.contacts;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class ContactLookupCall extends LookupCall<Long> {
    private static final long serialVersionUID = 1L;

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected Class<? extends ILookupService<Long>> getConfiguredService() {
        return IContactLookupService.class;
    }
}
