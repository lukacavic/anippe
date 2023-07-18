package com.velebit.anippe.client.contacts;

import com.velebit.anippe.client.contacts.ContactForm.MainBox.CancelButton;
import com.velebit.anippe.client.contacts.ContactForm.MainBox.GroupBox;
import com.velebit.anippe.client.contacts.ContactForm.MainBox.OkButton;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.contacts.ContactFormData;
import com.velebit.anippe.shared.contacts.IContactService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = ContactFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ContactForm extends AbstractForm {

    private Integer clientId;
    private Integer contactId;

    @FormData
    public Integer getContactId() {
        return contactId;
    }

    @FormData
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Override
    protected void execStored() {
        NotificationHelper.showSaveSuccessNotification();
    }

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Contact");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.PersonSolid;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
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

    public GroupBox.EmailField getEmailField() {
        return getFieldByClass(GroupBox.EmailField.class);
    }

    public GroupBox.FirstNameField getFirstNameField() {
        return getFieldByClass(GroupBox.FirstNameField.class);
    }

    public GroupBox.LastNameField getLastNameField() {
        return getFieldByClass(GroupBox.LastNameField.class);
    }

    public GroupBox.PhoneField getPhoneField() {
        return getFieldByClass(GroupBox.PhoneField.class);
    }

    public GroupBox.PositionField getPositionField() {
        return getFieldByClass(GroupBox.PositionField.class);
    }

    public GroupBox.PrimaryContactField getPrimaryContactField() {
        return getFieldByClass(GroupBox.PrimaryContactField.class);
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
            public class PositionField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Position");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
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
            public class PhoneField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Phone");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(6000)
            public class PrimaryContactField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("PrimaryContact");
                }
            }

            @Order(7000)
            public class ActiveField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Active");
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    setValue(true);
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
            ContactFormData formData = new ContactFormData();
            exportFormData(formData);
            formData = BEANS.get(IContactService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ContactFormData formData = new ContactFormData();
            exportFormData(formData);
            formData = BEANS.get(IContactService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ContactFormData formData = new ContactFormData();
            exportFormData(formData);
            formData = BEANS.get(IContactService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ContactFormData formData = new ContactFormData();
            exportFormData(formData);
            formData = BEANS.get(IContactService.class).store(formData);
            importFormData(formData);
        }
    }
}
