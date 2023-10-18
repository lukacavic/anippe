package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.gantt.AbstractGanttField;
import com.velebit.anippe.client.gantt.GanttItem;
import com.velebit.anippe.client.projects.OverviewForm.MainBox.GroupBox;
import com.velebit.anippe.shared.projects.IOverviewService;
import com.velebit.anippe.shared.projects.OverviewFormData;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;

import java.util.Collection;
import java.util.Date;

@FormData(value = OverviewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OverviewForm extends AbstractForm {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Overview");
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
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).store(formData);
            importFormData(formData);
        }
    }
}
