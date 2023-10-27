package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.projects.TasksForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.TasksForm.MainBox.GroupBox.TasksTableField.Table;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.tasks.TaskStatusLookupCall;
import com.velebit.anippe.client.tasks.TaskViewForm;
import com.velebit.anippe.shared.constants.ColorConstants;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.TasksFormData;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.Task;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;
import java.util.Set;

@FormData(value = TasksFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TasksForm extends AbstractForm {

    private Integer relatedType;
    private Integer relatedId;

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    @FormData
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Tasks");
    }

    public GroupBox.InformationBoxesBox.AwaitingFeedbackField getAwaitingFeedbackField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.AwaitingFeedbackField.class);
    }

    public GroupBox.InformationBoxesBox.CompletedField getCompletedField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.CompletedField.class);
    }

    public GroupBox.InformationBoxesBox.CreatedField getCreatedField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.CreatedField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.InformationBoxesBox.InProgressField getInProgressField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.InProgressField.class);
    }

    public GroupBox.InformationBoxesBox getInformationBoxesBox() {
        return getFieldByClass(GroupBox.InformationBoxesBox.class);
    }

    public GroupBox.TasksSummaryField getTasksSummaryField() {
        return getFieldByClass(GroupBox.TasksSummaryField.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        fetchTasks();
    }

    public GroupBox.TasksTableField getTasksTableField() {
        return getFieldByClass(GroupBox.TasksTableField.class);
    }

    public GroupBox.InformationBoxesBox.TestingField getTestingField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.TestingField.class);
    }

    public void fetchTasks() {
        List<TasksFormData.TasksTable.TasksTableRowData> rows = BEANS.get(ITasksService.class).fetchTasks(relatedType, relatedId);
        getTasksTableField().getTable().importFromTableRowBeanData(rows, TasksFormData.TasksTable.TasksTableRowData.class);
    }

    private void updateSummaryLabels() {
        int created = 0;
        int inProgress = 0;
        int testing = 0;
        int awaitingFeedback = 0;
        int completed = 0;

        for (ITableRow row : getTasksTableField().getTable().getRows()) {
            Integer statusId = getTasksTableField().getTable().getStatusColumn().getValue(row);

            if (statusId.equals(Constants.TaskStatus.CREATED)) {
                created++;
            } else if (statusId.equals(Constants.TaskStatus.IN_PROGRESS)) {
                inProgress++;
            } else if (statusId.equals(Constants.TaskStatus.TESTING)) {
                testing++;
            } else if (statusId.equals(Constants.TaskStatus.AWAITING_FEEDBACK)) {
                awaitingFeedback++;
            } else if (statusId.equals(Constants.TaskStatus.COMPLETED)) {
                completed++;
            }
        }

        getCreatedField().setCount(created);
        getCreatedField().renderContent();
        getInProgressField().setCount(inProgress);
        getInProgressField().renderContent();
        getTestingField().setCount(testing);
        getTestingField().renderContent();
        getAwaitingFeedbackField().setCount(awaitingFeedback);
        getAwaitingFeedbackField().renderContent();
        getCompletedField().setCount(completed);
        getCompletedField().renderContent();
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Override
            protected double getConfiguredGridWeightY() {
                return 1;
            }

            @Order(0)
            public class SwitchDisplayStyleMenu extends AbstractMenu {

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Menu;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(100)
            public class TasksOverview extends AbstractMenu {

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("TasksOverview");
                }

                @Override
                protected String getConfiguredCssClass() {
                    return "greenbutton";
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(-1000)
            @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
            public class TasksSummaryField extends AbstractLabelField {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredHtmlEnabled() {
                    return true;
                }

                @Override
                protected void execInitField() {
                    super.execInitField();
                    IHtmlContent content = HTML.fragment(HTML.bold("Tasks Summary").style("font-size:19px;font-weight:normal"));

                    setValue(content.toHtml());
                }
            }

            @Order(0)
            public class InformationBoxesBox extends AbstractGroupBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 5;
                }

                @Override
                public boolean isBorderVisible() {
                    return false;
                }

                @Order(1000)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class CreatedField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Created";
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 3";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#64748b";
                    }
                }

                @Order(2000)
                public class InProgressField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "In Progress";
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 3";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#3b82f6";
                    }
                }

                @Order(3000)
                public class TestingField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Testing";
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 1";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#0284c7";
                    }
                }

                @Order(4000)
                public class AwaitingFeedbackField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Awaiting Feedback";
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 1";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#84cc16";
                    }
                }

                @Order(5000)
                public class CompletedField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Completed";
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 33";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#22c55e";
                    }
                }

            }

            @Order(1000)
            public class TasksTableField extends AbstractTableField<Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected double getConfiguredGridWeightY() {
                    return -1;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredPreventInitialFocus() {
                    return true;
                }

                @ClassId("24b73347-a392-4f9d-94f0-4a204dab2ed9")
                public class Table extends AbstractTable {
                    @Override
                    protected void execContentChanged() {
                        super.execContentChanged();

                        updateSummaryLabels();
                    }

                    @Override
                    protected void execRowAction(ITableRow row) {
                        super.execRowAction(row);

                        TaskViewForm form = new TaskViewForm();
                        form.setTaskId(getTaskColumn().getSelectedValue().getId());
                        form.startModify();
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
                            fetchTasks();
                        }
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

                                fetchTasks();
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

                                fetchTasks();
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
    }


    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TasksFormData formData = new TasksFormData();
            exportFormData(formData);
            formData = BEANS.get(ITasksService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TasksFormData formData = new TasksFormData();
            exportFormData(formData);
            formData = BEANS.get(ITasksService.class).create(formData);
            importFormData(formData);
        }
    }

}
