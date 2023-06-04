package com.velebit.anippe.server.settings.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.settings.leads.ILeadStatusesService;
import com.velebit.anippe.shared.settings.leads.LeadStatusesTablePageData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class LeadStatusesService implements ILeadStatusesService {
    @Override
    public LeadStatusesTablePageData getLeadStatusesTableData(SearchFilter filter) {
        LeadStatusesTablePageData pageData = new LeadStatusesTablePageData();

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT id, name, color ");
        varname1.append("FROM lead_statuses ");
        varname1.append("WHERE organisation_id = :organisationId ");
        varname1.append("AND deleted_at IS NULL ");
        varname1.append("INTO :StatusId, :Name, :Color");
        SQL.selectInto(varname1.toString(), pageData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return pageData;
    }

    @Override
    public void delete(Integer statusId) {

    }
}
