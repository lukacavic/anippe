package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.projects.IProjectsService;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.ProjectsTablePageData;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class ProjectsService extends AbstractService implements IProjectsService {
    @Override
    public ProjectsTablePageData getProjectsTableData(SearchFilter filter) {
        ProjectsTablePageData pageData = new ProjectsTablePageData();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT          p.id, ");
        varname1.append("                p.NAME, ");
        varname1.append("                c.NAME, ");
        varname1.append("                c.id, ");
        varname1.append("                p.type_id, ");
        varname1.append("                p.start_at, ");
        varname1.append("                p.deadline_at, ");
        varname1.append("                p.status_id, ");
        varname1.append("                p.type_id, ");
        varname1.append("(SELECT string_agg(u.first_name || ' ' || u.last_name, ', ') FROM users u, link_project_users lpu WHERE lpu.user_id = u.id AND lpu.project_id = p.id ) ");
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
        varname1.append("                :{rows.Status}, ");
        varname1.append("                :{rows.Type}, ");
        varname1.append("                :{rows.Members} ");
        SQL.selectInto(varname1.toString(), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("rows", pageData));

        return pageData;
    }

    @Override
    public List<Project> findForUser(Integer userId) {
        BeanArrayHolder<Project> holder = new BeanArrayHolder<>(Project.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT DISTINCT ON (p.id) p.id, ");
        varname1.append("       p.NAME ");
        varname1.append("FROM   projects p, ");
        varname1.append("       users u, ");
        varname1.append("       link_project_users lpu ");
        varname1.append("WHERE  p.id = lpu.project_id ");
        varname1.append("AND    (u.id = lpu.user_id OR u.administrator is true)");
        varname1.append("AND    p.deleted_at IS NULL ");
        varname1.append("AND    u.id = :userId ");
        varname1.append("into   :{project.id}, ");
        varname1.append("       :{project.name}");
        SQL.selectInto(varname1.toString(), new NVPair("project", holder), new NVPair("userId", ServerSession.get().getCurrentUser().getId()));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void delete(Integer projectId) {
        SQL.update("UPDATE projects SET deleted_at = now() WHERE id = :projectId", new NVPair("projectId", projectId));
    }
}
