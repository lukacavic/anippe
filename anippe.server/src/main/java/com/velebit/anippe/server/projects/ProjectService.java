package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ProjectService extends AbstractService implements IProjectService {
    @Override
    public ProjectFormData prepareCreate(ProjectFormData formData) {
        return formData;
    }

    @Override
    public ProjectFormData create(ProjectFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO projects ");
        varname1.append("            (NAME, ");
        varname1.append("             description, ");
        varname1.append("             type_id, ");
        varname1.append("             client_id, ");
        varname1.append("             start_at, ");
        varname1.append("             deadline_at, ");
        varname1.append("             status_id, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Description, ");
        varname1.append("             :Type, ");
        varname1.append("             :Client, ");
        varname1.append("             :StartDate, ");
        varname1.append("             :Deadline, ");
        varname1.append("             :Status, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("RETURNING id INTO :projectId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()));

        saveProjectUsers(formData);

        return formData;
    }

    private void saveProjectUsers(ProjectFormData formData) {
        deleteProjectUsers(formData.getProjectId());

        if(CollectionUtility.isEmpty(formData.getMembersListBox().getValue())) return;

        for (Long userId : formData.getMembersListBox().getValue()) {
            String stmt = "INSERT INTO link_project_users (user_id, project_id) VALUES (:userId, :projectId)";
            SQL.insert(stmt, formData, new NVPair("userId", userId));
        }
    }

    private void deleteProjectUsers(Integer projectId) {
        SQL.delete("DELETE FROM link_project_users WHERE project_id = :projectId", new NVPair("projectId", projectId));
    }

    @Override
    public ProjectFormData load(ProjectFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       type_id, ");
        varname1.append("       description, ");
        varname1.append("       start_at, ");
        varname1.append("       deadline_at, ");
        varname1.append("       client_id, ");
        varname1.append("       status_id ");
        varname1.append("FROM   projects ");
        varname1.append("WHERE  id = :projectId ");
        varname1.append("INTO   :Name, :Type, :Description, :StartDate, ");
        varname1.append(":Deadline, :Client, :Status");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public ProjectFormData store(ProjectFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("UPDATE projects ");
        varname1.append("SET    NAME = :Name, ");
        varname1.append("       type_id = :Type, ");
        varname1.append("       description = :Description, ");
        varname1.append("       start_at = :StartDate, ");
        varname1.append("       deadline_at = :Deadline, ");
        varname1.append("       client_id = :Client, ");
        varname1.append("       status_id = :Status ");
        varname1.append("WHERE  id = :projectId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public void delete(Integer projectId) {
        SQL.update("UPDATE projects SET deleted_at = now() WHERE id = :projectId", new NVPair("projectId", projectId));
    }
}
