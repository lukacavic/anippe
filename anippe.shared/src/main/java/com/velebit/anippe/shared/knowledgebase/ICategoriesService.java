package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ICategoriesService extends IService {
    CategoriesFormData prepareCreate(CategoriesFormData formData);

    CategoriesFormData create(CategoriesFormData formData);

    CategoriesFormData load(CategoriesFormData formData);

    CategoriesFormData store(CategoriesFormData formData);

    List<CategoriesFormData.CategoriesTable.CategoriesTableRowData> fetchCategories(Integer projectId);
}
