package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IKnowledgeArticleLookupService extends ILookupService<Long> {

}
