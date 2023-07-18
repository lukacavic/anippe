package com.velebit.anippe.shared.email;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Set;

@TunnelToServer
public interface IEmailService extends IService {
    EmailFormData prepareCreate(EmailFormData formData);

    EmailFormData create(EmailFormData formData);

    boolean isEmailValid(String email);

    boolean isEmailValid(Set<String> rawValue);
}
