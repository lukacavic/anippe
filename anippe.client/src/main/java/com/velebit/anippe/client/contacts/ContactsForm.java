package com.velebit.anippe.client.contacts;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.contacts.ContactsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.contacts.ContactsFormData;
import com.velebit.anippe.shared.contacts.IContactsService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

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
                    form.startNew();
                    form.waitFor();
                    if (form.isFormStored()) {
                        fetchContacts();
                    }
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
                    return 1;
                }

                @ClassId("12ddb1b4-12cf-4a95-b112-e734503be3b2")
                public class Table extends AbstractTable {
                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
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
                }
            }
        }
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ContactsFormData formData = new ContactsFormData();
            exportFormData(formData);
            formData = BEANS.get(IContactsService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ContactsFormData formData = new ContactsFormData();
            exportFormData(formData);
            formData = BEANS.get(IContactsService.class).create(formData);
            importFormData(formData);
        }
    }
}
