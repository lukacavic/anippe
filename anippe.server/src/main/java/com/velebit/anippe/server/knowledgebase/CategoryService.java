package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.CategoryFormData;
import com.velebit.anippe.shared.knowledgebase.ICategoryService;

public class CategoryService implements ICategoryService {
    @Override
    public CategoryFormData prepareCreate(CategoryFormData formData) {
        return formData;
    }

    @Override
    public CategoryFormData create(CategoryFormData formData) {
        return formData;
    }

    @Override
    public CategoryFormData load(CategoryFormData formData) {
        return formData;
    }

    @Override
    public CategoryFormData store(CategoryFormData formData) {
        return formData;
    }
}
