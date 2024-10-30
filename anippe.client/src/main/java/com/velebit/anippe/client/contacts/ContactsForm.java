package com.velebit.anippe.client.contacts;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.common.menus.AbstractSendEmailMenu;
import com.velebit.anippe.client.contacts.ContactsForm.MainBox.GroupBox;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.contacts.ContactsFormData;
import com.velebit.anippe.shared.contacts.IContactsService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

@FormData(value = ContactsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ContactsForm extends AbstractForm {

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Contacts");
    }

    public GroupBox.ContactsTableField getContactsTableField() {
        return getFieldByClass(GroupBox.ContactsTableField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public void fetchContacts() {
        List<ContactsFormData.ContactsTable.ContactsTableRowData> rows = BEANS.get(IContactsService.class).fetchContacts(getClientId());
        getContactsTableField().getTable().importFromTableRowBeanData(rows, ContactsFormData.ContactsTable.ContactsTableRowData.class);
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
            public class AddMenu extends AbstractAddMenu {
                @Override
                protected void execAction() {
                    ContactForm form = new ContactForm();
                    form.setClientId(getClientId());
                    if (getClientId() != null) {
                        form.getClientField().setVisible(false);
                    }
                    form.startNew();
                    form.waitFor();
                    if (form.isFormStored()) {
                        fetchContacts();
                    }
                }

                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("AddContact");
                }
            }

            @Order(1000)
            public class ContactsTableField extends AbstractTableField<ContactsTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected double getConfiguredGridWeightY() {
                    return -1;
                }

                @Override
                protected boolean getConfiguredFillVertical() {
                    return true;
                }

                @Override
                protected boolean getConfiguredGridUseUiHeight() {
                    return true;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 10;
                }

                @ClassId("12ddb1b4-12cf-4a95-b112-e734503be3b2")
                public class Table extends AbstractTable {

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public ActiveColumn getActiveColumn() {
                        return getColumnSet().getColumnByClass(ActiveColumn.class);
                    }

                    public ContactColumn getContactColumn() {
                        return getColumnSet().getColumnByClass(ContactColumn.class);
                    }

                    public EmailColumn getEmailColumn() {
                        return getColumnSet().getColumnByClass(EmailColumn.class);
                    }

                    public FullNameColumn getFullNameColumn() {
                        return getColumnSet().getColumnByClass(FullNameColumn.class);
                    }

                    public PhoneColumn getPhoneColumn() {
                        return getColumnSet().getColumnByClass(PhoneColumn.class);
                    }

                    public PositionColumn getPositionColumn() {
                        return getColumnSet().getColumnByClass(PositionColumn.class);
                    }

                    public PrimaryColumn getPrimaryColumn() {
                        return getColumnSet().getColumnByClass(PrimaryColumn.class);
                    }

                    @Order(1000)
                    public class EditMenu extends AbstractEditMenu {

                        @Override
                        protected void execAction() {
                            ContactForm form = new ContactForm();
                            form.setContactId(getContactColumn().getSelectedValue().getId());
                            form.startModify();
                            form.waitFor();
                            if (form.isFormStored()) {
                                fetchContacts();
                            }
                        }
                    }

                    @Order(1500)
                    public class SendEmailMenu extends AbstractSendEmailMenu {

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(2000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {
                            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                BEANS.get(IContactsService.class).delete(getContactColumn().getSelectedValue().getId());

                                fetchContacts();

                                NotificationHelper.showDeleteSuccessNotification();
                            }
                        }
                    }

                    @Order(0)
                    public class ContactColumn extends AbstractColumn<Contact> {
                        @Override
                        public boolean isDisplayable() {
                            return false;
                        }
                    }

                    @Order(1000)
                    public class FullNameColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("FullName");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(2000)
                    public class PositionColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Position");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(2500)
                    public class PrimaryColumn extends AbstractBooleanColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("PrimaryContact");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(3000)
                    public class EmailColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Email");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(4000)
                    public class PhoneColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Phone");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(5000)
                    public class ActiveColumn extends AbstractBooleanColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Active");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }
                }
            }
        }
    }

}
