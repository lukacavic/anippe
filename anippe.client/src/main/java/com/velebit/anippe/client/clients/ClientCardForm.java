package com.velebit.anippe.client.clients;

import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.contacts.ContactForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.AbstractProjectsTable;
import com.velebit.anippe.client.projects.ProjectForm;
import com.velebit.anippe.client.tasks.AbstractTasksGroupBox;
import com.velebit.anippe.client.tickets.AbstractTicketsTable;
import com.velebit.anippe.client.tickets.TicketForm;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.clients.IClientService;
import com.velebit.anippe.shared.contacts.IContactService;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.shareds.ClientCardFormData;
import com.velebit.anippe.shared.shareds.IClientCardService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

@FormData(value = ClientCardFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ClientCardForm extends AbstractForm {
    private Integer clientId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("ViewEntry");
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ClientCard");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        setLabels();
    }

    public MainBox.MainTabBox.ContactsBox getContactsBox() {
        return getFieldByClass(MainBox.MainTabBox.ContactsBox.class);
    }

    public MainBox.MainTabBox.ContactsBox.ContactsTableField getContactsTableField() {
        return getFieldByClass(MainBox.MainTabBox.ContactsBox.ContactsTableField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox getMainInformationsBox() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.ProjectsBox getProjectsBox() {
        return getFieldByClass(MainBox.MainTabBox.ProjectsBox.class);
    }

    public MainBox.MainTabBox.ProjectsBox.ProjectsTableField getProjectsTableField() {
        return getFieldByClass(MainBox.MainTabBox.ProjectsBox.ProjectsTableField.class);
    }

    public MainBox.MainTabBox.TasksBox getTasksBox() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.class);
    }

    public MainBox.MainTabBox.TasksBox.TasksTableField getTasksTableField() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.TasksTableField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    public MainBox.MainTabBox.TicketsBox getTicketsBox() {
        return getFieldByClass(MainBox.MainTabBox.TicketsBox.class);
    }

    public MainBox.MainTabBox.TicketsBox.TicketsTableField getTicketsTableField() {
        return getFieldByClass(MainBox.MainTabBox.TicketsBox.TicketsTableField.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void fetchContacts() {
        List<ClientCardFormData.ContactsTable.ContactsTableRowData> rows = BEANS.get(IClientCardService.class).fetchContacts(getClientId());
        getContactsTableField().getTable().importFromTableRowBeanData(rows, ClientCardFormData.ContactsTable.ContactsTableRowData.class);

        setLabels();
    }

    public void setLabels() {
        Integer contactsCount = getContactsTableField().getTable().getRowCount();
        getContactsBox().setLabel(getContactsBox().getConfiguredLabel() + " (" + contactsCount + ")");

        Integer ticketsCount = getTicketsTableField().getTable().getRowCount();
        getTicketsBox().setLabel(getTicketsBox().getConfiguredLabel() + " (" + ticketsCount + ")");

        Integer projectsCount = getProjectsTableField().getTable().getRowCount();
        getProjectsBox().setLabel(getProjectsBox().getConfiguredLabel() + " (" + projectsCount + ")");
    }

    public void fetchTickets() {

    }

    public void fetchProjects() {

    }

    public void fetchTasks() {

    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(0)
        public class ActionsMenu extends AbstractActionsMenu {

            @Override
            protected boolean getConfiguredVisible() {
                return true;
            }

            @Order(0)
            public class EditMenu extends AbstractEditMenu {

                @Override
                protected void execAction() {
                    ClientForm form = new ClientForm();
                    form.setClientId(getClientId());
                    form.startModify();
                    form.waitFor();
                    if (form.isFormStored()) {
                        NotificationHelper.showSaveSuccessNotification();
                    }
                }
            }

            @Order(1000)
            public class DeleteMenu extends AbstractDeleteMenu {


                @Override
                protected void execAction() {
                    if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                        BEANS.get(IClientService.class).delete(getClientId());
                        NotificationHelper.showDeleteSuccessNotification();

                        doClose();

                    }
                }
            }
        }

        @Order(1000)
        public class SendEmailMenu extends AbstractSendEmailMenu {

            @Override
            protected void execAction() {

            }
        }

        @Order(2000)
        public class NewTaskMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewTask");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Check;
            }

            @Override
            protected void execAction() {

            }
        }

        @Order(3000)
        public class PinMenu extends AbstractMenu {
            @Override
            protected String getConfiguredIconId() {
                return FontIcons.MapPin;
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected void execAction() {

            }
        }

        @Order(1000)
        public class MainTabBox extends AbstractTabBox {

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class MainInformationsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("MainInformations");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }
            }

            @Order(2000)
            public class ContactsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Contacts");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
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
                            NotificationHelper.showSaveSuccessNotification();

                            fetchContacts();
                        }
                    }
                }

                @Order(1000)
                public class ContactsTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<ContactsTableField.Table> {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 6;
                    }

                    @ClassId("723e2e27-3ac8-432e-a0db-fd177bf86e1f")
                    public class Table extends AbstractTable {

                        @Override
                        protected boolean getConfiguredAutoResizeColumns() {
                            return true;
                        }

                        public EmailColumn getEmailColumn() {
                            return getColumnSet().getColumnByClass(EmailColumn.class);
                        }

                        public NameColumn getNameColumn() {
                            return getColumnSet().getColumnByClass(NameColumn.class);
                        }

                        public PhoneColumn getPhoneColumn() {
                            return getColumnSet().getColumnByClass(PhoneColumn.class);
                        }

                        public PositionColumn getPositionColumn() {
                            return getColumnSet().getColumnByClass(PositionColumn.class);
                        }

                        public ContactColumn getContactColumn() {
                            return getColumnSet().getColumnByClass(ContactColumn.class);
                        }

                        public LastLoginAtColumn getLastLoginAtColumn() {
                            return getColumnSet().getColumnByClass(LastLoginAtColumn.class);
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
                                    NotificationHelper.showSaveSuccessNotification();

                                    fetchContacts();
                                }
                            }
                        }

                        @Order(2000)
                        public class DeleteMenu extends AbstractDeleteMenu {

                            @Override
                            protected void execAction() {
                                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                    BEANS.get(IContactService.class).delete(getContactColumn().getSelectedValue().getId());

                                    NotificationHelper.showDeleteSuccessNotification();

                                    fetchContacts();
                                }
                            }
                        }

                        @Order(1000)
                        public class ContactColumn extends AbstractColumn<Contact> {
                            @Override
                            protected boolean getConfiguredDisplayable() {
                                return false;
                            }
                        }

                        @Order(2000)
                        public class NameColumn extends AbstractStringColumn {
                            @Override
                            protected String getConfiguredHeaderText() {
                                return TEXTS.get("Name");
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

                        @Order(5000)
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

                        @Order(6000)
                        public class ActiveColumn extends AbstractBooleanColumn {
                            @Override
                            protected String getConfiguredHeaderText() {
                                return TEXTS.get("Active");
                            }

                            @Override
                            protected int getConfiguredWidth() {
                                return 100;
                            }

                            @Override
                            protected boolean getConfiguredEditable() {
                                return true;
                            }

                            @Override
                            protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                                super.execCompleteEdit(row, editingField);

                                BEANS.get(IContactService.class).toggleActivated(getContactColumn().getValue(row).getId(), getValue(row));

                                NotificationHelper.showSaveSuccessNotification();
                            }
                        }

                        @Order(7000)
                        public class LastLoginAtColumn extends AbstractDateTimeColumn {
                            @Override
                            protected String getConfiguredHeaderText() {
                                return TEXTS.get("LastLogin");
                            }

                            @Override
                            protected int getConfiguredWidth() {
                                return 100;
                            }
                        }
                    }
                }
            }

            @Order(3000)
            public class TicketsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Tickets");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class AddMenu extends AbstractAddMenu {

                    @Override
                    protected void execAction() {
                        TicketForm form = new TicketForm();
                        form.startNew();
                        form.waitFor();
                        if (form.isFormStored()) {
                            NotificationHelper.showSaveSuccessNotification();

                            fetchTickets();
                        }
                    }
                }

                @Order(1000)
                public class TicketsTableField extends AbstractTableField<TicketsTableField.Table> {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 6;
                    }

                    @ClassId("7cb17cf8-e981-40dd-8be0-bb0fb8477064")
                    public class Table extends AbstractTicketsTable {

                        @Override
                        public void reloadData() {

                        }
                    }
                }
            }

            @Order(3500)
            public class ProjectsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Projects");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class AddMenu extends AbstractAddMenu {

                    @Override
                    protected void execAction() {
                        ProjectForm form = new ProjectForm();
                        form.startNew();
                        form.waitFor();
                        if (form.isFormStored()) {
                            NotificationHelper.showSaveSuccessNotification();

                            fetchProjects();
                        }
                    }
                }

                @Order(1000)
                public class ProjectsTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<ProjectsTableField.Table> {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 6;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @ClassId("f3c40c79-de19-4211-bccd-0f2cadbff36f")
                    public class Table extends AbstractProjectsTable {
                        @Override
                        protected void execContentChanged() {
                            getTasksBox().setLabel(TEXTS.get("Tasks") + " (" + getRowCount() + ")");
                        }

                        @Override
                        public void reloadData() {

                        }
                    }
                }
            }

            @Order(4000)
            public class TasksBox extends AbstractTasksGroupBox {

                @Override
                protected void reloadTasks() {

                }
            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {
            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ClientCardFormData formData = new ClientCardFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientCardService.class).load(formData);
            importFormData(formData);
        }


        @Override
        protected void execStore() {
            ClientCardFormData formData = new ClientCardFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientCardService.class).store(formData);
            importFormData(formData);
        }
    }
}
