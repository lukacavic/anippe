package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.beans.Article;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

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
    public List<KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData> fetchArticles() {
        BeanArrayHolder<Article> holder = new BeanArrayHolder<Article>(Article.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT a.id, ");
        varname1.append("       a.title, ");
        varname1.append("       a.content ");
        varname1.append("FROM   knowledge_articles a, users u ");
        varname1.append("WHERE  a.deleted_at IS NULL AND u.id = a.user_created_id");
        varname1.append("into   :{articles.id}, ");
        varname1.append("       :{articles.title}, ");
        varname1.append("       :{articles.content} ");
        SQL.selectInto(varname1.toString(), new NVPair("articles", holder));

        List<KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData> rows = CollectionUtility.emptyArrayList();

        for (Article article : holder.getBeans()) {
            KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData row = new KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData();
            row.setArticle(article);

            rows.add(row);
        }

        return rows;
    }
}
