package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.leads.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class LeadService implements ILeadService {
    @Override
    public LeadFormData prepareCreate(LeadFormData formData) {
        return formData;
    }

    @Override
    public LeadFormData create(LeadFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO leads ");
        varname1.append("            (NAME, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :leadId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return formData;
    }

    @Override
    public LeadFormData load(LeadFormData formData) {
        return formData;
    }

    @Override
    public LeadFormData store(LeadFormData formData) {
        return formData;
    }
}
