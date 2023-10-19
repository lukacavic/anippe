package com.velebit.anippe.client.reminders;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.reminders.IReminderService;
import com.velebit.anippe.shared.reminders.Reminder;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractRemindersTable extends AbstractTable {

    protected abstract void reloadTableData();

    @Override
    protected boolean getConfiguredTableStatusVisible() {
        return false;
    }

    @Override
    protected void execRowAction(ITableRow row) {
        super.execRowAction(row);

        ReminderForm form = new ReminderForm();
        form.setReminderId(getReminderColumn().getValue(row).getId());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
            reloadTableData();
        }
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
    }

    public UserCreatedColumn getUserCreatedColumn() {
        return getColumnSet().getColumnByClass(UserCreatedColumn.class);
    }

    public CreatedAtColumn getCreatedAtColumn() {
        return getColumnSet().getColumnByClass(CreatedAtColumn.class);
    }

    public TitleColumn getTitleColumn() {
        return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    public ContentColumn getContentColumn() {
        return getColumnSet().getColumnByClass(ContentColumn.class);
    }

    public RemindAtColumn getRemindAtColumn() {
        return getColumnSet().getColumnByClass(RemindAtColumn.class);
    }

    public ReminderColumn getReminderColumn() {
        return getColumnSet().getColumnByClass(ReminderColumn.class);
    }

    public SendEmailColumn getSendEmailColumn() {
        return getColumnSet().getColumnByClass(SendEmailColumn.class);
    }

    public UserColumn getUserColumn() {
        return getColumnSet().getColumnByClass(UserColumn.class);
    }

    @Order(1000)
    public class DeleteMenu extends AbstractDeleteMenu {

        @Override
        protected void execAction() {
            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                List<Reminder> reminders = getReminderColumn().getSelectedValues();
                BEANS.get(IReminderService.class).delete(reminders.stream().map(Reminder::getId).collect(Collectors.toList()));

                NotificationHelper.showDeleteSuccessNotification();

                reloadTableData();
            }

        }
    }

    @Order(1000)
    public class ReminderColumn extends AbstractColumn<Reminder> {
        @Override
        protected boolean getConfiguredDisplayable() {
            return false;
        }
    }

    @Order(1900)
    public class CreatedAtColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("CreatedAt");
        }

        @Override
        protected boolean getConfiguredVisible() {
            return false;
        }

        @Override
        protected int getConfiguredWidth() {
            return 120;
        }
    }

    @Order(1950)
    public class RemindAtColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("ReminderAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 130;
        }
    }

    @Order(2000)
    public class UserCreatedColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("UserCreated");
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            boolean isSystemGenerated = getReminderColumn().getValue(row).isSystemGenerated();

            cell.setText(isSystemGenerated ? TEXTS.get("System") : getValue(row));
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5000)
    public class TitleColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Title");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5500)
    public class UserColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("User");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(6000)
    public class ContentColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Content");
        }

        @Override
        protected int getConfiguredWidth() {
            return 200;
        }
    }

    @Order(7000)
    public class SendEmailColumn extends AbstractBooleanColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("SendEmail");
        }

        @Override
        public boolean isDisplayable() {
            return false;
        }

        @Override
        protected int getConfiguredWidth() {
            return 130;
        }
    }

}
