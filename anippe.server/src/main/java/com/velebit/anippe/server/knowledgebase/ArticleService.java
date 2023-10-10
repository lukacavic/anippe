package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.knowledgebase.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
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
        varname1.append("             content, ");
        varname1.append("             category_id, ");
        varname1.append("             created_at, ");
        varname1.append("             project_id ) ");
        varname1.append("VALUES      (:organisationId, ");
        varname1.append("             :userCreatedId, ");
        varname1.append("             :Title, ");
        varname1.append("             :Content, ");
        varname1.append("             :Category, ");
        varname1.append("             Now(), ");
        varname1.append("             :projectId )");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userCreatedId", getCurrentUserId()));

        return formData;
    }

    @Override
    public ArticleFormData load(ArticleFormData formData) {
        return formData;
    }

    @Override
    public ArticleFormData store(ArticleFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("update knowledge_articles ");
        varname1.append("set title       = :Title, ");
        varname1.append("    category_id = :Category, ");
        varname1.append("    content     = :Content ");
        varname1.append("WHERE id = :articleId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
