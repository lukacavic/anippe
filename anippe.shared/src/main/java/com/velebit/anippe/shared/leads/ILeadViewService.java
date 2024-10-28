package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ILeadViewService extends IService {
    LeadViewFormData prepareCreate(LeadViewFormData formData);

    LeadViewFormData create(LeadViewFormData formData);

    LeadViewFormData load(LeadViewFormData formData);

    LeadViewFormData store(LeadViewFormData formData);
}
