package com.velebit.anippe.shared.country;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface ICountryLookupService extends ILookupService<Long> {

}
