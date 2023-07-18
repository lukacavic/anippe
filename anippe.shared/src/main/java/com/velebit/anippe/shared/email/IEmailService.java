package com.velebit.anippe.shared.email;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IEmailService extends IService {
    EmailFormData prepareCreate(EmailFormData formData);

    EmailFormData create(EmailFormData formData);

    EmailFormData load(EmailFormData formData);

    EmailFormData store(EmailFormData formData);
}
