package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class OverviewService implements IOverviewService {
    @Override
    public OverviewFormData prepareCreate(OverviewFormData formData) {
        return formData;
    }

    @Override
    public OverviewFormData create(OverviewFormData formData) {
        return formData;
    }

    @Override
    public OverviewFormData load(OverviewFormData formData) {
        return formData;
    }

    @Override
    public OverviewFormData store(OverviewFormData formData) {
        return formData;
    }
}
