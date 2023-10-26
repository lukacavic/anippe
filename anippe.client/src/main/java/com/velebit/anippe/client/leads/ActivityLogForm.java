package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.leads.ActivityLogForm.MainBox.CancelButton;
import com.velebit.anippe.client.leads.ActivityLogForm.MainBox.GroupBox;
import com.velebit.anippe.client.leads.ActivityLogForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.ActivityLogFormData;
import com.velebit.anippe.shared.leads.IActivityLogService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = ActivityLogFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ActivityLogForm extends AbstractForm {

    private Integer leadId;
    private Integer activityId;

    @FormData
    public Integer getActivityId() {
        return activityId;
    }

    @FormData
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @FormData
    public Integer getLeadId() {
        return leadId;
    }

    @FormData
    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ActivityLog");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.History;
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

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public GroupBox.ContentField getContentField() {
        return getFieldByClass(GroupBox.ContentField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 500;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class ContentField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("AddActivity");
                }

                @Override
                public boolean isMandatory() {
                    return true;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_ON_FIELD;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 4;
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ActivityLogFormData formData = new ActivityLogFormData();
            exportFormData(formData);
            formData = BEANS.get(IActivityLogService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ActivityLogFormData formData = new ActivityLogFormData();
            exportFormData(formData);
            formData = BEANS.get(IActivityLogService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ActivityLogFormData formData = new ActivityLogFormData();
            exportFormData(formData);
            formData = BEANS.get(IActivityLogService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ActivityLogFormData formData = new ActivityLogFormData();
            exportFormData(formData);
            formData = BEANS.get(IActivityLogService.class).store(formData);
            importFormData(formData);
        }
    }
}
