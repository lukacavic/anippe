package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class SettingsService implements ISettingsService {
    @Override
    public SettingsFormData prepareCreate(SettingsFormData formData) {
        return formData;
    }

    @Override
    public SettingsFormData create(SettingsFormData formData) {
        return formData;
    }

    @Override
    public SettingsFormData load(SettingsFormData formData) {
        return formData;
    }

    @Override
    public SettingsFormData store(SettingsFormData formData) {
        return formData;
    }
}
