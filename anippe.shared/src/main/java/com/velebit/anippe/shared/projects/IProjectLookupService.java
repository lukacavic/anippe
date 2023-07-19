package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IProjectLookupService extends ILookupService<Long> {

}
