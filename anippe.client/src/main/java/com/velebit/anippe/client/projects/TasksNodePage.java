package com.velebit.anippe.client.projects;

import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class TasksNodePage extends AbstractPageWithNodes {
    private Project project;

    public TasksNodePage(Project project) {
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
        return TEXTS.get("Tasks");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Tasks;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Tasks;
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return TasksForm.class;
    }

    @Override
    protected void execInitDetailForm() {
        TasksForm form = (TasksForm) getDetailForm();
        form.setProject(getProject());
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }
}
