package com.velebit.anippe.shared.settings.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ILeadSourceService extends IService {
    LeadSourceFormData prepareCreate(LeadSourceFormData formData);

    LeadSourceFormData create(LeadSourceFormData formData);

    LeadSourceFormData load(LeadSourceFormData formData);

    LeadSourceFormData store(LeadSourceFormData formData);
}
