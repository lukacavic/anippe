package com.velebit.anippe.server.platform;

import com.velebit.anippe.server.config.DatabaseProperties;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.flywaydb.core.Flyway;

public class FlywayListener implements IPlatformListener {
    @Override
    public void stateChanged(PlatformEvent event) {
        if(event.getState().equals(IPlatform.State.PlatformStarted)) {
            //runMigrations();
        }
    }

    private void runMigrations() {
        String dbUrl = CONFIG.getPropertyValue(DatabaseProperties.URLProperty.class);
        String dbUsername = CONFIG.getPropertyValue(DatabaseProperties.UsernameProperty.class);
        String dbPassword = CONFIG.getPropertyValue(DatabaseProperties.PasswordProperty.class);

        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .dataSource(dbUrl, dbUsername, dbPassword)
                .load();

        flyway.migrate();
    }
}
