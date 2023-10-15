package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.LeadFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
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
        varname1.append("            (name, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :leadId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return formData;
    }

    @Override
    public LeadFormData load(LeadFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       company ");
        varname1.append("FROM   leads ");
        varname1.append("WHERE  id = :leadId ");
        varname1.append("INTO   :Name, :Company");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public LeadFormData store(LeadFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE leads ");
        varname1.append("SET name    = :Name, ");
        varname1.append("    company = :Company ");
        varname1.append("WHERE id = :leadId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
