package com.velebit.anippe.ui.html;

import com.velebit.anippe.client.config.PortalSimulateConfigProperty;
import com.velebit.anippe.shared.auth.ILoginService;
import com.velebit.anippe.shared.beans.User;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.security.Crypter;
import org.eclipse.scout.rt.platform.security.ICredentialVerifier;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.shared.TunnelToServer;

import javax.security.auth.Subject;
import java.io.IOException;
import java.util.concurrent.Callable;

@TunnelToServer
public class DataSourceCredentialVerifier extends Crypter implements IAnippeCredentialVerifier {

    @Override
    public int verify(String username, char[] password, String subdomain) throws IOException {
        Subject subject = new Subject();
        subject.getPrincipals().add(new SimplePrincipal(username));
        subject.setReadOnly();

        RunContext runContext = RunContexts.copyCurrent(true).withSubject(subject);

        Object result = null;

        boolean isUserLogin = checkIsUserLogin(subdomain);

        result = findUser(username, password, runContext, subdomain);

        return validateLogin(result, isUserLogin);
    }

    private boolean checkIsUserLogin(String subdomain) {
        boolean isSimulatePortal = CONFIG.getPropertyValue(PortalSimulateConfigProperty.class);

        if (isSimulatePortal)
            return false;

        return subdomain != null && !subdomain.equalsIgnoreCase("portal");
    }

    private int validateLogin(Object result, boolean isUserLogin) {
        if (isUserLogin) {
            User user = (User) result;

            if (user != null && user.getId() != null) {
                return ICredentialVerifier.AUTH_OK;
            }
        }

        return ICredentialVerifier.AUTH_FORBIDDEN;
    }

    private User findUser(String username, char[] password, RunContext runContext, String subdomain) {
        User result = runContext.call(new Callable<User>() {

            @Override
            public User call() throws Exception {
                return BEANS.get(ILoginService.class).getUser(username, new String(password), subdomain);
            }

        });

        return result;
    }

}
