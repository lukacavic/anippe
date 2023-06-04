package com.velebit.anippe.server.settings.roles;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.settings.users.UserMap;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.settings.roles.Role;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.*;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

@Bean
public class RoleDao {
    public List<Role> all() {
        BeanArrayHolder<RoleDto> holder = new BeanArrayHolder<>(RoleDto.class);

        List<Role> roles = CollectionUtility.emptyArrayList();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   r.id, ");
        varname1.append("         r.created_at, ");
        varname1.append("         r.NAME ");
        varname1.append("FROM     roles r ");
        varname1.append("WHERE    r.deleted_at IS NULL ");
        varname1.append("AND      r.organisation_id = :organisationId ");
        varname1.append("ORDER BY r.NAME ");
        varname1.append("into     :{holder.id}, ");
        varname1.append("         :{holder.createdAt}, ");
        varname1.append("         :{holder.name}");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));
        List<RoleDto> dto = CollectionUtility.arrayList(holder.getBeans());

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new RoleMap());
        dto.forEach(item -> roles.add(mapper.map(item, Role.class)));

        return roles;
    }


    /**
     * Delete linked roles for user
     *
     * @param userId
     */
    public void deleteUserRoles(Integer userId) {
        String stmt = "DELETE FROM link_user_roles WHERE user_id = :userId";
        SQL.update(stmt, new NVPair("userId", userId));
    }

    /**
     * Link user with roles
     *
     * @param userId
     * @param roleIds
     */
    public void linkUserWithRoles(Integer userId, Set<Long> roleIds) {
        deleteUserRoles(userId);

        if(CollectionUtility.isEmpty(roleIds)) return;

        for (Long roleId : roleIds) {
            String stmt = "INSERT INTO link_user_roles (user_id, role_id) VALUES (:userId, :roleId)";
            SQL.insert(stmt, new NVPair("userId", userId), new NVPair("roleId", roleId));
        }
    }

    public Set<Long> findRoleIdsForUser(int userId) {
        LongArrayHolder holder = new LongArrayHolder();

        String stmt = "SELECT role_id FROM link_user_roles WHERE user_id = :userId INTO :holder";
        SQL.selectInto(stmt, new NVPair("userId", userId), new NVPair("holder", holder));

        return CollectionUtility.hashSet(holder.getValue());
    }

}
