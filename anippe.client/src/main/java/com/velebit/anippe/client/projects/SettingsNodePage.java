package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.settings.LeadSettingsNodePage;
import com.velebit.anippe.client.projects.settings.support.PredefinedRepliesTablePage;
import com.velebit.anippe.client.projects.settings.support.SupportSettingsNodePage;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class SettingsNodePage extends AbstractPageWithNodes {
    private Project project;

    public SettingsNodePage(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected boolean getConfiguredShowTileOverview() {
        return true;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new SupportSettingsNodePage(project));
        pageList.add(new LeadSettingsNodePage(getProject()));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Settings");
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Gear;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Gear;
    }

}
