package com.velebit.anippe.client.contacts;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.contacts.ContactsTablePage.Table;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.contacts.ContactsTablePageData;
import com.velebit.anippe.shared.contacts.IContactsService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(ContactsTablePageData.class)
public class ContactsTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IContactsService.class).getContactsTableData(filter));
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.PersonSolid;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.PersonSolid;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Contacts");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected void execAction() {
            ContactForm form = new ContactForm();
            form.getClientField().setEnabled(true);
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("AddContact");
        }
    }

    public class Table extends AbstractTable {
        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public ActiveColumn getActiveColumn() {
            return getColumnSet().getColumnByClass(ActiveColumn.class);
        }

        public ClientColumn getClientColumn() {
            return getColumnSet().getColumnByClass(ClientColumn.class);
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

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                ContactForm form = new ContactForm();
                form.setContactId(getContactColumn().getSelectedValue().getId());
                if (getClientColumn().getSelectedValue() == null) {
                    form.getClientField().setEnabled(true);
                }
                form.startModify();
                form.waitFor();
                if (form.isFormStored()) {
                    NotificationHelper.showSaveSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Order(2000)
        public class DeleteMenu extends AbstractDeleteMenu {

            @Override
            protected void execAction() {
                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(IContactsService.class).delete(getContactColumn().getSelectedValue().getId());

                    reloadPage();

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

        @Order(2250)
        public class ClientColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Client");
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
