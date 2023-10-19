package com.velebit.anippe.client.reminders;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.reminders.AbstractRemindersGroupBox.RemindersTableField.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.reminders.AbstractRemindersGroupBoxData;
import com.velebit.anippe.shared.reminders.AbstractRemindersGroupBoxData.RemindersTable.RemindersTableRowData;
import com.velebit.anippe.shared.reminders.IRemindersService;
import com.velebit.anippe.shared.reminders.Reminder;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;
import java.util.Set;

@FormData(value = AbstractRemindersGroupBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractRemindersGroupBox extends AbstractGroupBox {

    protected Integer relatedId;
    protected Integer relatedType;
    protected Integer clientId;

    @Override
    protected void execInitField() {
        registerDataChangeListener(Reminder.class);
    }

    @Override
    protected void execDisposeField() {
        unregisterDataChangeListener(Reminder.class);
    }

    @Override
    protected void execDataChanged(Object... dataTypes) {
        fetchReminders();
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
    protected int getConfiguredGridColumnCount() {
        return 1;
    }

    public RemindersTableField getRemindersTableField() {
        return getFieldByClass(RemindersTableField.class);
    }

    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }

    @Override
    protected String getConfiguredLabel() {
        return TEXTS.get("Reminders");
    }

    public void fetchReminders() {
        List<RemindersTableRowData> rowData = BEANS.get(IRemindersService.class).fetchReminders(relatedId, relatedType);
        getRemindersTableField().getTable().importFromTableRowBeanData(rowData, RemindersTableRowData.class);
    }

    @Order(-3000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewReminder");
        }

        @Override
        protected void execAction() {
            ReminderForm form = new ReminderForm();
            form.setRelatedId(getRelatedId());
            form.setRelatedType(getRelatedType());
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                fetchReminders();
            }
        }
    }

    @Order(1000)
    public class RemindersTableField extends AbstractTableField<Table> {

        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
            return false;
        }

        @Override
        protected int getConfiguredGridH() {
            return 6;
        }

        public class Table extends AbstractRemindersTable {

            @Override
            protected void reloadTableData() {
                fetchReminders();
            }

            @Order(-2000)
            public class RefreshMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("Refresh");
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                    return CollectionUtility.hashSet(TableMenuType.EmptySpace);
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Spinner1;
                }

                @Override
                protected void execAction() {
                    fetchReminders();
                }
            }

        }
    }

}
