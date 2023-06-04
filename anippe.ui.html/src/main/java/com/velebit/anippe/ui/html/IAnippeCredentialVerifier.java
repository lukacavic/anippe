package com.velebit.anippe.ui.html;

import org.eclipse.scout.rt.platform.ApplicationScoped;

import java.io.IOException;

@FunctionalInterface
@ApplicationScoped
public interface IAnippeCredentialVerifier {

    int AUTH_OK = 1 << 0;

    int AUTH_FORBIDDEN = 1 << 1;

    int AUTH_FAILED = 1 << 2;

    int AUTH_CREDENTIALS_REQUIRED = 1 << 3;

    int verify(String username, char[] password, String subdomain) throws IOException;
}
