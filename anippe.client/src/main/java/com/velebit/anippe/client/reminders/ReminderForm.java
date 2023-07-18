package com.velebit.anippe.client.reminders;

import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.CancelButton;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.GroupBox;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.reminders.IReminderService;
import com.velebit.anippe.shared.reminders.ReminderFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = ReminderFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ReminderForm extends AbstractForm {

    private Integer relatedId;
    private Integer relatedType;

    private Integer reminderId;

    @FormData
    public Integer getReminderId() {
        return reminderId;
    }

    @FormData
    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    @FormData
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    @Override
    protected void execStored() {
        NotificationHelper.showSaveSuccessNotification();
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Reminder");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Clock;
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

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

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
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).store(formData);
            importFormData(formData);
        }
    }
}
