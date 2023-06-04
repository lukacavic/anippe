package com.velebit.anippe.server.settings.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.settings.leads.ILeadSourcesService;
import com.velebit.anippe.shared.settings.leads.LeadSourcesTablePageData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class LeadSourcesService implements ILeadSourcesService {
    @Override
    public LeadSourcesTablePageData getLeadSourcesTableData(SearchFilter filter) {
        LeadSourcesTablePageData pageData = new LeadSourcesTablePageData();

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT id, name ");
        varname1.append("FROM lead_sources ");
        varname1.append("WHERE organisation_id = :organisationId ");
        varname1.append("AND deleted_at IS NULL ");
        varname1.append("INTO :SourceId, :Name ");
        SQL.selectInto(varname1.toString(), pageData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return pageData;
    }

    @Override
    public void delete(Integer sourceId) {
        StringBuffer  varname1 = new StringBuffer();
        varname1.append("UPDATE lead_sources ");
        varname1.append("SET    deleted_at = Now() ");
        varname1.append("WHERE  id = :leadSourceId");
        SQL.update(varname1.toString(), new NVPair("leadSourceId", sourceId));
    }
}
