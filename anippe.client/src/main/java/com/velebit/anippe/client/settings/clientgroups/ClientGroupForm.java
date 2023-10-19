package com.velebit.anippe.client.settings.clientgroups;

import com.velebit.anippe.client.settings.clientgroups.ClientGroupForm.MainBox.CancelButton;
import com.velebit.anippe.client.settings.clientgroups.ClientGroupForm.MainBox.GroupBox;
import com.velebit.anippe.client.settings.clientgroups.ClientGroupForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.sharedgroups.ClientGroupFormData;
import com.velebit.anippe.shared.settings.sharedgroups.IClientGroupService;
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

@FormData(value = ClientGroupFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ClientGroupForm extends AbstractForm {

    private Integer clientGroupId;

    @FormData
    public Integer getClientGroupId() {
        return clientGroupId;
    }
    @FormData
    public void setClientGroupId(Integer clientGroupId) {
        this.clientGroupId = clientGroupId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ClientGroup");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
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
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                public int getLabelWidthInPixel() {
                    return 80;
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
            ClientGroupFormData formData = new ClientGroupFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientGroupService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ClientGroupFormData formData = new ClientGroupFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientGroupService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ClientGroupFormData formData = new ClientGroupFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientGroupService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            setSubTitle(TEXTS.get("ViewEntry"));
        }

        @Override
        protected void execStore() {
            ClientGroupFormData formData = new ClientGroupFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientGroupService.class).store(formData);
            importFormData(formData);
        }
    }
}
