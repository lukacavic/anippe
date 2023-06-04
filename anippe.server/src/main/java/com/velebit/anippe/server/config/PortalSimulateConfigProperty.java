package com.velebit.anippe.server.config;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;

public class PortalSimulateConfigProperty extends AbstractBooleanConfigProperty {

    @Override
    public String getKey() {
        return "portal.simulate";
    }

    @Override
    public String description() {
        return "Simulate portal in development mode";
    }
}
