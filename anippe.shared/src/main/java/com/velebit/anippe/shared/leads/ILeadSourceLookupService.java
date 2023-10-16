package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface ILeadSourceLookupService extends ILookupService<Long> {

}
