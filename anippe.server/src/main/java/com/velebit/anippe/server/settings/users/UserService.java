package com.velebit.anippe.server.settings.users;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.settings.roles.RoleDao;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.settings.users.IUserService;
import com.velebit.anippe.shared.settings.users.UserFormData;
import com.velebit.anippe.shared.utilities.PasswordUtility;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Set;

public class UserService extends AbstractService implements IUserService {
    @Override
    public UserFormData prepareCreate(UserFormData formData) {
        return formData;
    }

    @Override
    public UserFormData create(UserFormData formData) {

        if (formData.getUserId() != null)
            return store(formData);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO users ");
        varname1.append("            (first_name, ");
        varname1.append("             last_name, ");
        varname1.append("             username, ");
        varname1.append("             email, ");
        varname1.append("             password, ");
        varname1.append("             active, ");
        varname1.append("             organisation_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:FirstName, ");
        varname1.append("             :LastName, ");
        varname1.append("             :Username, ");
        varname1.append("             :Email, ");
        varname1.append("             :defaultPassword, ");
        varname1.append("             :Active, ");
        varname1.append("             :organisationId, ");
        varname1.append("             Now()) ");
        varname1.append("returning id INTO :userId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("defaultPassword", PasswordUtility.calculateEncodedPassword(Constants.DEFAULT_USER_PASSWORD)));

        saveRoles(formData);

        return formData;
    }

    private void loadRoles(UserFormData formData) {
        formData.getRolesBox().setValue(BEANS.get(RoleDao.class).findRoleIdsForUser(formData.getUserId().intValue()));
    }

    private void saveRoles(UserFormData formData) {
        Integer userId = formData.getUserId().intValue();
        Set<Long> roleIds = formData.getRolesBox().getValue();

        BEANS.get(RoleDao.class).linkUserWithRoles(userId, roleIds);
    }

    @Override
    public UserFormData load(UserFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT first_name, ");
        varname1.append("       last_name, ");
        varname1.append("       username, ");
        varname1.append("       email, ");
        varname1.append("       active ");
        varname1.append("FROM   users ");
        varname1.append("WHERE  id = :userId ");
        varname1.append("INTO ");
        varname1.append(":FirstName, ");
        varname1.append(":LastName, ");
        varname1.append(":Username, ");
        varname1.append(":Email, ");
        varname1.append(":Active");
        SQL.selectInto(varname1.toString(), formData);

        loadRoles(formData);

        formData.getRolesBox().setValue(BEANS.get(RoleDao.class).findRoleIdsForUser(formData.getUserId().intValue()));

        return formData;
    }

    @Override
    public UserFormData store(UserFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE users ");
        varname1.append("SET    username = :Username, ");
        varname1.append("       first_name = :FirstName, ");
        varname1.append("       last_name = :LastName, ");
        varname1.append("       active = :Active, ");
        varname1.append("       email = :Email, ");
        varname1.append("       administrator = :Administrator ");
        varname1.append("WHERE  id = :userId");
        SQL.update(varname1.toString(), formData);

        saveRoles(formData);

        return formData;
    }

    @Override
    public boolean isUsernameValid(String username, Integer userId) {
        IntegerHolder holder = new IntegerHolder();

        String stmt = "SELECT COUNT(*) FROM users WHERE organisation_id = :organisationId AND deleted_at is null AND username = :username ";

        if (userId != null) {
            stmt += " AND id != :userId ";
        }
        stmt += " INTO :Holder ";

        SQL.selectInto(stmt, new NVPair("username", username), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userId", userId), new NVPair("Holder", holder));

        return holder.getValue() != null && holder.getValue() > 0;
    }

    @Override
    public void resetPassword(int userId) {
        String stmt = "UPDATE users SET password = :newPassword WHERE id = :userId";
        SQL.update(stmt, new NVPair("userId", userId), new NVPair("newPassword", PasswordUtility.calculateEncodedPassword(Constants.DEFAULT_USER_PASSWORD)));

    }
}
