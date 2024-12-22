package com.velebit.anippe.client.vaults;

import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
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
import org.eclipse.scout.rt.client.ui.form.fields.mode.AbstractMode;
import org.eclipse.scout.rt.client.ui.form.fields.modeselector.AbstractModeSelectorField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
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

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
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

    public GroupBox.VisibilityModeSelectorField getVisibilityModeSelectorField() {
        return getFieldByClass(GroupBox.VisibilityModeSelectorField.class);
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
            return 900;
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
                protected int getConfiguredLabelWidthInPixel() {
                    return 100;
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

            @Order(2000)
            public class ContentField extends AbstractTextEditorField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Content");
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 100;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

            }

            @Order(2500)
            public class VisibilityModeSelectorField extends AbstractModeSelectorField<java.lang.Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Visibility");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execInitField() {
                    setValue(3L);
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected String getConfiguredFieldStyle() {
                    return FIELD_STYLE_CLASSIC;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 100;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Order(1000)
                @ClassId("86af0a2e-95b0-4bbf-b39c-862655e1f1db")
                public class OnlyAdministratorsMode extends AbstractMode<java.lang.Long> {
                    @Override
                    protected String getConfiguredText() {
                        return "Samo administratori";
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return 1L;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Key;
                    }
                }

                @Order(2000)
                @ClassId("465a3452-9f76-4510-9155-b75cf84d115d")
                public class AllMembersMode extends AbstractMode<java.lang.Long> {
                    @Override
                    protected String getConfiguredText() {
                        return "Svi djelatnici";
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return 2L;
                    }
                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Users1;
                    }
                }

                @Order(3000)
                @ClassId("465a3452-9f76-4510-9155-b75cf84d115d")
                public class OnlyMeMode extends AbstractMode<java.lang.Long> {
                    @Override
                    protected String getConfiguredText() {
                        return "Samo ja (administratori nisu isključeni)";
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return 3L;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.UserCheck;
                    }
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
        protected void execPostLoad() {
            super.execPostLoad();

            setSubTitle(TEXTS.get("ViewEntry"));
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
