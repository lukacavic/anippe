package com.velebit.anippe.client.projects.settings.support;

import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class SupportSettingsNodePage extends AbstractPageWithNodes {
    private Project project;

    public SupportSettingsNodePage(Project project) {
        this.project = project;
    }

    @Override
    protected boolean getConfiguredShowTileOverview() {
        return true;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new DepartmentsTablePage(project));
        pageList.add(new PredefinedRepliesTablePage(project));
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Support");
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Info;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Info;
    }
}
