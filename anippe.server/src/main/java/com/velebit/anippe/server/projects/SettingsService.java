package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class SettingsService implements ISettingsService {
    @Override
    public SettingsFormData prepareCreate(SettingsFormData formData) {
        if (!ACCESS.check(new CreateSettingsPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public SettingsFormData create(SettingsFormData formData) {
        if (!ACCESS.check(new CreateSettingsPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public SettingsFormData load(SettingsFormData formData) {
        if (!ACCESS.check(new ReadSettingsPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public SettingsFormData store(SettingsFormData formData) {
        if (!ACCESS.check(new UpdateSettingsPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }
}
