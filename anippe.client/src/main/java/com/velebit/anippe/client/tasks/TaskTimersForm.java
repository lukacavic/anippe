package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.tasks.TaskTimersForm.MainBox.CancelButton;
import com.velebit.anippe.client.tasks.TaskTimersForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskTimersForm.MainBox.GroupBox.TaskTimersTableField.Table;
import com.velebit.anippe.client.tasks.TaskTimersForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.ITaskTimersService;
import com.velebit.anippe.shared.tasks.TaskTimersFormData;
import com.velebit.anippe.shared.tasks.TaskTimersFormData.TaskTimersTable.TaskTimersTableRowData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;

import java.util.Date;
import java.util.List;

@FormData(value = TaskTimersFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskTimersForm extends AbstractForm {

    private Integer taskId;

    @FormData
    public Integer getTaskId() {
        return taskId;
    }

    @FormData
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        fetchTimers();
    }

    public void fetchTimers() {
        List<TaskTimersTableRowData> rows = BEANS.get(ITaskTimersService.class).fetchTimers(getTaskId());
        getTaskTimersTableField().getTable().importFromTableRowBeanData(rows, TaskTimersTableRowData.class);
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("TaskTimers");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Clock;
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

    public GroupBox.TaskTimersTableField getTaskTimersTableField() {
        return getFieldByClass(GroupBox.TaskTimersTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 650;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class TaskTimersTableField extends AbstractTableField<Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("b96a86dd-4516-4030-8faa-0fcc1cbd386e")
                public class Table extends AbstractTable {
                    @Override
                    protected boolean getConfiguredHeaderEnabled() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public EndAtColumn getEndAtColumn() {
                        return getColumnSet().getColumnByClass(EndAtColumn.class);
                    }

                    public StartAtColumn getStartAtColumn() {
                        return getColumnSet().getColumnByClass(StartAtColumn.class);
                    }

                    public TimerIdColumn getTimerIdColumn() {
                        return getColumnSet().getColumnByClass(TimerIdColumn.class);
                    }

                    public TotalTimeColumn getTotalTimeColumn() {
                        return getColumnSet().getColumnByClass(TotalTimeColumn.class);
                    }

                    public UserColumn getUserColumn() {
                        return getColumnSet().getColumnByClass(UserColumn.class);
                    }

                    @Order(1000)
                    public class TimerIdColumn extends AbstractIDColumn {

                    }

                    @Order(2000)
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

                    @Order(2500)
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

                    @Order(2750)
                    public class EndAtColumn extends AbstractDateTimeColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("EndAt");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(3000)
                    public class TotalTimeColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("TotalTime");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            super.execDecorateCell(cell, row);

                            Date startAt = getStartAtColumn().getValue(row);
                            Date endAt = getEndAtColumn().getValue(row);

                            if (startAt == null || endAt == null) return;

                            long diff = endAt.getTime() - startAt.getTime();
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000);

                            String content = StringUtility.join(":", String.valueOf(String.format("%02d", diffHours)), String.valueOf(String.format("%02d", diffMinutes)));

                            cell.setText(content);
                        }
                    }

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
            TaskTimersFormData formData = new TaskTimersFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskTimersService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TaskTimersFormData formData = new TaskTimersFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskTimersService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskTimersFormData formData = new TaskTimersFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskTimersService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TaskTimersFormData formData = new TaskTimersFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskTimersService.class).store(formData);
            importFormData(formData);
        }
    }
}
