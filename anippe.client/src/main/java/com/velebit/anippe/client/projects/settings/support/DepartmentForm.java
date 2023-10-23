package com.velebit.anippe.client.projects.settings.support;

import com.velebit.anippe.client.common.fields.AbstractEmailField;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.settings.support.DepartmentForm.MainBox.CancelButton;
import com.velebit.anippe.client.projects.settings.support.DepartmentForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.settings.support.DepartmentForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.settings.support.DepartmentFormData;
import com.velebit.anippe.shared.projects.settings.support.IDepartmentService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractProposalField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;
import java.util.Set;

@FormData(value = DepartmentFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class DepartmentForm extends AbstractForm {

    private Integer projectId;
    private Integer departmentId;

    @FormData
    public Integer getDepartmentId() {
        return departmentId;
    }

    @FormData
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Department");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Info;
    }

    public GroupBox.ActiveField getActiveField() {
        return getFieldByClass(GroupBox.ActiveField.class);
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

    public GroupBox.DeleteAfterImportField getDeleteAfterImportField() {
        return getFieldByClass(GroupBox.DeleteAfterImportField.class);
    }

    public GroupBox.EmailImapEmailField getEmailImapEmailField() {
        return getFieldByClass(GroupBox.EmailImapEmailField.class);
    }

    public GroupBox.EmailImapEnabledField getEmailImapEnabledField() {
        return getFieldByClass(GroupBox.EmailImapEnabledField.class);
    }

    public GroupBox.EmailImapEncryptionGroup getEmailImapEncryptionGroup() {
        return getFieldByClass(GroupBox.EmailImapEncryptionGroup.class);
    }

    public GroupBox.EmailImapHostField getEmailImapHostField() {
        return getFieldByClass(GroupBox.EmailImapHostField.class);
    }

    public GroupBox.EmailImapPasswordField getEmailImapPasswordField() {
        return getFieldByClass(GroupBox.EmailImapPasswordField.class);
    }

    public GroupBox.FoldersField getFoldersField() {
        return getFieldByClass(GroupBox.FoldersField.class);
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.EmailImapEncryptionGroup.NoEncryptionButton getNoEncryptionButton() {
        return getFieldByClass(GroupBox.EmailImapEncryptionGroup.NoEncryptionButton.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewDepartment");
    }

    public GroupBox.EmailImapEncryptionGroup.SSLButton getSSLButton() {
        return getFieldByClass(GroupBox.EmailImapEncryptionGroup.SSLButton.class);
    }

    public GroupBox.EmailImapEncryptionGroup.TSLButton getTSLButton() {
        return getFieldByClass(GroupBox.EmailImapEncryptionGroup.TSLButton.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
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
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1500)
            public class EmailImapEmailField extends AbstractEmailField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("IMAPEmail");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(2000)
            public class ActiveField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Active");
                }

                @Override
                protected void execInitField() {
                    setValue(true);
                }
            }

            @Order(3000)
            public class EmailImapEnabledField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("EmailImapEnabled");
                }

                @Override
                protected void execChangedValue() {
                    super.execChangedValue();

                    getEmailImapEncryptionGroup().setEnabled(getValue());
                    getEmailImapHostField().setEnabled(getValue());
                    getEmailImapPasswordField().setEnabled(getValue());
                    getEmailImapEmailField().setEnabled(getValue());
                    getFoldersField().setEnabled(getValue());
                    getDeleteAfterImportField().setEnabled(getValue());

                    if (!getValue()) {
                        getEmailImapEncryptionGroup().setValue(null);
                        getEmailImapHostField().setValue(null);
                        getEmailImapPasswordField().setValue(null);
                        getEmailImapEmailField().setValue(null);
                        getFoldersField().setValue(null);
                        getDeleteAfterImportField().setValue(false);
                    }
                }
            }

            @Order(3500)
            public class EmailImapHostField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("IMAPHost");
                }

                @Override
                protected boolean getConfiguredEnabled() {
                    return false;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(5000)
            public class EmailImapPasswordField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("IMAPPassword");
                }

                @Override
                protected boolean getConfiguredInputMasked() {
                    return true;
                }

                @Override
                protected boolean getConfiguredEnabled() {
                    return false;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(6000)
            public class EmailImapEncryptionGroup extends AbstractRadioButtonGroup<String> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("IMAPEncryption");
                }

                @Override
                protected boolean getConfiguredEnabled() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 1;
                }

                @Order(1000)
                public class TSLButton extends AbstractRadioButton<String> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("TLS");
                    }

                    @Override
                    protected String getConfiguredRadioValue() {
                        return "TLS";
                    }
                }

                @Order(2000)
                public class SSLButton extends AbstractRadioButton<String> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("SSL");
                    }

                    @Override
                    protected String getConfiguredRadioValue() {
                        return "SSL";
                    }
                }

                @Order(3000)
                public class NoEncryptionButton extends AbstractRadioButton<String> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("NoEncryption");
                    }

                    @Override
                    protected String getConfiguredRadioValue() {
                        return null;
                    }
                }
            }

            @Order(7000)
            public class FoldersField extends AbstractProposalField<String> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Folders");
                }

                @Override
                protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                    return ImapFoldersLocalLookupCall.class;
                }

                @Override
                protected boolean getConfiguredEnabled() {
                    return false;
                }

                @Order(1000)
                public class FetchFoldersMenu extends AbstractMenu {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("FetchFolders");
                    }

                    @Override
                    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                        return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Spinner1;
                    }

                    @Override
                    protected void execAction() {
                        DepartmentFormData formData = new DepartmentFormData();
                        exportFormData(formData);
                        List<String> folders = BEANS.get(IDepartmentService.class).fetchImapFolders(formData);

                        ImapFoldersLocalLookupCall c = new ImapFoldersLocalLookupCall();

                        if (!CollectionUtility.isEmpty(folders)) {
                            c.setFolders(folders);
                        }

                        getFoldersField().setLookupCall(c);
                        getFoldersField().reinit();
                    }
                }

            }

            @Order(8000)
            public class DeleteAfterImportField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("DeleteAfterImport");
                }

                @Override
                protected boolean getConfiguredEnabled() {
                    return false;
                }
            }


        }

        @Order(1000)
        public class CheckIMAPConnectionMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("CheckIMAPConnection");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Spinner1;
            }

            @Override
            protected void execAction() {
                DepartmentFormData formData = new DepartmentFormData();
                exportFormData(formData);
                boolean connectionActive = BEANS.get(IDepartmentService.class).checkImapConnection(formData);

                String message = connectionActive ? TEXTS.get("ConnectionActive") : TEXTS.get("ConnectionFailed");

                NotificationHelper.showNotification(message);
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
            DepartmentFormData formData = new DepartmentFormData();
            exportFormData(formData);
            formData = BEANS.get(IDepartmentService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            DepartmentFormData formData = new DepartmentFormData();
            exportFormData(formData);
            formData = BEANS.get(IDepartmentService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            DepartmentFormData formData = new DepartmentFormData();
            exportFormData(formData);
            formData = BEANS.get(IDepartmentService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getEmailImapEnabledField().fireValueChanged();
        }

        @Override
        protected void execStore() {
            DepartmentFormData formData = new DepartmentFormData();
            exportFormData(formData);
            formData = BEANS.get(IDepartmentService.class).store(formData);
            importFormData(formData);
        }
    }
}
