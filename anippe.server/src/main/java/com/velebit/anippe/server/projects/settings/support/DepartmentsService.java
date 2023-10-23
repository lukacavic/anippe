package com.velebit.anippe.server.projects.settings.support;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.settings.support.DepartmentsTablePageData;
import com.velebit.anippe.shared.projects.settings.support.IDepartmentsService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class DepartmentsService extends AbstractService implements IDepartmentsService {
    @Override
    public DepartmentsTablePageData getDepartmentsTableData(SearchFilter filter, Integer projectId) {
        DepartmentsTablePageData pageData = new DepartmentsTablePageData();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT id, ");
        varname1.append("       NAME, ");
        varname1.append("       active, ");
        varname1.append("       imap_import_enabled ");
        varname1.append("FROM   ticket_departments ");
        varname1.append("WHERE  project_id = :projectId ");
        varname1.append("AND    organisation_id = :organisationId ");
        varname1.append("AND    deleted_at IS NULL ");
        varname1.append("into   :{pageData.DepartmentId}, ");
        varname1.append("       :{pageData.Name}, ");
        varname1.append("       :{pageData.Active}, ");
        varname1.append("       :{pageData.EmailImapEnabled}");
        SQL.selectInto(varname1.toString(), new NVPair("projectId", projectId), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("pageData", pageData));
        return pageData;
    }
}
