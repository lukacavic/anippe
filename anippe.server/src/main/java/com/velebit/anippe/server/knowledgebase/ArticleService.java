package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.knowledgebase.ArticleFormData;
import com.velebit.anippe.shared.knowledgebase.IArticleService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ArticleService extends AbstractService implements IArticleService {
    @Override
    public ArticleFormData prepareCreate(ArticleFormData formData) {
        return formData;
    }

    @Override
    public ArticleFormData create(ArticleFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO knowledge_articles ");
        varname1.append("            (organisation_id, ");
        varname1.append("             user_created_id, ");
        varname1.append("             title, ");
        varname1.append("             description, ");
        varname1.append("             content, ");
        varname1.append("             category_id, ");
        varname1.append("             created_at, ");
        varname1.append("             project_id ) ");
        varname1.append("VALUES      (:organisationId, ");
        varname1.append("             :userCreatedId, ");
        varname1.append("             :Title, ");
        varname1.append("             :Description, ");
        varname1.append("             :Content, ");
        varname1.append("             :Category, ");
        varname1.append("             Now(), ");
        varname1.append("             :projectId )");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userCreatedId", getCurrentUserId()));

        return formData;
    }

    @Override
    public ArticleFormData load(ArticleFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT title, content, category_id, description ");
        varname1.append("FROM knowledge_articles ");
        varname1.append("WHERE id = :articleId ");
        varname1.append("INTO :Title, :Content, :Category, :Description");
        SQL.selectInto(varname1.toString(), formData);
        return formData;
    }

    @Override
    public ArticleFormData store(ArticleFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("update knowledge_articles ");
        varname1.append("SET title       = :Title, ");
        varname1.append("    category_id = :Category, ");
        varname1.append("    description = :Description, ");
        varname1.append("    content     = :Content ");
        varname1.append("WHERE id = :articleId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public void delete(Integer articleId) {
        SQL.update("UPDATE knowledge_articles SET deleted_at = now() WHERE id = :articleId", new NVPair("articleId", articleId));
    }

    @Override
    public String fetchContent(Long articleId) {
        StringHolder holder = new StringHolder();

        SQL.selectInto("SELECT content FROM knowledge_articles WHERE id = :articleId INTO :holder", new NVPair("articleId", articleId), new NVPair("holder", holder));

        return holder.getValue();
    }
}
