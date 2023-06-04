package com.velebit.anippe.client.settings.users;

import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.users.UserForm.MainBox.ActionsMenu;
import com.velebit.anippe.client.settings.users.UserForm.MainBox.CancelButton;
import com.velebit.anippe.client.settings.users.UserForm.MainBox.GroupBox;
import com.velebit.anippe.client.settings.users.UserForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.users.CreateUserPermission;
import com.velebit.anippe.shared.settings.users.IUserService;
import com.velebit.anippe.shared.settings.users.UpdateUserPermission;
import com.velebit.anippe.shared.settings.users.UserFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = UserFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserForm extends AbstractForm {

    private Integer userId;


    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("User");
    }

    public GroupBox.ActiveField getActiveField() {
        return getFieldByClass(GroupBox.ActiveField.class);
    }

    public GroupBox.AdministratorField getAdministratorField() {
        return getFieldByClass(GroupBox.AdministratorField.class);
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

    public GroupBox.EmailField getEmailField() {
        return getFieldByClass(GroupBox.EmailField.class);
    }

    public GroupBox.FirstNameField getFirstNameField() {
        return getFieldByClass(GroupBox.FirstNameField.class);
    }

    public GroupBox.LastNameField getLastNameField() {
        return getFieldByClass(GroupBox.LastNameField.class);
    }

    public GroupBox.RolesBox getRolesBox() {
        return getFieldByClass(GroupBox.RolesBox.class);
    }

    public GroupBox.UsernameField getUsernameField() {
        return getFieldByClass(GroupBox.UsernameField.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.PersonSolid;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    @FormData
    public Integer getUserId() {
        return userId;
    }

    @FormData
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class FirstNameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("FirstName");
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
            public class LastNameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("LastName");
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

            @Order(3000)
            public class UsernameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Username");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }

                @Override
                protected String execValidateValue(String rawValue) {
                    if (rawValue != null) {
                        if (BEANS.get(IUserService.class).isUsernameValid(rawValue, getUserId() != null ? getUserId().intValue() : null)) {
                            throw new VetoException(TEXTS.get("UsernameIsInUse"));
                        }

                        if (rawValue.matches(".*\\s+.*")) {
                            throw new VetoException(TEXTS.get("UsernameContainsSpace"));
                        }
                    }

                    return rawValue;
                }
            }

            @Order(4000)
            public class EmailField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Email");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(5000)
            public class RolesBox extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Roles");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }
            }

            @Order(6000)
            public class ActiveField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("ActiveStates");
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    setValue(true);
                }
            }

            @Order(7000)
            public class AdministratorField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Administrator");
                }
            }


        }

        @Order(1000)
        public class ActionsMenu extends AbstractActionsMenu {
            @Order(1000)
            public class ResetPasswordMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("ResetPassword");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Spinner1;
                }

                @Override
                protected void execAction() {
                    if (MessageBoxHelper.showYesNoConfirmationMessage() == IMessageBox.YES_OPTION) {
                        BEANS.get(IUserService.class).resetPassword(getUserId().intValue());

                        NotificationHelper.showSaveSuccessNotification();
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            UserFormData formData = new UserFormData();
            exportFormData(formData);
            formData = BEANS.get(IUserService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            UserFormData formData = new UserFormData();
            exportFormData(formData);
            formData = BEANS.get(IUserService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            UserFormData formData = new UserFormData();
            exportFormData(formData);
            formData = BEANS.get(IUserService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            setSubTitle(TEXTS.get("ViewEntry"));
            MenuUtility.getMenuByClass(getMainBox(), ActionsMenu.class).setVisible(true);
        }

        @Override
        protected void execStore() {
            UserFormData formData = new UserFormData();
            exportFormData(formData);
            formData = BEANS.get(IUserService.class).store(formData);
            importFormData(formData);
        }
    }
}
