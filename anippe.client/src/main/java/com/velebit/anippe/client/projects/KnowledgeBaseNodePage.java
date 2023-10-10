package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.documents.DocumentsForm;
import com.velebit.anippe.client.knowledgebase.ArticleForm;
import com.velebit.anippe.client.knowledgebase.KnowledgeBaseForm;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Set;

public class KnowledgeBaseNodePage extends AbstractPageWithNodes {
    private Project project;

    public KnowledgeBaseNodePage(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("KnowledgeBase");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Book;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Book;
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return KnowledgeBaseForm.class;
    }

    @Override
    protected boolean getConfiguredNavigateButtonsVisible() {
        return false;
    }


    @Override
    protected void execInitDetailForm() {
        KnowledgeBaseForm form = (KnowledgeBaseForm) getDetailForm();
        form.setRelatedId(getProject().getId());
        form.setRelatedType(Constants.Related.PROJECT);
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }


}
