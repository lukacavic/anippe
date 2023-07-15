package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.IManageUsersService;
import com.velebit.anippe.shared.projects.ManageUsersFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ManageUsersService implements IManageUsersService {
    @Override
    public ManageUsersFormData prepareCreate(ManageUsersFormData formData) {
        SQL.selectInto("SELECT user_id FROM link_project_users WHERE project_id = :projectId INTO :UsersBox", formData);

        return formData;
    }

    @Override
    public ManageUsersFormData create(ManageUsersFormData formData) {

        deleteProjectUsers(formData.getProjectId());

        for (Long userId : formData.getUsersBox().getValue()) {
            String stmt = "INSERT INTO link_project_users (user_id, project_id) VALUES (:userId, :projectId)";
            SQL.insert(stmt, formData, new NVPair("userId", userId));
        }
        return formData;
    }

    private void deleteProjectUsers(Integer projectId) {
        SQL.delete("DELETE FROM link_project_users WHERE project_id = :projectId", new NVPair("projectId", projectId));
    }

}
