package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ILeadService extends IService {
    LeadFormData prepareCreate(LeadFormData formData);

    LeadFormData create(LeadFormData formData);

    LeadFormData load(LeadFormData formData);

    LeadFormData store(LeadFormData formData);

    Lead find(Integer leadId);

    boolean isEmailUnique(String email, Integer leadId);

    boolean isPhoneUnique(String rawValue, Integer integer);
}
