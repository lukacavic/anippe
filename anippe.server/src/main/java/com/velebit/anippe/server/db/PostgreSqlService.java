package com.velebit.anippe.server.db;

import com.velebit.anippe.server.config.DatabaseProperties;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.server.jdbc.postgresql.AbstractPostgreSqlService;
import org.eclipse.scout.rt.server.jdbc.postgresql.PostgreSqlStyle;
import org.eclipse.scout.rt.server.jdbc.style.ISqlStyle;

public class PostgreSqlService extends AbstractPostgreSqlService {
    @Override
    protected Class<? extends ISqlStyle> getConfiguredSqlStyle() {
        return PostgreSqlStyle.class;
    }

    @Override
    protected String getConfiguredJdbcMappingName() {
        return CONFIG.getPropertyValue(DatabaseProperties.URLProperty.class);
    }

    @Override
    protected String getConfiguredPassword() {
        return CONFIG.getPropertyValue(DatabaseProperties.PasswordProperty.class);
    }

    @Override
    protected String getConfiguredUsername() {
        return CONFIG.getPropertyValue(DatabaseProperties.UsernameProperty.class);
    }
}
