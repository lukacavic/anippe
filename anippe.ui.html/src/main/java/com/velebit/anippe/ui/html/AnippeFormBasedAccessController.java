package com.velebit.anippe.ui.html;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.ICredentialVerifier;
import org.eclipse.scout.rt.platform.security.IPrincipalProducer;
import org.eclipse.scout.rt.platform.security.SimplePrincipalProducer;
import org.eclipse.scout.rt.platform.util.Assertions;
import org.eclipse.scout.rt.platform.util.Pair;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.server.commons.authentication.FormBasedAccessController;
import org.eclipse.scout.rt.server.commons.authentication.ServletFilterHelper;
import org.eclipse.scout.rt.server.commons.servlet.HttpServletControl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

public class AnippeFormBasedAccessController extends FormBasedAccessController {

    protected AnippeFormBasedAuthConfig m_config;

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!m_config.isEnabled()) {
            return false;
        }

        if ("/auth".equals(request.getPathInfo())) {
            return handleAuthRequest(request, response);
        }

        return false;
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck", "DuplicatedCode"})
    // required so that overriding subclasses can throw ServletExceptions
    protected boolean handleAuthRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Never cache authentication requests.
        response.setHeader("Cache-Control", "private, no-store, no-cache, max-age=0"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // prevents caching at the proxy server

        // requests on /auth should have the default headers as this might also be
        // called using GET which returns a 404 html page (container dependent)
        BEANS.get(HttpServletControl.class).doDefaults(null, request, response);

        Pair<String, char[]> credentials = readCredentials(request);
        if (credentials == null) {
            handleForbidden(ICredentialVerifier.AUTH_CREDENTIALS_REQUIRED, response);
            return true;
        }

        int status = m_config.getCredentialVerifier().verify(credentials.getLeft(), credentials.getRight(), getSubdomainFromRequest(request));
        if (status != ICredentialVerifier.AUTH_OK) {
            handleForbidden(status, response);
            return true;
        }

        // OWASP: force a new HTTP session to be created.
        ServletFilterHelper helper = BEANS.get(ServletFilterHelper.class);
        helper.invalidateSessionAfterLogin(request);

        // Put authenticated principal onto (new) HTTP session
        Principal principal = m_config.getPrincipalProducer().produce(credentials.getLeft());
        helper.putPrincipalOnSession(request, principal);
        return true;
    }

    /**
     * Method invoked if the user could not be verified. The default implementation
     * waits some time to address brute-force attacks, and sets a 403 HTTP status
     * code.
     *
     * @param status is a {@link ICredentialVerifier} AUTH_* constant
     */
    protected void handleForbidden(int status, HttpServletResponse response) throws IOException {
        if (m_config.getStatus403WaitMillis() > 0L) {
            SleepUtil.sleepSafe(m_config.getStatus403WaitMillis(), TimeUnit.MILLISECONDS);
        }
        response.sendError(javax.servlet.http.HttpServletResponse.SC_FORBIDDEN);
    }

    private String getSubdomainFromRequest(HttpServletRequest request) {
        return request != null ? request.getServerName().split("\\.")[0] : "";
    }

    public AnippeFormBasedAccessController init(AnippeFormBasedAuthConfig config) {
        m_config = config;
        Assertions.assertNotNull(m_config.getCredentialVerifier(), "CredentialVerifier must not be null");
        Assertions.assertNotNull(m_config.getPrincipalProducer(), "PrincipalProducer must not be null");
        return this;
    }

    public static class AnippeFormBasedAuthConfig {

        private boolean m_enabled = true;
        private long m_status403WaitMillis = 500L;
        private IAnippeCredentialVerifier m_credentialVerifier;
        private IPrincipalProducer m_principalProducer = BEANS.get(SimplePrincipalProducer.class);

        public boolean isEnabled() {
            return m_enabled;
        }

        public AnippeFormBasedAuthConfig withEnabled(boolean enabled) {
            m_enabled = enabled;
            return this;
        }

        public IAnippeCredentialVerifier getCredentialVerifier() {
            return m_credentialVerifier;
        }

        /**
         * Sets the {@link ICredentialVerifier} to verify user's credentials.
         */
        public AnippeFormBasedAuthConfig withCredentialVerifier(IAnippeCredentialVerifier credentialVerifier) {
            m_credentialVerifier = credentialVerifier;
            return this;
        }

        public IPrincipalProducer getPrincipalProducer() {
            return m_principalProducer;
        }

        /**
         * Sets the {@link IPrincipalProducer} to produce a {@link Principal} for
         * authenticated users. By default, {@link SimplePrincipalProducer} is used.
         */
        public AnippeFormBasedAuthConfig withPrincipalProducer(IPrincipalProducer principalProducer) {
            m_principalProducer = principalProducer;
            return this;
        }

        public long getStatus403WaitMillis() {
            return m_status403WaitMillis;
        }

        /**
         * Sets the time to wait to respond with a 403 response code. That is a simple
         * mechanism to address brute-force attacks, but may have a negative effect on
         * DoS attacks. By default, this authenticator waits for 500ms.
         */
        public AnippeFormBasedAuthConfig withStatus403WaitMillis(long waitMillis) {
            m_status403WaitMillis = waitMillis;
            return this;
        }
    }
}


