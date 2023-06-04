package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.IProjectsService;
import com.velebit.anippe.shared.projects.ProjectsTablePageData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class ProjectsService extends AbstractService implements IProjectsService {
    @Override
    public ProjectsTablePageData getProjectsTableData(SearchFilter filter) {
        ProjectsTablePageData pageData = new ProjectsTablePageData();

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT          p.id, ");
        varname1.append("                p.NAME, ");
        varname1.append("                c.NAME, ");
        varname1.append("                c.id, ");
        varname1.append("                p.type_id, ");
        varname1.append("                p.start_at, ");
        varname1.append("                p.deadline_at, ");
        varname1.append("                p.status_id ");
        varname1.append("FROM            projects p ");
        varname1.append("LEFT OUTER JOIN clients c ");
        varname1.append("ON              c.id = p.client_id ");
        varname1.append("WHERE           p.deleted_at IS NULL ");
        varname1.append("AND             p.organisation_id = :organisationId ");
        varname1.append("ORDER BY        p.created_at ");
        varname1.append("into            :{rows.ProjectId}, ");
        varname1.append("                :{rows.Name}, ");
        varname1.append("                :{rows.Client}, ");
        varname1.append("                :{rows.ClientId}, ");
        varname1.append("                :{rows.Type}, ");
        varname1.append("                :{rows.StartAt}, ");
        varname1.append("                :{rows.DeadlineAt}, ");
        varname1.append("                :{rows.Status}");
        SQL.selectInto(varname1.toString(), new NVPair("organisationId", getCurrentOrganisationId()),new NVPair("rows", pageData));

        return pageData;
    }
}
