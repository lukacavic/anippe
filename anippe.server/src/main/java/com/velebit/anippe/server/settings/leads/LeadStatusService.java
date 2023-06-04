package com.velebit.anippe.server.settings.leads;

import com.velebit.anippe.shared.settings.leads.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class LeadStatusService implements ILeadStatusService {
    @Override
    public LeadStatusFormData prepareCreate(LeadStatusFormData formData) {
        return formData;
    }

    @Override
    public LeadStatusFormData create(LeadStatusFormData formData) {
        return formData;
    }

    @Override
    public LeadStatusFormData load(LeadStatusFormData formData) {
        return formData;
    }

    @Override
    public LeadStatusFormData store(LeadStatusFormData formData) {
        return formData;
    }
}
