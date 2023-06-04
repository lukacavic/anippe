package com.velebit.anippe.shared.clients;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IClientLookupService extends ILookupService<Long> {

}
