package com.velebit.anippe.server.config;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class AdminProperties {

    public static class AdminUsername extends AbstractStringConfigProperty {

        @Override
        public String getKey() {
            return "admin.username";
        }

        @Override
        public String description() {
            return "Super Admin Username";
        }
    }

    public static class AdminPassword extends AbstractStringConfigProperty {

        @Override
        public String getKey() {
            return "admin.password";
        }

        @Override
        public String description() {
            return "Super Admin Password";
        }
    }

}
