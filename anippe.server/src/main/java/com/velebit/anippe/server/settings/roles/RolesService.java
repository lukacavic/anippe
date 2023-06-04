package com.velebit.anippe.server.settings.roles;

import com.velebit.anippe.shared.settings.roles.IRolesService;
import com.velebit.anippe.shared.settings.roles.Role;
import com.velebit.anippe.shared.settings.roles.RolesTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class RolesService implements IRolesService {
    @Override
    public RolesTablePageData getRolesTableData(SearchFilter filter) {
        RolesTablePageData pageData = new RolesTablePageData();

        List<Role> roles = BEANS.get(RoleDao.class).all();

        if(CollectionUtility.isEmpty(roles)) return pageData;

        for(Role role : roles) {
            RolesTablePageData.RolesTableRowData row = pageData.addRow();
            row.setRole(role);
            row.setName(role.getName());
            row.setCreatedAt(role.getCreatedAt());
        }

        return pageData;
    }
}
