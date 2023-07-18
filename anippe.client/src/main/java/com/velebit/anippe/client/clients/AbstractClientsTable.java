package com.velebit.anippe.client.clients;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.clients.IClientsService;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

public abstract class AbstractClientsTable extends AbstractTable {

    public abstract void reloadData();

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
        return ClientCardMenu.class;
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
    }

    public ClientColumn getClientColumn() {
        return getColumnSet().getColumnByClass(ClientColumn.class);
    }

    public ActiveColumn getActiveColumn() {
        return getColumnSet().getColumnByClass(ActiveColumn.class);
    }

    public com.velebit.anippe.client.clients.AbstractClientsTable.GroupsColumn getGroupsColumn() {
        return getColumnSet().getColumnByClass(com.velebit.anippe.client.clients.AbstractClientsTable.GroupsColumn.class);
    }

    public NameColumn getNameColumn() {
        return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public com.velebit.anippe.client.clients.AbstractClientsTable.PhoneColumn getPhoneColumn() {
        return getColumnSet().getColumnByClass(com.velebit.anippe.client.clients.AbstractClientsTable.PhoneColumn.class);
    }

    public PrimaryContactColumn getPrimaryContactColumn() {
        return getColumnSet().getColumnByClass(PrimaryContactColumn.class);
    }

    public PrimaryEmailColumn getPrimaryEmailColumn() {
        return getColumnSet().getColumnByClass(PrimaryEmailColumn.class);
    }

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {

        @Override
        protected void execAction() {
            ClientForm form = new ClientForm();
            form.setClientId(getClientColumn().getSelectedValue().getId());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
                reloadData();
            }
        }
    }

    @Order(2000)
    public class DeleteMenu extends AbstractDeleteMenu {

        @Override
        protected void execAction() {
            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                BEANS.get(IClientsService.class).delete(getClientColumn().getSelectedValue().getId());

                NotificationHelper.showDeleteSuccessNotification();

                reloadData();
            }
        }
    }

    @Order(500)
    public class ClientCardMenu extends AbstractClientCardMenu {
        @Override
        protected void execAction() {
            super.setClientId(getClientColumn().getSelectedValue().getId());
            super.execAction();
        }
    }

    @Order(1000)
    public class ClientColumn extends AbstractColumn<Client> {
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
    public class PrimaryContactColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("PrimaryContact");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(4000)
    public class PrimaryEmailColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("PrimaryEmail");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @org.eclipse.scout.rt.platform.Order(4500)
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
            return TEXTS.get("ActiveStates");
        }

        @Override
        protected boolean getConfiguredEditable() {
            return true;
        }

        @Override
        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
            super.execCompleteEdit(row, editingField);

            BEANS.get(IClientsService.class).toggleActivated(getClientColumn().getValue(row).getId(), getValue(row));

            NotificationHelper.showSaveSuccessNotification();
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @org.eclipse.scout.rt.platform.Order(5500)
    public class GroupsColumn extends org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return org.eclipse.scout.rt.platform.text.TEXTS.get("Groups");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(6000)
    public class CreatedAtColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("CreatedAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }
}
