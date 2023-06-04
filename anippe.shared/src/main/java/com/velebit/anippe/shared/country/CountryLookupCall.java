package com.velebit.anippe.shared.country;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class CountryLookupCall extends LookupCall<Long> {
    private static final long serialVersionUID = 1L;

    @Override
    protected Class<? extends ILookupService<Long>> getConfiguredService() {
        return ICountryLookupService.class;
    }
}
