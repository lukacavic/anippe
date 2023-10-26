package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.knowledgebase.Article;
import com.velebit.anippe.shared.knowledgebase.ArticleRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class ArticleDao {

    public List<Article> get(ArticleRequest request) {
        BeanArrayHolder<ArticleDto> dto = new BeanArrayHolder<ArticleDto>(ArticleDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT a.id, ");
        varname1.append("       a.title, ");
        varname1.append("       a.created_at, ");
        varname1.append("       a.content, ");
        varname1.append("       a.description, ");
        varname1.append("       c.id, ");
        varname1.append("       c.name, ");
        varname1.append("       u.id, ");
        varname1.append("       u.first_name, ");
        varname1.append("       u.last_name ");
        varname1.append("FROM   knowledge_articles a, ");
        varname1.append("       users u, ");
        varname1.append("       knowledge_categories c ");
        varname1.append("WHERE  a.category_id = c.id ");
        varname1.append("       AND a.user_created_id = u.id ");
        varname1.append("       AND a.deleted_at IS NULL ");
        varname1.append("       AND c.deleted_at IS NULL ");
        varname1.append("       AND a.organisation_id = :organisationId ");

        if (request.getProjectId() != null) {
            varname1.append(" AND a.project_id = :{request.projectId} ");
        }

        varname1.append("INTO ");
        varname1.append("       :{dto.id}, ");
        varname1.append("       :{dto.title}, ");
        varname1.append("       :{dto.createdAt}, ");
        varname1.append("       :{dto.content}, ");
        varname1.append("       :{dto.description}, ");
        varname1.append("       :{dto.categoryId}, ");
        varname1.append("       :{dto.categoryName}, ");
        varname1.append("       :{dto.userId}, ");
        varname1.append("       :{dto.userFirstName}, ");
        varname1.append("       :{dto.userLastName} ");
        SQL.selectInto(varname1.toString(), new NVPair("dto", dto),
                new NVPair("request", request),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("dto", dto), new NVPair("request", request)
        );

        List<ArticleDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Article> articles = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new ArticleMap());
        dtos.forEach(item -> articles.add(mapper.map(item, Article.class)));

        return articles;
    }

}
