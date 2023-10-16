package com.velebit.anippe.server;

import com.velebit.anippe.shared.ModuleActionNotification;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.organisations.Organisation;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;

import java.io.Serializable;

public abstract class AbstractService {

    protected final String FILE_SEPARATOR = System.getProperty("file.separator");

    protected Organisation getCurrentOrganisation() {
        return ServerSession.get().getCurrentOrganisation();
    }

    protected Integer getCurrentOrganisationId() {
        return getCurrentOrganisation().getId();
    }

    protected User getCurrentUser() {
        return ServerSession.get().getCurrentUser();
    }

    protected Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }

    protected <T extends Serializable> void emitModuleEvent(Class<?> type, Object source, int changeStatus) {
        ModuleActionNotification notification = new ModuleActionNotification(type, source, changeStatus, getCurrentOrganisationId(), getCurrentUser());
        BEANS.get(ClientNotificationRegistry.class).putForAllSessions(notification);
    }
}
