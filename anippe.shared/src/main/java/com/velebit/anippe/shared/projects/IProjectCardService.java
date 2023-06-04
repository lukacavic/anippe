package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IProjectCardService extends IService {
    ProjectCardFormData prepareCreate(ProjectCardFormData formData);

    ProjectCardFormData create(ProjectCardFormData formData);

    ProjectCardFormData load(ProjectCardFormData formData);

    ProjectCardFormData store(ProjectCardFormData formData);
}
