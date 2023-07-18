package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.SupportForm.MainBox.GroupBox;
import com.velebit.anippe.shared.projects.ISupportService;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.SupportFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = SupportFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SupportForm extends AbstractForm {

    private Project project;
    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

        }

    }

    public void startClient() {
        startInternal(new ClientHandler());
    }

    public class ClientHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            SupportFormData formData = new SupportFormData();
            exportFormData(formData);
            formData = BEANS.get(ISupportService.class).loadClients(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            //Hide show fields...
        }
    }

}
