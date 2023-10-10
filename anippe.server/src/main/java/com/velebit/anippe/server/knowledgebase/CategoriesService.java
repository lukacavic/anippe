package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.CategoriesFormData;
import com.velebit.anippe.shared.knowledgebase.ICategoriesService;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class CategoriesService implements ICategoriesService {
    @Override
    public CategoriesFormData prepareCreate(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public CategoriesFormData create(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public CategoriesFormData load(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public CategoriesFormData store(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public List<CategoriesFormData.CategoriesTable.CategoriesTableRowData> fetchCategories(Integer projectId) {
        return CollectionUtility.emptyArrayList();
    }
}
