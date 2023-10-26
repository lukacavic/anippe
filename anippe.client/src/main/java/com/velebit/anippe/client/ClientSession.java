package com.velebit.anippe.client;

import com.velebit.anippe.client.administration.AdministrationDesktop;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.organisations.Organisation;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.shared.services.common.code.CODES;

import java.util.Locale;

/**
 * @author lukacavic
 */
public class ClientSession extends AbstractClientSession {

    public ClientSession() {
        super(true);
    }

    /**
     * @return The {@link IClientSession} which is associated with the current thread, or {@code null} if not found.
     */
    public static ClientSession get() {
        return ClientSessionProvider.currentSession(ClientSession.class);
    }

    @Override
    protected void execLoadSession() {
        //pre-load all known code types
        CODES.getAllCodeTypes("com.velebit.anippe.shared");
        initializeSharedVariables();

        User user = getCurrentUser();

        IDesktop desktop = findDesktopByLoginType(user);

        setLocale(new Locale("hr"));

        setDesktop(desktop);
    }

    public IDesktop findDesktopByLoginType(User user) {
        return user.isSuperAdministrator() ? new AdministrationDesktop() : new Desktop();
    }

    public User getCurrentUser() {
        return getSharedContextVariable("CURRENT_USER", User.class);
    }

    public Organisation getCurrentOrganisation() {
        return getSharedContextVariable("CURRENT_ORGANISATION", Organisation.class);
    }

}
