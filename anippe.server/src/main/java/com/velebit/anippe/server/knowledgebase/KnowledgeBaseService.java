package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.knowledgebase.Article;
import com.velebit.anippe.shared.knowledgebase.ArticleRequest;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class KnowledgeBaseService extends AbstractService implements IKnowledgeBaseService {

    @Override
    public List<ArticlesTableRowData> fetchArticles(Integer projectId, Long typeId) {
        ArticleRequest request = new ArticleRequest();
        request.setProjectId(projectId);
        request.setUserId(typeId.equals(2L) ? getCurrentUserId() : null);

        List<Article> articles = BEANS.get(ArticleDao.class).get(request);

        List<ArticlesTableRowData> rows = CollectionUtility.emptyArrayList();

        for (Article article : articles) {
            ArticlesTableRowData row = new ArticlesTableRowData();
            row.setArticle(article);

            rows.add(row);
        }

        return rows;
    }
}
