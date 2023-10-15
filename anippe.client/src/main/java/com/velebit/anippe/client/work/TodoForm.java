package com.velebit.anippe.client.work;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.work.TodoForm.MainBox.GroupBox;
import com.velebit.anippe.client.work.TodoForm.MainBox.GroupBox.TodoField;
import com.velebit.anippe.client.work.TodoForm.MainBox.GroupBox.TodoTableField;
import com.velebit.anippe.client.work.TodoForm.MainBox.GroupBox.TodoTableField.Table;
import com.velebit.anippe.shared.constants.ColorConstants.Green;
import com.velebit.anippe.shared.work.ITodoService;
import com.velebit.anippe.shared.work.TodoFormData;
import com.velebit.anippe.shared.work.TodoFormData.TodoTable.TodoTableRowData;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

@FormData(value = TodoFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TodoForm extends AbstractForm {
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Todo");
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    @Override
    protected void execInitForm() {
        fetchTodos();
    }

    public TodoField getTodoField() {
        return getFieldByClass(TodoField.class);
    }

    public void fetchTodos() {
        List<TodoTableRowData> rows = BEANS.get(ITodoService.class).fetchTodos();
        getTodoTableField().getTable().importFromTableRowBeanData(rows, TodoTableRowData.class);
    }

    public TodoTableField getTodoTableField() {
        return getFieldByClass(TodoTableField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Override
        protected int getConfiguredWidthInPixel() {
            return 500;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(0)
            public class TodoField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("AddNewTodo");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_ON_FIELD;
                }

                @Override
                protected String getConfiguredFieldStyle() {
                    return FIELD_STYLE_CLASSIC;
                }

                @Override
                protected void execChangedValue() {
                    if (getValue() == null) return;

                    createTodo(getValue());

                    ModelJobs.schedule(() -> setValue(null), ModelJobs.newInput(ClientRunContexts.copyCurrent()));
                }

                private void createTodo(String value) {
                    BEANS.get(ITodoService.class).createTodo(value);

                    fetchTodos();
                }
            }

            @Order(1000)
            public class TodoTableField extends AbstractTableField<Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                public class Table extends AbstractTable {

                    @Order(1000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {
                            BEANS.get(ITodoService.class).delete(getTodoIdColumn().getSelectedValues());

                            fetchTodos();
                        }
                    }

                    @Override
                    public boolean isAutoResizeColumns() {
                        return true;
                    }

                    @Override
                    protected boolean getConfiguredHeaderEnabled() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredHeaderMenusEnabled() {
                        return false;
                    }

                    private void updateTodo(Integer todoId, String content, Boolean completed) {
                        BEANS.get(ITodoService.class).updateTodo(todoId, content, completed);
                    }

                    @Override
                    protected boolean getConfiguredHeaderVisible() {
                        return false;
                    }

                    public CompletedColumn getCompletedColumn() {
                        return getColumnSet().getColumnByClass(CompletedColumn.class);
                    }

                    public NameColumn getNameColumn() {
                        return getColumnSet().getColumnByClass(NameColumn.class);
                    }

                    public TodoIdColumn getTodoIdColumn() {
                        return getColumnSet().getColumnByClass(TodoIdColumn.class);
                    }

                    @Order(1000)
                    public class TodoIdColumn extends AbstractIDColumn {

                    }

                    @Order(1500)
                    public class CompletedColumn extends AbstractBooleanColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return null;
                        }

                        @Override
                        protected boolean getConfiguredEditable() {
                            return true;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            cell.setBackgroundColor(getValue(row) ? Green.Green1 : null);
                        }

                        @Override
                        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                            super.execCompleteEdit(row, editingField);

                            updateTodo(getTodoIdColumn().getValue(row), getNameColumn().getValue(row), getValue(row));
                        }

                        @Override
                        public boolean isFixedWidth() {
                            return true;
                        }

                        @Override
                        public boolean isFixedPosition() {
                            return true;
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 30;
                        }
                    }

                    @Order(2000)
                    public class NameColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Todo");
                        }

                        @Override
                        protected boolean getConfiguredEditable() {
                            return true;
                        }

                        @Override
                        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                            super.execCompleteEdit(row, editingField);

                            updateTodo(getTodoIdColumn().getValue(row), getValue(row), getCompletedColumn().getValue(row));
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            cell.setTooltipText(getValue(row));
                        }
                    }

                }
            }

        }

    }

}
