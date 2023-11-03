package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.tasks.TaskTimersForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskTimersForm.MainBox.GroupBox.TaskTimersTableField.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
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
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

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

    public GroupBox.AddManualTimeBox getAddManualTimeBox() {
        return getFieldByClass(GroupBox.AddManualTimeBox.class);
    }

    public GroupBox.AddManualTimeBox.EndAtField getEndAtField() {
        return getFieldByClass(GroupBox.AddManualTimeBox.EndAtField.class);
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

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
        return false;
    }

    public GroupBox.AddManualTimeBox.NoteField getNoteField() {
        return getFieldByClass(GroupBox.AddManualTimeBox.NoteField.class);
    }

    public GroupBox.AddManualTimeBox.SaveButton getSaveButton() {
        return getFieldByClass(GroupBox.AddManualTimeBox.SaveButton.class);
    }

    public GroupBox.AddManualTimeBox.StartAtField getStartAtField() {
        return getFieldByClass(GroupBox.AddManualTimeBox.StartAtField.class);
    }

    public GroupBox.TaskTimersTableField getTaskTimersTableField() {
        return getFieldByClass(GroupBox.TaskTimersTableField.class);
    }

    public GroupBox.AddManualTimeBox.UserField getUserField() {
        return getFieldByClass(GroupBox.AddManualTimeBox.UserField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 650;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(1000)
            public class AddTimerMenu extends AbstractAddMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("AddTime");
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return -1;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Clock;
                }

                @Override
                protected void execAction() {
                    getAddManualTimeBox().setVisible(!getAddManualTimeBox().isVisible());
                }
            }

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

                    @Order(1000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {
                            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                BEANS.get(ITaskTimersService.class).delete(getTimerIdColumn().getSelectedValues());

                                fetchTimers();
                            }
                        }
                    }

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

                        @Override
                        protected boolean getConfiguredEditable() {
                            return true;
                        }

                        @Override
                        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                            super.execCompleteEdit(row, editingField);

                            Integer timerId = getTimerIdColumn().getValue(row);

                            if(getValue(row) == null) return;

                            BEANS.get(ITaskTimersService.class).updateStartTime(timerId, getValue(row));
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

                        @Override
                        protected boolean getConfiguredEditable() {
                            return true;
                        }

                        @Override
                        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                            super.execCompleteEdit(row, editingField);

                            Integer timerId = getTimerIdColumn().getValue(row);

                            if(getValue(row) == null) return;

                            BEANS.get(ITaskTimersService.class).updateEndTime(timerId, getValue(row));
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

            @Order(2000)
            public class AddManualTimeBox extends AbstractGroupBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 2;
                }

                @Order(1000)
                public class StartAtField extends AbstractDateTimeField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("StartAt");
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        setValue(new Date());
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }
                }

                @Order(2000)
                public class EndAtField extends AbstractDateTimeField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("EndAt");
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }
                }

                @Order(3000)
                public class UserField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("User");
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        setValue(ClientSession.get().getCurrentUser().getId().longValue());
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return UserLookupCall.class;
                    }
                }

                @Order(4000)
                public class NoteField extends AbstractTextAreaField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Note");
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                }

                @Order(5000)
                public class SaveButton extends AbstractButton {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Save");
                    }

                    @Override
                    public boolean isProcessButton() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected int getConfiguredHorizontalAlignment() {
                        return 1;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Check;
                    }

                    @Override
                    protected Boolean getConfiguredDefaultButton() {
                        return true;
                    }

                    @Override
                    protected void execClickAction() {
                        validateForm();

                        TaskTimersFormData formData = new TaskTimersFormData();
                        exportFormData(formData);
                        BEANS.get(ITaskTimersService.class).addManualEntry(formData);

                        getStartAtField().resetValue();
                        getEndAtField().setValue(null);
                        getUserField().resetValue();
                        getNoteField().setValue(null);

                        fetchTimers();
                    }
                }

            }
        }

        @Order(2000)
        public class CancelButton extends AbstractCancelButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Close");
            }
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
