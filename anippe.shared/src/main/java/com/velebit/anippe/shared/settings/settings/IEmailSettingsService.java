package com.velebit.anippe.shared.settings.settings;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IEmailSettingsService extends IService {
    EmailSettingsFormData load(EmailSettingsFormData formData);

    EmailSettingsFormData store(EmailSettingsFormData formData);

    String sendTestEmail(EmailSettingsFormData formData);
}
