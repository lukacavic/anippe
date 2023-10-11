package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ICategoryService extends IService {
    CategoryFormData prepareCreate(CategoryFormData formData);

    CategoryFormData create(CategoryFormData formData);

    CategoryFormData load(CategoryFormData formData);

    CategoryFormData store(CategoryFormData formData);
}
