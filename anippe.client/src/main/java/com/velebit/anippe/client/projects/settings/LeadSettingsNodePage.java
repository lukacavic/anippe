package com.velebit.anippe.client.projects.settings;

import com.velebit.anippe.client.projects.settings.leads.LeadSourcesTablePage;
import com.velebit.anippe.client.projects.settings.leads.LeadStatusesTablePage;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class LeadSettingsNodePage extends AbstractPageWithNodes {
    private Project project;

    public LeadSettingsNodePage(Project project) {
        this.project = project;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new LeadSourcesTablePage(project));
        pageList.add(new LeadStatusesTablePage(project));
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Leads");
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.UserPlus;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserPlus;
    }
}
