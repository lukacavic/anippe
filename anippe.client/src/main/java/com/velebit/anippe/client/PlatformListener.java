package com.velebit.anippe.client;

import com.velebit.anippe.client.extensions.ScoutButtonsExtension;
import com.velebit.anippe.client.extensions.ScoutFieldsExtension;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

public class PlatformListener implements IPlatformListener {
    @Override
    public void stateChanged(PlatformEvent event) {
        if (event.getState() == IPlatform.State.PlatformStarted) {
            BEANS.get(IExtensionRegistry.class).register(ScoutButtonsExtension.class);
            BEANS.get(IExtensionRegistry.class).register(ScoutFieldsExtension.class);
        }
    }
}
