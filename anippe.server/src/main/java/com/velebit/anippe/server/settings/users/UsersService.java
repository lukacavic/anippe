package com.velebit.anippe.server.settings.users;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.settings.users.IUsersService;
import com.velebit.anippe.shared.settings.users.UserRequest;
import com.velebit.anippe.shared.settings.users.UsersTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class UsersService implements IUsersService {
    @Override
    public UsersTablePageData getUsersTableData(SearchFilter filter) {
        UsersTablePageData pageData = new UsersTablePageData();

        UserRequest request = new UserRequest();

        List<User> users = BEANS.get(UserDao.class).get(request);

        if (CollectionUtility.isEmpty(users)) return pageData;

        for (User user : users) {
            UsersTablePageData.UsersTableRowData row = pageData.addRow();
            row.setUser(user);
            row.setFirstName(user.getFirstName());
            row.setLastName(user.getLastName());
            row.setUsername(user.getUsername());
            row.setCreatedAt(user.getCreatedAt());
            row.setActive(user.isActive());
            row.setEmail(user.getEmail());
            row.setLastLogin(user.getLastLoginAt());
        }

        return pageData;
    }

    @Override
    public void toggleActivated(Integer userId, Boolean active) {
        String stmt = "UPDATE users SET active = :active WHERE id = :userId";
        SQL.update(stmt, new NVPair("active", active), new NVPair("userId", userId));
    }

    @Override
    public void delete(Integer userId) {
        // Cannot delete self
        if (ServerSession.get().getCurrentUser().getId().intValue() == userId.intValue()) {
            throw new VetoException(TEXTS.get("CannotDeleteYourself"));
        }

        // Cannot delete if administrator.
        if (ServerSession.get().getCurrentUser().getId().intValue() == userId.intValue() && ServerSession.get().getCurrentUser().isAdministrator()) {
            throw new VetoException(TEXTS.get("CannotDeleteAdminUser"));
        }

        String stmt = "UPDATE users SET deleted_at = now() WHERE id = :userId";
        SQL.update(stmt, new NVPair("userId", userId));
    }
}
