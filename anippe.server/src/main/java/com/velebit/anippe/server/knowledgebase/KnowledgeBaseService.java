package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.knowledgebase.Article;
import com.velebit.anippe.shared.knowledgebase.ArticleRequest;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.List;

public class KnowledgeBaseService extends AbstractService implements IKnowledgeBaseService {

    @Override
    public List<Article> fetchArticles(Integer projectId, Long typeId) {
        ArticleRequest request = new ArticleRequest();
        request.setProjectId(projectId);
        request.setUserId(typeId.equals(2L) ? getCurrentUserId() : null);

        return BEANS.get(ArticleDao.class).get(request);
    }
}
