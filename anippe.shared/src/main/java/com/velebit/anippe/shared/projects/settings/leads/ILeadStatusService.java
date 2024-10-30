package com.velebit.anippe.shared.projects.settings.leads;

import com.velebit.anippe.shared.leads.LeadStatus;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ILeadStatusService extends IService {
    LeadStatusFormData prepareCreate(LeadStatusFormData formData);

    LeadStatusFormData create(LeadStatusFormData formData);

    LeadStatusFormData load(LeadStatusFormData formData);

    LeadStatusFormData store(LeadStatusFormData formData);

    List<LeadStatus> fetchStatuses(Integer projectId);
}
