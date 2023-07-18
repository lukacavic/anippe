package com.velebit.anippe.client.vaults;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.vaults.VaultForm.MainBox.CancelButton;
import com.velebit.anippe.client.vaults.VaultForm.MainBox.GroupBox;
import com.velebit.anippe.client.vaults.VaultForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.vaults.IVaultService;
import com.velebit.anippe.shared.vaults.VaultFormData;
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

@FormData(value = VaultFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class VaultForm extends AbstractForm {

    private Integer relatedId;
    private Integer relatedType;
    private Integer vaultId;

    @FormData
    public Integer getVaultId() {
        return vaultId;
    }

    @FormData
    public void setVaultId(Integer vaultId) {
        this.vaultId = vaultId;
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
    protected String getConfiguredTitle() {
        return TEXTS.get("Vault");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Key;
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

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredWidthInPixel() {
                return 700;
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
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(2000)
            public class ContentField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Content");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
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
            VaultFormData formData = new VaultFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            VaultFormData formData = new VaultFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            VaultFormData formData = new VaultFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            VaultFormData formData = new VaultFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultService.class).store(formData);
            importFormData(formData);
        }
    }
}
