package com.velebit.anippe.client.work;

import com.velebit.anippe.client.projects.TasksForm;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class TasksNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("MyTasks");
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
        super.execInitDetailForm();
        TasksForm form =  (TasksForm) getDetailForm();
        form.setMyTasks(true);
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }
}
