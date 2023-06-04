package com.velebit.anippe.server.config;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class DatabaseProperties {

    public static class URLProperty extends AbstractStringConfigProperty {

        @Override
        public String getKey() {
            return "db.url";
        }

        @Override
        public String description() {
            return "DB Connection";
        }
    }

    public static class UsernameProperty extends AbstractStringConfigProperty {

        @Override
        public String getKey() {
            return "db.username";
        }

        @Override
        public String description() {
            return "DB username";
        }
    }

    public static class PasswordProperty extends AbstractStringConfigProperty {

        @Override
        public String getKey() {

            return "db.password";
        }

        @Override
        public String description() {

            return "DB password";
        }
    }

}
