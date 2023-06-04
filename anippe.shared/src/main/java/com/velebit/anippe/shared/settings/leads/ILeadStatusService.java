package com.velebit.anippe.shared.settings.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ILeadStatusService extends IService {
    LeadStatusFormData prepareCreate(LeadStatusFormData formData);

    LeadStatusFormData create(LeadStatusFormData formData);

    LeadStatusFormData load(LeadStatusFormData formData);

    LeadStatusFormData store(LeadStatusFormData formData);
}
