package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.Article;
import com.velebit.anippe.shared.knowledgebase.ArticleRequest;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

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

    @Override
    public List<ArticlesTableRowData> fetchArticles() {
        List<Article> articles = BEANS.get(ArticleDao.class).get(new ArticleRequest());

        List<ArticlesTableRowData> rows = CollectionUtility.emptyArrayList();

        for (Article article : articles) {
            ArticlesTableRowData row = new ArticlesTableRowData();
            row.setArticle(article);

            rows.add(row);
        }

        return rows;
    }
}
