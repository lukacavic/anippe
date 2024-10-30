package com.velebit.anippe.server.projects.settings.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.leads.LeadDao;
import com.velebit.anippe.shared.projects.settings.leads.ILeadStatusesService;
import com.velebit.anippe.shared.projects.settings.leads.LeadStatusesTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class LeadStatusesService implements ILeadStatusesService {
    @Override
    public LeadStatusesTablePageData getLeadStatusesTableData(SearchFilter filter, Integer projectId) {
        LeadStatusesTablePageData pageData = new LeadStatusesTablePageData();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT id, name, color, deleteable ");
        varname1.append("FROM lead_statuses ");
        varname1.append("WHERE organisation_id = :organisationId ");
        varname1.append("AND project_id = :projectId ");
        varname1.append("AND deleted_at IS NULL ");
        varname1.append("INTO :StatusId, :Name, :Color, :Deleteable");
        SQL.selectInto(varname1.toString(), pageData,
                new NVPair("projectId", projectId),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return pageData;
    }

    @Override
    public void delete(Integer statusId) {
        int leadsCount = BEANS.get(LeadDao.class).countLeadsByStatus(statusId);

        if(leadsCount > 0) {
            throw new VetoException("Status ima vezane leadove, te se ne može brisati.");
        }

        SQL.update("UPDATE lead_statuses SET deleted_at = now() WHERE id = :statusId", new NVPair("statusId", statusId));
    }
}
