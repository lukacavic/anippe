package com.velebit.anippe.client.projects;

import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class ProjectNodePage extends AbstractPageWithNodes {

    private Project project;

    public ProjectNodePage(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    @Override
    protected boolean getConfiguredExpanded() {
        return true;
    }

    @Override
    protected boolean getConfiguredShowTileOverview() {
        return true;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return project.getName();
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Email;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new OverviewNodePage(project));
    }
}
