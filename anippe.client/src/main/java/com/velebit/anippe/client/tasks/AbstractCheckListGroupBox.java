package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.AbstractCheckListGroupBox.SubTasksTableField.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.TaskCheckList;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.HeaderCell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.ocpsoft.prettytime.PrettyTime;

public abstract class AbstractCheckListGroupBox extends AbstractGroupBox {

    private Integer taskId;

    private TaskCheckList taskCheckList;

    public TaskCheckList getTaskCheckList() {
        return taskCheckList;
    }

    public Integer getTaskId() {
        return taskId;
    }

    @Override
    protected String getConfiguredLabel() {
        return getTaskCheckList().getName();
    }

    public SubTasksTableField getSubTasksTableField() {
        return getFieldByClass(SubTasksTableField.class);
    }

    public abstract void reloadComponent();

    @Override
    protected int getConfiguredGridColumnCount() {
        return 1;
    }

    @Override
    protected boolean getConfiguredExpandable() {
        return true;
    }

    @Override
    protected boolean getConfiguredExpanded() {
        return true;
    }

    public ChildTasksProgressField getChildTasksProgressField() {
        return getFieldByClass(ChildTasksProgressField.class);
    }

    @Override
    protected String getConfiguredMenuBarPosition() {
        return MENU_BAR_POSITION_TITLE;
    }

    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }

    @Order(0)
    public class AddSubTaskMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Plus;
        }

        @Override
        protected void execAction() {
            ITableRow row = getSubTasksTableField().getTable().addRow();
            getSubTasksTableField().getTable().requestFocusInCell(getSubTasksTableField().getTable().getTaskColumn(), row);

            getChildTasksProgressField().renderPercentageBar();
        }
    }

    @Order(0)
    public class ChildTasksProgressField extends AbstractHtmlField {
        @Override
        public boolean isLabelVisible() {
            return false;
        }

        @Override
        protected boolean getConfiguredGridUseUiWidth() {
            return true;
        }

        @Override
        protected boolean getConfiguredFillHorizontal() {
            return true;
        }

        @Override
        public boolean isStatusVisible() {
            return false;
        }

        @Override
        protected String getConfiguredCssClass() {
            return "TaskViewForm_CheckListProgressBar";
        }

        public void renderPercentageBar() {
            String percentage = String.format("%.2f", calculateCompletionPercentage());

            IHtmlContent content = HTML.fragment(
                    HTML.span(String.valueOf(percentage) + "%").style("width:" + percentage + "%;background-color:#234d74;height:100%;color:white;padding:5px;display:block;")
            );

            setValue(content.toHtml());
        }

        private double calculateCompletionPercentage() {
            int totalTasks = getSubTasksTableField().getTable().getRowCount();
            int completedTasks = (int) getSubTasksTableField().getTable().getRows().stream().filter(r -> getSubTasksTableField().getTable().getCompletedColumn().getValue(r).equals(Boolean.TRUE)).count();

            if (totalTasks == 0) return 0.0;
            if (completedTasks == 0) return 0.0;

            return (double) (completedTasks * 100) / totalTasks;
        }
    }

    @Order(500)
    public class ActionsMenu extends AbstractActionsMenu {
        @Override
        protected byte getConfiguredHorizontalAlignment() {
            return 1;
        }

        @Override
        protected String getConfiguredText() {
            return null;
        }

        @Override
        protected boolean getConfiguredVisible() {
            return true;
        }

        @Order(0)
        public class HideCompletedTasksMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("HideCompletedItems");
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Filter;
            }

            @Override
            protected boolean getConfiguredToggleAction() {
                return true;
            }

            @Override
            protected void execSelectionChanged(boolean selection) {
                super.execSelectionChanged(selection);

                setText(selection ? TEXTS.get("ShowCompleted") : TEXTS.get("HideCompleted"));
            }

        }

        @Order(1)
        public class DeleteMenu extends AbstractDeleteMenu {
            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected void execAction() {
                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(ITaskViewService.class).deleteCheckList(getTaskCheckList().getId());
                    NotificationHelper.showSaveSuccessNotification();

                    reloadComponent();
                }
            }
        }
    }


    @Order(1000)
    public class SubTasksTableField extends AbstractTableField<Table> {
        @Override
        public boolean isLabelVisible() {
            return false;
        }

        @Override
        protected int getConfiguredGridH() {
            return 3;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @ClassId("2d4f86d4-9e72-463e-8f57-72390387f171")
        public class Table extends AbstractTable {

            private static final String APP_LINK_DELETE = "delete";
            private static final String APP_LINK_ASSIGN = "assign";
            private static final String APP_LINK_SAVE_AS_TEMPLATE = "saveAsTemplate";

            @Override
            protected void execDecorateRow(ITableRow row) {
                super.execDecorateRow(row);

                row.setCssClass("vertical-align-middle");
            }

            public Table.ActionsColumn getActionsColumn() {
                return getColumnSet().getColumnByClass(Table.ActionsColumn.class);
            }

            public Table.ChildTaskIdColumn getChildTaskIdColumn() {
                return getColumnSet().getColumnByClass(Table.ChildTaskIdColumn.class);
            }

            public Table.CompletedAtColumn getCompletedAtColumn() {
                return getColumnSet().getColumnByClass(Table.CompletedAtColumn.class);
            }

            public Table.CompletedColumn getCompletedColumn() {
                return getColumnSet().getColumnByClass(Table.CompletedColumn.class);
            }

            public Table.CreatedByColumn getCreatedByColumn() {
                return getColumnSet().getColumnByClass(Table.CreatedByColumn.class);
            }

            public Table.CreatedAtColumn getCreatedAtColumn() {
                return getColumnSet().getColumnByClass(Table.CreatedAtColumn.class);
            }

            public Table.TaskColumn getTaskColumn() {
                return getColumnSet().getColumnByClass(Table.TaskColumn.class);
            }

            @Override
            protected boolean getConfiguredHeaderVisible() {
                return false;
            }

            @Override
            public boolean isAutoResizeColumns() {
                return true;
            }

            @Override
            public void doAppLinkAction(String ref) {
                super.doAppLinkAction(ref);

                switch (ref) {
                    case APP_LINK_DELETE:
                        BEANS.get(ITaskViewService.class).deleteTaskCheckListItem(getChildTaskIdColumn().getSelectedValue());

                        ITableRow row = getSelectedRow();
                        row.delete();

                        getChildTasksProgressField().renderPercentageBar();
                        break;
                    case APP_LINK_ASSIGN:
                        //Assign sub task to user. Show popup.
                        break;
                    case APP_LINK_SAVE_AS_TEMPLATE:
                        //Show form to save template.
                        break;
                }
            }

            @Order(0)
            public class ChildTaskIdColumn extends AbstractIDColumn {

            }

            @Order(500)
            public class CompletedAtColumn extends AbstractDateTimeColumn {
                @Override
                public boolean isDisplayable() {
                    return false;
                }
            }

            @Order(1000)
            public class CompletedColumn extends AbstractBooleanColumn {
                @Override
                protected boolean getConfiguredEditable() {
                    return true;
                }

                @Override
                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                    super.execCompleteEdit(row, editingField);

                    BEANS.get(ITaskViewService.class).updateTaskCheckListItemAsCompleted(getChildTaskIdColumn().getValue(row), getValue(row));

                    getChildTasksProgressField().renderPercentageBar();
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
                    return 50;
                }
            }

            @Order(1500)
            public class CreatedAtColumn extends AbstractDateTimeColumn {
                @Override
                public boolean isDisplayable() {
                    return false;
                }
            }

            @Order(1750)
            public class CreatedByColumn extends AbstractStringColumn {
                @Override
                public boolean isDisplayable() {
                    return false;
                }
            }

            @Order(2000)
            public class TaskColumn extends AbstractStringColumn {
                @Override
                protected boolean getConfiguredEditable() {
                    return true;
                }

                @Override
                protected int getConfiguredWidth() {
                    return 100;
                }

                @Override
                protected boolean getConfiguredHtmlEnabled() {
                    return true;
                }

                @Override
                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                    super.execCompleteEdit(row, editingField);

                    Integer childTaskId = BEANS.get(ITaskViewService.class).updateTaskCheckListItem(getTaskCheckList().getId(), getChildTaskIdColumn().getValue(row), getValue(row));

                    if (childTaskId != null) {
                        getChildTaskIdColumn().setValue(row, childTaskId);
                    }
                }

                @Override
                protected void execDecorateCell(Cell cell, ITableRow row) {
                    super.execDecorateCell(cell, row);

                    String description = getTaskColumn().getValue(row);
                    String createdAt = new PrettyTime().format(getCreatedAtColumn().getValue(row));
                    String createdBy = getCreatedByColumn().getValue(row);
                    String footer = StringUtility.join(" ", TEXTS.get("CreatedBy"), createdBy, createdAt);

                    IHtmlContent content = HTML.fragment(
                            HTML.p(description).style("margin-top:0px;margin-bottom:0px;"),
                            HTML.span(footer).style("font-size:11px;color#333;")
                    );

                    cell.setText(content.toHtml());
                }
            }

            @Order(3000)
            public class ActionsColumn extends AbstractStringColumn {
                @Override
                protected boolean getConfiguredHtmlEnabled() {
                    return true;
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
                protected void execDecorateHeaderCell(HeaderCell cell) {
                    super.execDecorateHeaderCell(cell);

                    cell.setText("");
                }

                @Override
                protected void execDecorateCell(Cell cell, ITableRow row) {
                    super.execDecorateCell(cell, row);

                    IHtmlContent content = HTML.fragment(
                            HTML.span(HTML.appLink("saveTemplate", HTML.icon(FontIcons.Clone))),
                            HTML.span(HTML.appLink("assign", HTML.icon(FontIcons.Users1))).style("margin-left:10px;"),
                            HTML.span(HTML.appLink("delete", HTML.icon(FontIcons.Remove))).style("margin-left:10px;")
                    );

                    cell.setText(content.toHtml());
                }

                @Override
                protected int getConfiguredWidth() {
                    return 100;
                }

                @Override
                protected int getConfiguredHorizontalAlignment() {
                    return 0;
                }
            }
        }
    }


}
