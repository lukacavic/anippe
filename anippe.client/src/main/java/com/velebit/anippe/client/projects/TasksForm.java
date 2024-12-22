package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.common.menus.AbstractOpenMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.projects.TasksForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.TasksForm.MainBox.GroupBox.TasksTableField.Table;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.tasks.TaskStatusLookupCall;
import com.velebit.anippe.client.tasks.TaskViewForm;
import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.constants.ColorConstants;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.Priority;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.constants.Constants.TaskStatus;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.ProjectLookupCall;
import com.velebit.anippe.shared.projects.TasksFormData;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.*;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlElement;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Date;
import java.util.List;
import java.util.Set;

@FormData(value = TasksFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TasksForm extends AbstractForm {

    private Integer relatedType;
    private Integer relatedId;

    private Integer projectId;
    private boolean myTasks;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public boolean isMyTasks() {
        return myTasks;
    }

    public void setMyTasks(boolean myTasks) {
        this.myTasks = myTasks;
    }

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

    public GroupBox.FilterBox.AssignedUserField getAssignedUserField() {
        return getFieldByClass(GroupBox.FilterBox.AssignedUserField.class);
    }

    public GroupBox.FilterBox getFilterBox() {
        return getFieldByClass(GroupBox.FilterBox.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.FilterBox.PriorityField getPriorityField() {
        return getFieldByClass(GroupBox.FilterBox.PriorityField.class);
    }

    public GroupBox.FilterBox.ProjectField getProjectField() {
        return getFieldByClass(GroupBox.FilterBox.ProjectField.class);
    }

    public GroupBox.FilterBox.StatusField getStatusField() {
        return getFieldByClass(GroupBox.FilterBox.StatusField.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        getProjectField().setEnabled(getRelatedType() != null && !getRelatedType().equals(Related.PROJECT));
        if (getRelatedType() != null && getRelatedType().equals(Related.PROJECT)) {
            getProjectField().setValue(getRelatedId().longValue());
        }

        if (myTasks) {
            getAssignedUserField().setValue(ClientSession.get().getCurrentUser().getId().longValue());
            getAssignedUserField().setEnabled(false);
        }

        fetchTasks();
    }

    public GroupBox.TasksTableField getTasksTableField() {
        return getFieldByClass(GroupBox.TasksTableField.class);
    }

    public void fetchTasks() {
        TaskRequest request = new TaskRequest();
        request.setRelatedId(getRelatedId());
        request.setRelatedType(getRelatedType());

        if (getStatusField().getValue() != null) {
            request.setStatusIds(CollectionUtility.arrayList(getStatusField().getValue()));
        }

        if (getPriorityField().getValue() != null) {
            request.setPriorityIds(CollectionUtility.arrayList(getPriorityField().getValue()));
        }

        if (getAssignedUserField().getValue() != null) {
            request.setAssignedUserIds(CollectionUtility.arrayList(getAssignedUserField().getValue().intValue()));
        }

        List<TasksFormData.TasksTable.TasksTableRowData> rows = BEANS.get(ITasksService.class).fetchTasks(request);
        getTasksTableField().getTable().importFromTableRowBeanData(rows, TasksFormData.TasksTable.TasksTableRowData.class);
    }

    public void startNew() {
        startInternal(new NewHandler());
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
            public class FilterBox extends AbstractSequenceBox {

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("FilterBy");
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Order(0)
                public class ProjectField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Project");
                    }

                    @Override
                    protected int getConfiguredDisabledStyle() {
                        return DISABLED_STYLE_READ_ONLY;
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return ProjectLookupCall.class;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        ProjectLookupCall c = (ProjectLookupCall) call;

                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }
                }

                @Order(500)
                public class StatusField extends AbstractSmartField<Integer> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Status");
                    }

                    @Override
                    protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                        return TaskStatusLookupCall.class;
                    }

                    @Override
                    protected void execChangedValue() {
                        super.execChangedValue();

                        fetchTasks();
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }
                }

                @Order(1000)
                public class PriorityField extends AbstractSmartField<Integer> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Priority");
                    }

                    @Override
                    protected void execChangedValue() {
                        super.execChangedValue();

                        fetchTasks();
                    }

                    @Override
                    protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                        return PriorityLookupCall.class;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }
                }

                @Order(2000)
                public class AssignedUserField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("AssignedUser");
                    }

                    @Override
                    protected void execChangedValue() {
                        super.execChangedValue();

                        fetchTasks();
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return UserLookupCall.class;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        if (getProjectField().getValue() == null) return;
                        UserLookupCall c = (UserLookupCall) call;
                        c.setProjectId(getProjectField().getValue().intValue());
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
                    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
                        return OpenMenu.class;
                    }

                    public AssignedToColumn getAssignedToColumn() {
                        return getColumnSet().getColumnByClass(AssignedToColumn.class);
                    }

                    public CreatedByColumn getCreatedByColumn() {
                        return getColumnSet().getColumnByClass(CreatedByColumn.class);
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

                    @Order(-1000)
                    public class OpenMenu extends AbstractOpenMenu {

                        @Override
                        protected void execAction() {
                            TaskViewForm form = new TaskViewForm();
                            form.setTaskId(getTaskColumn().getSelectedValue().getId());
                            form.startModify();
                            form.waitFor();
                            if (form.isFormStored())
                                fetchTasks();
                        }
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

                    @Order(1000)
                    public class EditMenu extends AbstractEditMenu {

                        @Override
                        protected void execAction() {
                            TaskForm form = new TaskForm();
                            form.setTaskId(getTaskColumn().getSelectedValue().getId());
                            form.startModify();
                            form.waitFor();
                            if (form.isFormStored()) {
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            super.execDecorateCell(cell, row);

                            IHtmlElement hasAttachment = getTaskColumn() != null && getTaskColumn().getValue(row) != null && getTaskColumn().getValue(row).getAttachmentsCount() > 0 ? HTML.icon(FontIcons.Paperclip).style("margin-right:3px;") : null;

                            String content = HTML.fragment(
                                    HTML.span(hasAttachment, getValue(row)),
                                    HTML.br(),
                                    HTML.span(ObjectUtility.nvl("Vezan za: Poliklinika Sinteza", "")).cssClass(ICustomCssClasses.TABLE_HTML_CELL_SUB_HEADING)
                            ).toHtml();

                            cell.setText(content);
                        }
                    }

                    @Order(2500)
                    public class CreatedByColumn extends AbstractStringColumn {
                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("CreatedBy");
                        }

                        @Override
                        protected boolean getConfiguredFixedWidth() {
                            return true;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            String creator = getTaskColumn().getValue(row).getCreator().getFullName();

                            cell.setTooltipText(creator);
                            cell.setText(HTML.img("https://api.dicebear.com/9.x/initials/svg?radius=50&scale=70&seed=" + StringUtility.trim(creator)).style("max-width:30px;").toHtml());
                        }

                        @Override
                        protected int getConfiguredHorizontalAlignment() {
                            return 0;
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 70;
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
                        protected boolean getConfiguredFixedWidth() {
                            return true;
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 200;
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
                            } else if (getValue(row).equals(TaskStatus.AWAITING_FEEDBACK)) {
                                cell.setIconId(FontIcons.Note);
                            } else if (getValue(row).equals(TaskStatus.TESTING)) {
                                cell.setIconId(FontIcons.Users1);
                            } else if (getValue(row).equals(TaskStatus.CREATED)) {
                                cell.setIconId(FontIcons.Pencil);
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
                        public boolean isDisplayable() {
                            return false;
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(5000)
                    public class DeadlineAtColumn extends AbstractDateColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("DeadlineAt");
                        }

                        @Override
                        protected boolean getConfiguredFixedWidth() {
                            return true;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            super.execDecorateCell(cell, row);

                            if (DateUtility.isSameDay(getValue(row), new Date())) {
                                cell.setIconId(Icons.ExclamationCircleRed);
                            }
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 140;
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
                        protected boolean getConfiguredFixedWidth() {
                            return true;
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
                                cell.setIconId(Icons.RedCircle);
                            } else if (getValue(row).equals(Constants.Priority.HIGH)) {
                                cell.setIconId(Icons.OrangeCircle);
                            } else if (getValue(row).equals(Priority.NORMAL)) {
                                cell.setIconId(Icons.YellowCircle);
                            } else if (getValue(row).equals(Priority.LOW)) {
                                cell.setIconId(Icons.GrayCircle);
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
                            return 130;
                        }
                    }
                }
            }
        }
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
