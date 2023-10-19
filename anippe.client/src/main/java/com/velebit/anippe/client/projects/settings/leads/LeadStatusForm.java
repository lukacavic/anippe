package com.velebit.anippe.client.projects.settings.leads;

import com.velebit.anippe.client.projects.settings.leads.LeadStatusForm.MainBox.CancelButton;
import com.velebit.anippe.client.projects.settings.leads.LeadStatusForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.settings.leads.LeadStatusForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.settings.leads.ILeadStatusService;
import com.velebit.anippe.shared.projects.settings.leads.LeadStatusFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.colorfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = LeadStatusFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadStatusForm extends AbstractForm {

    private Integer projectId;
    private Integer leadStatusId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @FormData
    public Integer getLeadStatusId() {
        return leadStatusId;
    }

    @FormData
    public void setLeadStatusId(Integer leadStatusId) {
        this.leadStatusId = leadStatusId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("LeadStatus");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Lead;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 500;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(0)
            public class ColorField extends AbstractColorField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Color");
                }
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                public int getLabelWidthInPixel() {
                    return 80;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            LeadStatusFormData formData = new LeadStatusFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadStatusService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadStatusFormData formData = new LeadStatusFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadStatusService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            LeadStatusFormData formData = new LeadStatusFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadStatusService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadStatusFormData formData = new LeadStatusFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadStatusService.class).store(formData);
            importFormData(formData);
        }
    }
}
