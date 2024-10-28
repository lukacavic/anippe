package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.shared.constants.ColorConstants;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.Task;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Date;
import java.util.Set;

@FormData(value = AbstractTasksGroupBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractTasksGroupBox extends AbstractGroupBox {

    private Integer relatedId;
    private Integer relatedType;

    @FormData
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    protected abstract void reloadTasks();

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }

    public TasksTableField getTasksTableField() {
        return getFieldByClass(TasksTableField.class);
    }

    @Override
    protected String getConfiguredLabel() {
        return TEXTS.get("Tasks");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewTask");
        }

        @Override
        protected void execAction() {
            TaskForm form = new TaskForm();
            form.setRelatedId(getRelatedId().longValue());
            form.setRelatedType(getRelatedType());
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadTasks();
            }
        }
    }

    @Order(1000)
    public class TasksTableField extends AbstractTableField<TasksTableField.Table> {
        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @Override
        public boolean isLabelVisible() {
            return false;
        }

        @Override
        protected void execInitField() {
            super.execInitField();

            setLabel();
        }

        private void setLabel() {
            int tasksCount = getTable().getRowCount();
            AbstractTasksGroupBox.this.setLabel(AbstractTasksGroupBox.this.getConfiguredLabel() + " (" + tasksCount + ")");
        }

        @Override
        protected int getConfiguredGridH() {
            return 6;
        }

        @ClassId("d27e61a9-140e-49d3-b024-64b9f7f0c494")
        public class Table extends AbstractTasksTable {
            @Override
            protected void execContentChanged() {
                super.execContentChanged();

                setLabel();
            }

            @Override
            protected void execDecorateRow(ITableRow row) {
                super.execDecorateRow(row);

                boolean isOverdue = !getStatusColumn().getValue(row).equals(Constants.TaskStatus.COMPLETED) && (getDeadlineAtColumn().getValue(row) != null && getDeadlineAtColumn().getValue(row).before(new Date()));

                row.setBackgroundColor(isOverdue ? ColorConstants.Red.Red1 : null);
            }

            @Override
            protected void execRowAction(ITableRow row) {
                super.execRowAction(row);

                TaskForm form = new TaskForm();
                form.setTaskId(getTaskColumn().getSelectedValue().getId());
                form.startView();
            }

            public AssignedToColumn getAssignedToColumn() {
                return getColumnSet().getColumnByClass(AssignedToColumn.class);
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

            @Order(0)
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
                    reloadTasks();
                }
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

                        reloadTasks();
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

                        reloadTasks();
                    }
                }
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

                @Override
                protected void execDecorateCell(Cell cell, ITableRow row) {
                    super.execDecorateCell(cell, row);

                    String description = getTaskColumn().getValue(row).getDescription();

                    if (description != null) {
                        cell.setTooltipText(description);
                    }
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

                @Override
                protected void execDecorateCell(Cell cell, ITableRow row) {
                    super.execDecorateCell(cell, row);

                    if (getValue(row) == null) return;

                    if (getValue(row).equals(Constants.TaskStatus.COMPLETED)) {
                        cell.setIconId(FontIcons.Check);
                        cell.setBackgroundColor(ColorConstants.Green.Green1);
                    } else if (getValue(row).equals(Constants.TaskStatus.IN_PROGRESS)) {
                        cell.setIconId(FontIcons.Spinner1);
                    }
                }

                @Override
                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                    super.execCompleteEdit(row, editingField);

                    Integer taskId = getTaskColumn().getValue(row).getId();
                    Integer statusId = getValue(row);

                    BEANS.get(ITasksService.class).updateStatus(taskId, statusId);
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
                protected void execDecorateCell(Cell cell, ITableRow row) {
                    super.execDecorateCell(cell, row);

                    if (getValue(row) == null) return;

                    if (getValue(row).equals(Constants.Priority.URGENT)) {
                        cell.setBackgroundColor(ColorConstants.Red.Red1);
                    } else if (getValue(row).equals(Constants.Priority.HIGH)) {
                        cell.setBackgroundColor(ColorConstants.Orange.Orange1);
                    }
                }

                @Override
                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                    super.execCompleteEdit(row, editingField);

                    Integer taskId = getTaskColumn().getValue(row).getId();
                    Integer priorityId = getValue(row);

                    BEANS.get(ITasksService.class).updatePriority(taskId, priorityId);
                }

                @Override
                protected int getConfiguredWidth() {
                    return 100;
                }
            }


        }
    }

}
