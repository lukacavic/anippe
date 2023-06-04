package com.velebit.anippe.shared.administration.organisations;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IOrganisationService extends IService {
    OrganisationFormData prepareCreate(OrganisationFormData formData);

    OrganisationFormData create(OrganisationFormData formData);

    OrganisationFormData load(OrganisationFormData formData);

    OrganisationFormData store(OrganisationFormData formData);
}
