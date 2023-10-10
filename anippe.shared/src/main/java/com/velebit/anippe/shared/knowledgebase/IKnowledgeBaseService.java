package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IKnowledgeBaseService extends IService {
    KnowledgeBaseFormData prepareCreate(KnowledgeBaseFormData formData);

    KnowledgeBaseFormData create(KnowledgeBaseFormData formData);

    KnowledgeBaseFormData load(KnowledgeBaseFormData formData);

    KnowledgeBaseFormData store(KnowledgeBaseFormData formData);

    List<KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData> fetchArticles();
}
