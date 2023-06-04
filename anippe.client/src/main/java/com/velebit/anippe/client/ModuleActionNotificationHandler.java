package com.velebit.anippe.client;

import com.velebit.anippe.shared.ModuleActionNotification;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.datachange.ItemDataChangeEvent;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;

public class ModuleActionNotificationHandler implements INotificationHandler<ModuleActionNotification> {

    @Override
    public void handleNotification(ModuleActionNotification notification) {
        ModelJobs.schedule(() -> {

            // Check is session active and organisationId matches.
            if (ClientSession.get() != null && ClientSession.get().isActive()) {

                Integer organisationId = ClientSession.get().getCurrentOrganisation().getId();

                if (!organisationId.equals(notification.getOrganisationId())) return;

                ItemDataChangeEvent event = new ItemDataChangeEvent(notification.getSource(), notification.getType(), notification.getChangeStatus(), null, notification.getData());
                IDesktop.CURRENT.get().fireDataChangeEvent(event);

            }
        }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
    }

}
