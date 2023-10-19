package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ILeadToClientService extends IService {
    LeadToClientFormData prepareCreate(LeadToClientFormData formData);

    LeadToClientFormData create(LeadToClientFormData formData);

}
