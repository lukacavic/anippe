package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.columns.AbstractColorColumn;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.Task;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Set;

public abstract class AbstractTasksTable extends AbstractTable {
    public abstract void reloadData();

    public AssignedToColumn getAssignedToColumn() {
        return getColumnSet().getColumnByClass(AssignedToColumn.class);
    }

    public ColorColumn getColorColumn() {
        return getColumnSet().getColumnByClass(ColorColumn.class);
    }

    public DeadlineAtColumn getDeadlineAtColumn() {
        return getColumnSet().getColumnByClass(DeadlineAtColumn.class);
    }

    public NameColumn getNameColumn() {
        return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public PriorityColumn getPriorityColumn() {
        return getColumnSet().getColumnByClass(PriorityColumn.class);
    }

    public StartAtColumn getStartAtColumn() {
        return getColumnSet().getColumnByClass(StartAtColumn.class);
    }

    public StatusColumn getStatusColumn() {
        return getColumnSet().getColumnByClass(StatusColumn.class);
    }

    public TaskColumn getTaskColumn() {
        return getColumnSet().getColumnByClass(TaskColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
    }

    @Override
    protected boolean getConfiguredCheckable() {
        return true;
    }

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {

        @Override
        protected void execAction() {
            TaskForm form = new TaskForm();
            form.setTaskId(getTaskColumn().getSelectedValue().getId());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadData();
            }
        }
    }

    @Order(2000)
    public class DeleteMenu extends AbstractDeleteMenu {

        @Override
        protected void execAction() {
            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                BEANS.get(ITaskService.class).delete(getTaskColumn().getSelectedValue().getId());

                NotificationHelper.showDeleteSuccessNotification();
            }
        }
    }

    @Order(3000)
    public class ExportToExcelMenu extends AbstractMenu {
        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Excel;
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
            return 1;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.SingleSelection, TableMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {

        }
    }

    @Order(0)
    public class ColorColumn extends AbstractColorColumn {

    }

    @Order(1000)
    public class TaskColumn extends AbstractColumn<Task> {
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
    public class StatusColumn extends AbstractSmartColumn<Integer> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Status");
        }

        @Override
        protected boolean getConfiguredEditable() {
            return true;
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
            return TaskStatusLookupCall.class;
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(4000)
    public class StartAtColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("StartAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5000)
    public class DeadlineAtColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("DeadlineAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(6000)
    public class AssignedToColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Assigned");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(7000)
    public class PriorityColumn extends AbstractSmartColumn<Integer> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Priority");
        }

        @Override
        protected boolean getConfiguredEditable() {
            return true;
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
            return PriorityLookupCall.class;
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }
}
