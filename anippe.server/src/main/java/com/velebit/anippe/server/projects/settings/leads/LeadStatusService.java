package com.velebit.anippe.server.projects.settings.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.projects.settings.leads.ILeadStatusService;
import com.velebit.anippe.shared.projects.settings.leads.LeadStatusFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class LeadStatusService implements ILeadStatusService {
    @Override
    public LeadStatusFormData prepareCreate(LeadStatusFormData formData) {
        return formData;
    }

    @Override
    public LeadStatusFormData create(LeadStatusFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO lead_statuses ");
        varname1.append("            (name, color, ");
        varname1.append("            project_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:Name, :Color, :projectId, ");
        varname1.append("             :organisationId, ");
        varname1.append("             Now())");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));


        return formData;
    }

    @Override
    public LeadStatusFormData load(LeadStatusFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, color ");
        varname1.append("FROM lead_statuses ");
        varname1.append("WHERE id = :leadStatusId ");
        varname1.append("INTO :Name, :Color ");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public LeadStatusFormData store(LeadStatusFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE lead_statuses ");
        varname1.append("SET    name = :Name, ");
        varname1.append("       color = :Color, ");
        varname1.append("       updated_at = Now() ");
        varname1.append("WHERE  id = :leadStatusId ");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
