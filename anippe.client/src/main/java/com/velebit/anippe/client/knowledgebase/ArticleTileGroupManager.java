package com.velebit.anippe.client.knowledgebase;

import org.eclipse.scout.rt.client.ui.tile.AbstractTileAccordionGroupManager;
import org.eclipse.scout.rt.client.ui.tile.GroupTemplate;

public class ArticleTileGroupManager extends AbstractTileAccordionGroupManager<ArticleTile> {

    public static final Object ID = ArticleTileGroupManager.class;

    private String m_iconId;

    @Override
    public Object getGroupIdByTile(ArticleTile tile) {
        return tile.getArticle().getCategory().getTitle();
    }

    public String getIconId() {
        return m_iconId;
    }

    public void setIconId(String iconId) {
        m_iconId = iconId;
    }

    @Override
    public GroupTemplate createGroupForTile(ArticleTile tile) {
        return new GroupTemplate(tile.getArticle().getCategory().getTitle(), tile.getArticle().getCategory().getTitle()).withIconId(getIconId());
    }

    @Override
    public Object getId() {
        return ID;
    }

}
