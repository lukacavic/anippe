package com.velebit.anippe.server.config;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class ApplicationKeyConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
        return "app.key";
    }

    public String description() {
        return "Encryption Key";
    }
}
