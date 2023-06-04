package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class ProjectCardService implements IProjectCardService {
    @Override
    public ProjectCardFormData prepareCreate(ProjectCardFormData formData) {
        return formData;
    }

    @Override
    public ProjectCardFormData create(ProjectCardFormData formData) {
        return formData;
    }

    @Override
    public ProjectCardFormData load(ProjectCardFormData formData) {
        return formData;
    }

    @Override
    public ProjectCardFormData store(ProjectCardFormData formData) {
        return formData;
    }
}
