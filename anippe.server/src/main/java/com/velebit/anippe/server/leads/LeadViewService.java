package com.velebit.anippe.server.leads;

import com.velebit.anippe.shared.leads.ILeadViewService;
import com.velebit.anippe.shared.leads.LeadViewFormData;

public class LeadViewService implements ILeadViewService {
    @Override
    public LeadViewFormData prepareCreate(LeadViewFormData formData) {
        return formData;
    }

    @Override
    public LeadViewFormData create(LeadViewFormData formData) {
        return formData;
    }

    @Override
    public LeadViewFormData load(LeadViewFormData formData) {
        return formData;
    }

    @Override
    public LeadViewFormData store(LeadViewFormData formData) {
        return formData;
    }
}
