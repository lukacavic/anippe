package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.projects.IProjectService;
import com.velebit.anippe.shared.projects.IProjectsService;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class ProjectsOutline extends AbstractOutline {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Projects");
    }

    @Override
    protected String getConfiguredIconId() {
        return Icons.Folder;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        Integer userId = ClientSession.get().getCurrentUser().getId();
        List<Project> projects = BEANS.get(IProjectsService.class).findForUser(userId);

        if (CollectionUtility.isEmpty(projects)) return;
        for (Project project : projects) {
            pageList.add(new ProjectNodePage(project));
        }

    }
}
