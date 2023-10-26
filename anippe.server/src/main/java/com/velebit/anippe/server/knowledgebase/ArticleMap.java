package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.Article;
import org.modelmapper.PropertyMap;

public class ArticleMap extends PropertyMap<ArticleDto, Article> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setTitle(source.getTitle());
        map().setContent(source.getContent());
        map().setDescription(source.getDescription());
        map().setCreatedAt(source.getCreatedAt());

        map().getCategory().setId(source.getCategoryId());
        map().getCategory().setTitle(source.getCategoryName());

        map().getUserCreated().setId(source.getUserId());
        map().getUserCreated().setFirstName(source.getUserFirstName());
        map().getUserCreated().setLastName(source.getUserLastName());
    }
}
