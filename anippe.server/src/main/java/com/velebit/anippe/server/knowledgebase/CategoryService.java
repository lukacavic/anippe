package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class CategoryService implements ICategoryService {
    @Override
    public CategoryFormData prepareCreate(CategoryFormData formData) {
        if (!ACCESS.check(new CreateCategoryPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public CategoryFormData create(CategoryFormData formData) {
        if (!ACCESS.check(new CreateCategoryPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public CategoryFormData load(CategoryFormData formData) {
        if (!ACCESS.check(new ReadCategoryPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public CategoryFormData store(CategoryFormData formData) {
        if (!ACCESS.check(new UpdateCategoryPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }
}
