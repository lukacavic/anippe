package com.velebit.anippe.client.projects;

import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class GanttNodePage extends AbstractPageWithNodes {
    private Project project;

    public GanttNodePage(Project project) {
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
        return TEXTS.get("Gantt");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Chart;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Chart;
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return GanttForm.class;
    }

    @Override
    protected void execInitDetailForm() {
        GanttForm form = (GanttForm) getDetailForm();
        form.setProjectId(getProject().getId());
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }


}
