package com.velebit.anippe.shared.contacts;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IContactLookupService extends ILookupService<Long> {

}
