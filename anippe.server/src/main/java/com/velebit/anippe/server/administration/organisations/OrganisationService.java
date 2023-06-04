package com.velebit.anippe.server.administration.organisations;

import com.velebit.anippe.shared.administration.organisations.*;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class OrganisationService implements IOrganisationService {
    @Override
    public OrganisationFormData prepareCreate(OrganisationFormData formData) {
        return formData;
    }

    @Override
    public OrganisationFormData create(OrganisationFormData formData) {

        BEANS.get(OrganisationCreator.class).create(formData);

        return formData;
    }

    @Override
    public OrganisationFormData load(OrganisationFormData formData) {
        return formData;
    }

    @Override
    public OrganisationFormData store(OrganisationFormData formData) {
        return formData;
    }
}
