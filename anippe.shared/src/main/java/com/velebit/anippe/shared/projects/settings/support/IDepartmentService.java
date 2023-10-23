package com.velebit.anippe.shared.projects.settings.support;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IDepartmentService extends IService {
    DepartmentFormData prepareCreate(DepartmentFormData formData);

    DepartmentFormData create(DepartmentFormData formData);

    DepartmentFormData load(DepartmentFormData formData);

    DepartmentFormData store(DepartmentFormData formData);

    List<String> fetchImapFolders(DepartmentFormData formData);

    boolean checkImapConnection(DepartmentFormData formData);

    void delete(Integer departmentId);
}
