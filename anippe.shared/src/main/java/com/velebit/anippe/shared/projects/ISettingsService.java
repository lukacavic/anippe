package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ISettingsService extends IService {
    SettingsFormData prepareCreate(SettingsFormData formData);

    SettingsFormData create(SettingsFormData formData);

    SettingsFormData load(SettingsFormData formData);

    SettingsFormData store(SettingsFormData formData);
}
