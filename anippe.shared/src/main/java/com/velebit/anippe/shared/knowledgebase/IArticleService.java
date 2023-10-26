package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IArticleService extends IService {
    ArticleFormData prepareCreate(ArticleFormData formData);

    ArticleFormData create(ArticleFormData formData);

    ArticleFormData load(ArticleFormData formData);

    ArticleFormData store(ArticleFormData formData);

    void delete(Integer articleId);

    String fetchContent(Long articleId);
}
