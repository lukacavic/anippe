package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.CreateTaskCheckListForm.MainBox.CancelButton;
import com.velebit.anippe.client.tasks.CreateTaskCheckListForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.CreateTaskCheckListForm.MainBox.OkButton;
import com.velebit.anippe.shared.tasks.CreateTaskCheckListFormData;
import com.velebit.anippe.shared.tasks.ICreateTaskCheckListService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = CreateTaskCheckListFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CreateTaskCheckListForm extends AbstractForm {

    private Integer taskId;

    @FormData
    public Integer getTaskId() {
        return taskId;
    }

    @FormData
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("CreateTaskCheckList");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Override
    protected void execStored() {
        super.execStored();

        NotificationHelper.showSaveSuccessNotification();

        doClose();
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
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
            return 600;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
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
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Save");
            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CreateTaskCheckListFormData formData = new CreateTaskCheckListFormData();
            exportFormData(formData);
            formData = BEANS.get(ICreateTaskCheckListService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            CreateTaskCheckListFormData formData = new CreateTaskCheckListFormData();
            exportFormData(formData);
            formData = BEANS.get(ICreateTaskCheckListService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CreateTaskCheckListFormData formData = new CreateTaskCheckListFormData();
            exportFormData(formData);
            formData = BEANS.get(ICreateTaskCheckListService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            CreateTaskCheckListFormData formData = new CreateTaskCheckListFormData();
            exportFormData(formData);
            formData = BEANS.get(ICreateTaskCheckListService.class).store(formData);
            importFormData(formData);
        }
    }
}
