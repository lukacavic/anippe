package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.knowledgebase.CategoryFormData;
import com.velebit.anippe.shared.knowledgebase.ICategoryService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class CategoryService implements ICategoryService {
    @Override
    public CategoryFormData prepareCreate(CategoryFormData formData) {
        return formData;
    }

    @Override
    public CategoryFormData create(CategoryFormData formData) {
        SQL.insert("INSERT INTO knowledge_categories (name, description, project_id, organisation_id) VALUES (:Name, :Description, :projectId, :organisationId)", formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return formData;
    }

    @Override
    public CategoryFormData load(CategoryFormData formData) {
        SQL.selectInto("SELECT name, description FROM knowledge_categories WHERE id = :categoryId INTO :Name, :Description", formData);

        return formData;
    }

    @Override
    public CategoryFormData store(CategoryFormData formData) {
        SQL.update("UPDATE knowledge_categories SET name = :Name, description = :Description WHERE id = :categoryId", formData);

        return formData;
    }
}
