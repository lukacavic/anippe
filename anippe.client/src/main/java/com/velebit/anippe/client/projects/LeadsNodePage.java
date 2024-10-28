package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.leads.LeadForm;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class LeadsNodePage extends AbstractPageWithNodes {
    private Project project;

    public LeadsNodePage(Project project) {
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
        return TEXTS.get("Leads");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.UserPlus;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserPlus;
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return LeadsForm.class;
    }

    @Override
    protected void execInitDetailForm() {
        LeadsForm form = (LeadsForm) getDetailForm();
        form.setProjectId(getProject().getId());
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }

    public LeadsForm getCastedForm() {
        return (LeadsForm) getDetailForm();
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("AddLead");
        }

        @Override
        protected void execAction() {
            LeadForm form = new LeadForm();
            form.setProjectId(getProject().getId());
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                getCastedForm().fetchLeads();
            }
        }
    }
}
