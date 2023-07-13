package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.DocumentsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.projects.DocumentsFormData;
import com.velebit.anippe.shared.projects.IDocumentsService;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = DocumentsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class DocumentsForm extends AbstractForm {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Documents");
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            DocumentsFormData formData = new DocumentsFormData();
            exportFormData(formData);
            formData = BEANS.get(IDocumentsService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            DocumentsFormData formData = new DocumentsFormData();
            exportFormData(formData);
            formData = BEANS.get(IDocumentsService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            DocumentsFormData formData = new DocumentsFormData();
            exportFormData(formData);
            formData = BEANS.get(IDocumentsService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            DocumentsFormData formData = new DocumentsFormData();
            exportFormData(formData);
            formData = BEANS.get(IDocumentsService.class).store(formData);
            importFormData(formData);
        }
    }
}
