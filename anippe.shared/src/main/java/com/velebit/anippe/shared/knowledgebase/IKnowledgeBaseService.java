package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IKnowledgeBaseService extends IService {
    KnowledgeBaseFormData prepareCreate(KnowledgeBaseFormData formData);

    KnowledgeBaseFormData create(KnowledgeBaseFormData formData);

    KnowledgeBaseFormData load(KnowledgeBaseFormData formData);

    KnowledgeBaseFormData store(KnowledgeBaseFormData formData);
}
