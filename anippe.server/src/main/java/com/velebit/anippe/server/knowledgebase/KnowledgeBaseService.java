package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData;

public class KnowledgeBaseService implements IKnowledgeBaseService {
    @Override
    public KnowledgeBaseFormData prepareCreate(KnowledgeBaseFormData formData) {
        return formData;
    }

    @Override
    public KnowledgeBaseFormData create(KnowledgeBaseFormData formData) {
        return formData;
    }

    @Override
    public KnowledgeBaseFormData load(KnowledgeBaseFormData formData) {
        return formData;
    }

    @Override
    public KnowledgeBaseFormData store(KnowledgeBaseFormData formData) {
        return formData;
    }
}
