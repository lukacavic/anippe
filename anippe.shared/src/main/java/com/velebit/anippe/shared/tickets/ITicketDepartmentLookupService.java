package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface ITicketDepartmentLookupService extends ILookupService<Long> {

}
