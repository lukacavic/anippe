package com.velebit.anippe.server.auth;

import com.velebit.anippe.server.config.AdminProperties;
import com.velebit.anippe.server.organisations.OrganisationDao;
import com.velebit.anippe.shared.auth.ILoginService;
import com.velebit.anippe.shared.organisations.Organisation;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.utilities.PasswordUtility;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService implements ILoginService {

    Logger LOG = LoggerFactory.getLogger(getClass());

    private boolean checkLoginPassword(User user, String passwordPlainText) {
        return PasswordUtility.passwordIsValid(passwordPlainText, user.getPassword());
    }

    @Override
    public User getUser(String username, String password, String subdomain) {
        User user = new User();

        String adminUsername = CONFIG.getPropertyValue(AdminProperties.AdminUsername.class);
        String adminPassword = CONFIG.getPropertyValue(AdminProperties.AdminPassword.class);

        if (username.equals(adminUsername) && PasswordUtility.passwordIsValid(adminPassword, PasswordUtility.calculateEncodedPassword(adminPassword))) {
            user.setId(-1);
            user.setSuperAdministrator(true);

            return user;
        }

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT u.id, ");
        varname1.append("       u.username, ");
        varname1.append("       u.password, ");
        varname1.append("       u.administrator, ");
        varname1.append("       u.organisation_id ");
        varname1.append("FROM   users u, ");
        varname1.append("       organisations o ");
        varname1.append("WHERE  u.organisation_id = o.id ");
        varname1.append("       AND u.username = :username ");
        varname1.append("       AND u.active = true ");
        varname1.append("       AND o.deleted_at IS NULL ");
        varname1.append("       AND u.deleted_at IS NULL ");

        // Do not use subdomain if dev mode.
        if (!CONFIG.getPropertyValue(PlatformConfigProperties.PlatformDevModeProperty.class)) {
            varname1.append(" AND o.subdomain = :subdomain ");
        }

        varname1.append("INTO :{user.id}, :{user.Username}, :{user.Password}, :{user.Administrator}, :{user.organisation.id} ");
        SQL.selectInto(varname1.toString(), new NVPair("user", user), new NVPair("subdomain", subdomain), new NVPair("username", username), new NVPair("password", password), new NVPair("subdomain", subdomain));

        if (user.getId() == null) {
            LOG.info("User {} not found ", username);
            return null;
        }

        // Check password on login for user.
        if (!checkLoginPassword(user, password)) {
            LOG.info("Wrong password for user {} ", username);
            return null;
        }

        return user;
    }

	@Override
	public User getUserByUsername(String userId) {
		User user = new User();

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT u.id, ");
		varname1.append("       u.username, ");
		varname1.append("       u.password, ");
		varname1.append("       u.first_name, ");
		varname1.append("       u.last_name, ");
		varname1.append("       u.organisation_id, ");
		varname1.append("       u.administrator, ");
		varname1.append("       u.super_administrator ");
		varname1.append("FROM   users u ");
		varname1.append("WHERE  u.username = :username   ");
		varname1.append("AND    u.deleted_at IS NULL ");
		varname1.append("AND    u.active IS TRUE ");
		varname1.append("INTO :{user.id}, :{user.Username},:{user.Password}, :{user.firstName}, :{user.lastName}, :{user.organisation.id}, :{user.Administrator}, :{user.superAdministrator}");
		SQL.selectInto(varname1.toString(), new NVPair("user", user), new NVPair("username", userId));

		return user;
	}

	@Override
	public Organisation getCurrentOrganisation(Integer organisationId) {
		return BEANS.get(OrganisationDao.class).findById(organisationId);
	}
}
