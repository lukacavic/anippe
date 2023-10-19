package com.velebit.anippe.server.projects.settings.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.projects.settings.leads.ILeadSourceService;
import com.velebit.anippe.shared.projects.settings.leads.LeadSourceFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class LeadSourceService implements ILeadSourceService {
    @Override
    public LeadSourceFormData prepareCreate(LeadSourceFormData formData) {
        return formData;
    }

    @Override
    public LeadSourceFormData create(LeadSourceFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO lead_sources ");
        varname1.append("            (name, ");
        varname1.append("            project_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:Name, :projectId, ");
        varname1.append("             :organisationId, ");
        varname1.append("             Now())");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return formData;
    }

    @Override
    public LeadSourceFormData load(LeadSourceFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name ");
        varname1.append("FROM lead_sources ");
        varname1.append("WHERE id = :leadSourceId ");
        varname1.append("INTO :Name");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public LeadSourceFormData store(LeadSourceFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE lead_sources ");
        varname1.append("SET    name = :Name, ");
        varname1.append("       updated_at = Now() ");
        varname1.append("WHERE  id = :leadSourceId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
