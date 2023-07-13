package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IProjectService extends IService {
    ProjectFormData prepareCreate(ProjectFormData formData);

    ProjectFormData create(ProjectFormData formData);

    ProjectFormData load(ProjectFormData formData);

    ProjectFormData store(ProjectFormData formData);

    void delete(Integer projectId);
}
