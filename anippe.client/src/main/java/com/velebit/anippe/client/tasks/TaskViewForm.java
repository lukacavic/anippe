package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.SubTasksBox.ChildTasksProgressField;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.InformationsBox.RemindersTableField;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.InformationsBox.StartDateLabelField;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.InformationsBox.StatusLabelField;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskViewFormData;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.HeaderCell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.popup.AbstractFormPopup;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

@FormData(value = TaskViewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskViewForm extends AbstractForm {

    private Integer taskId;

    private Integer activeTimerId;
    private Task task;

    @FormData
    public Integer getActiveTimerId() {
        return activeTimerId;
    }

    @FormData
    public void setActiveTimerId(Integer activeTimerId) {
        this.activeTimerId = activeTimerId;
    }

    @FormData
    public Task getTask() {
        return task;
    }

    @FormData
    public void setTask(Task task) {
        this.task = task;
    }

    @FormData
    public Integer getTaskId() {
        return taskId;
    }

    @FormData
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
        return false;
    }

    @Override
    protected String getConfiguredTitle() {
        return "Otvoriti kliniku Šuderkla";
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Tasks;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return "Vezan za: 4aMed";
    }

    public GroupBox.DetailsBox.CommentsBox.ActivityLogTableField getActivityLogTableField() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.ActivityLogTableField.class);
    }

    public GroupBox.DetailsBox.CommentsBox.AddCommentButtonsSequenceBox.AddCommentAttachmentButton getAddCommentAttachmentButton() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.AddCommentButtonsSequenceBox.AddCommentAttachmentButton.class);
    }

    public GroupBox.DetailsBox.CommentsBox.AddCommentButtonsSequenceBox.AddCommentButton getAddCommentButton() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.AddCommentButtonsSequenceBox.AddCommentButton.class);
    }

    public GroupBox.DetailsBox.CommentsBox.AddCommentButtonsSequenceBox getAddCommentButtonsSequenceBox() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.AddCommentButtonsSequenceBox.class);
    }


    public GroupBox.DetailsBox.CommentsBox.CommentField getCommentField() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.CommentField.class);
    }

    public GroupBox.DetailsBox.CommentsBox getCommentsBox() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.class);
    }

    public GroupBox.DetailsBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DetailsBox.DescriptionField.class);
    }

    public GroupBox.DetailsBox getDetailsBox() {
        return getFieldByClass(GroupBox.DetailsBox.class);
    }

    public GroupBox.InformationsBox.DueDateLabelField getDueDateLabelField() {
        return getFieldByClass(GroupBox.InformationsBox.DueDateLabelField.class);
    }

    public GroupBox.InformationsBox.FollowersTableField getFollowersTableField() {
        return getFieldByClass(GroupBox.InformationsBox.FollowersTableField.class);
    }

    public ChildTasksProgressField getChildTasksProgressField() {
        return getFieldByClass(ChildTasksProgressField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.InformationsBox getInformationsBox() {
        return getFieldByClass(GroupBox.InformationsBox.class);
    }

    public GroupBox.InformationsBox.PriorityLabelField getPriorityLabelField() {
        return getFieldByClass(GroupBox.InformationsBox.PriorityLabelField.class);
    }

    public RemindersTableField getRemindersTableField() {
        return getFieldByClass(RemindersTableField.class);
    }

    public StartDateLabelField getStartDateLabelField() {
        return getFieldByClass(StartDateLabelField.class);
    }

    public StatusLabelField getStatusLabelField() {
        return getFieldByClass(StatusLabelField.class);
    }

    public GroupBox.DetailsBox.SubTasksBox getSubTasksBox() {
        return getFieldByClass(GroupBox.DetailsBox.SubTasksBox.class);
    }

    public GroupBox.DetailsBox.SubTasksBox.SubTasksTableField getSubTasksTableField() {
        return getFieldByClass(GroupBox.DetailsBox.SubTasksBox.SubTasksTableField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public void renderForm() {
        if (getTask() == null) {
            setTask(BEANS.get(ITaskViewService.class).find(getTaskId()));
        }
        getChildTasksProgressField().renderPercentageBar();
        Integer userId = ClientSession.get().getCurrentUser().getId();

        //MenuUtility.getMenuByClass(getGroupBox(), ToggleTimerMenu.class).setEnabled(task.isUserAssigned(userId));
    }

    public void fetchActivityLogs() {
        List<ActivityLogTableRowData> rows = BEANS.get(ITaskViewService.class).fetchComments(getTaskId());
        getActivityLogTableField().getTable().importFromTableRowBeanData(rows, ActivityLogTableRowData.class);

        getCommentsBox().setLabel(getCommentsBox().getConfiguredLabel() + " (" + rows.size() + ")");
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 3;
            }

            @Override
            public boolean isBorderVisible() {
                return false;
            }

            @Override
            protected String getConfiguredCssClass() {
                return CssClasses.TOP_PADDING_INVISIBLE;
            }

            @Order(0)
            public class MarkAsCompletedMenu extends AbstractMenu {

                @Override
                protected boolean getConfiguredToggleAction() {
                    return true;
                }

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected void execSelectionChanged(boolean selection) {
                    super.execSelectionChanged(selection);

                    BEANS.get(ITaskViewService.class).markAsCompleted(getTaskId(), selection);

                    renderForm();
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Check;
                }

            }

            @Order(250)
            public class TimerStatsMenu extends AbstractMenu {
                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Chart;
                }

                @Override
                protected void execAction() {
                    AbstractFormPopup<TaskTimersForm> popup = new AbstractFormPopup<TaskTimersForm>() {
                        @Override
                        protected TaskTimersForm createForm() {
                            TaskTimersForm form = new TaskTimersForm();
                            form.setTaskId(getTaskId());

                            return form;
                        }

                    };

                    popup.setAnchor(this);
                    popup.setCloseOnMouseDownOutside(true);
                    popup.setAnimateOpening(true);
                    popup.setHorizontalSwitch(true);
                    popup.setTrimWidth(true);
                    popup.setTrimHeight(true);
                    popup.setWithArrow(true);
                    popup.setClosable(true);
                    popup.setClosable(false);
                    popup.setCloseOnOtherPopupOpen(true);
                    popup.setMovable(false);
                    popup.setResizable(true);
                    popup.open();
                }
            }

            @Order(300)
            public class ToggleTimerMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("StartTimer");
                }

                @Override
                protected String getConfiguredCssClass() {
                    return "green-menu";
                }

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Clock;
                }

                private void renderTimerMenu() {
                    setText(getActiveTimerId() != null ? TEXTS.get("StopTimer") : getConfiguredText());

                    setCssClass(getActiveTimerId() != null ? "red-menu" : getConfiguredCssClass());
                }

                @Override
                protected void execAction() {
                    super.execAction();

                    setActiveTimerId(BEANS.get(ITaskViewService.class).toggleTimer(getTaskId(), getActiveTimerId()));

                    renderTimerMenu();
                }
            }

            @Order(1000)
            public class DetailsBox extends AbstractGroupBox {

                @Override
                protected String getConfiguredMenuBarPosition() {
                    return MENU_BAR_POSITION_TOP;
                }

                @Override
                protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                    return super.getConfiguredBodyLayoutConfig().withVGap(0);
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredLabelHtmlEnabled() {
                    return true;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }


                @Order(1000)
                public class DescriptionField extends AbstractTextAreaField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.span("Description").style("font-weight:bold;font-size:13px;"),
                                HTML.span("  "),
                                HTML.appLink("editDescription", HTML.icon(FontIcons.Pencil).style("color:#234d74;"))
                        );

                        return content.toHtml();
                    }

                    @Override
                    protected boolean getConfiguredEnabled() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredDisabledStyle() {
                        return DISABLED_STYLE_READ_ONLY;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                }

                @Order(2000)
                public class SubTasksBox extends AbstractGroupBox {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("SubTasks");
                    }


                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 1;
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
                            return FontIcons.Tasks;
                        }

                        @Override
                        protected void execAction() {
                            ITableRow row = getSubTasksTableField().getTable().addRow();
                            getSubTasksTableField().getTable().requestFocusInCell(getSubTasksTableField().getTable().getTaskColumn(), row);

                            getChildTasksProgressField().renderPercentageBar();
                        }
                    }

                    @Order(1000)
                    public class HideCompletedTasksMenu extends AbstractMenu {
                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("HideCompletedItems");
                        }

                        @Override
                        public boolean isVisible() {
                            return false;
                        }

                        @Override
                        protected byte getConfiguredHorizontalAlignment() {
                            return 1;
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


                    @Order(1000)
                    public class SubTasksTableField extends AbstractTableField<SubTasksTableField.Table> {
                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 4;
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

                            public ActionsColumn getActionsColumn() {
                                return getColumnSet().getColumnByClass(ActionsColumn.class);
                            }

                            public ChildTaskIdColumn getChildTaskIdColumn() {
                                return getColumnSet().getColumnByClass(ChildTaskIdColumn.class);
                            }

                            public CompletedAtColumn getCompletedAtColumn() {
                                return getColumnSet().getColumnByClass(CompletedAtColumn.class);
                            }

                            public CompletedColumn getCompletedColumn() {
                                return getColumnSet().getColumnByClass(CompletedColumn.class);
                            }

                            public CreatedByColumn getCreatedByColumn() {
                                return getColumnSet().getColumnByClass(CreatedByColumn.class);
                            }

                            public CreatedAtColumn getCreatedAtColumn() {
                                return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                            }

                            public TaskColumn getTaskColumn() {
                                return getColumnSet().getColumnByClass(TaskColumn.class);
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
                                        BEANS.get(ITaskViewService.class).deleteChildTask(getChildTaskIdColumn().getSelectedValue());

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

                                    BEANS.get(ITaskViewService.class).updateCompleted(getChildTaskIdColumn().getValue(row), getValue(row));

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

                                    Integer childTaskId = BEANS.get(ITaskViewService.class).updateChildTask(getTaskId(), getChildTaskIdColumn().getValue(row), getValue(row));

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

                @Order(3000)
                public class CommentsBox extends AbstractGroupBox {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Comments");
                    }

                    @Override
                    protected boolean getConfiguredExpanded() {
                        return true;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected boolean getConfiguredExpandable() {
                        return true;
                    }

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 1;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Order(1000)
                    public class CommentField extends AbstractTextAreaField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("AddComment");
                        }

                        @Override
                        protected void execChangedDisplayText() {
                            super.execChangedDisplayText();

                            getAddCommentButton().setEnabled(!StringUtility.isNullOrEmpty(getDisplayText()));
                            getAddCommentAttachmentButton().setEnabled(!StringUtility.isNullOrEmpty(getDisplayText()));
                        }

                        @Override
                        protected boolean getConfiguredUpdateDisplayTextOnModify() {
                            return true;
                        }

                        @Override
                        protected int getConfiguredUpdateDisplayTextOnModifyDelay() {
                            return 100;
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_ON_FIELD;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }
                    }

                    @Order(1250)
                    public class AddCommentButtonsSequenceBox extends AbstractSequenceBox {
                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredEnabled() {
                            return false;
                        }

                        @Override
                        protected LogicalGridLayoutConfig getConfiguredLayoutConfig() {
                            return super.getConfiguredLayoutConfig().withHGap(5);
                        }

                        @Override
                        public boolean isStatusVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredAutoCheckFromTo() {
                            return false;
                        }

                        @Order(1500)
                        public class AddCommentAttachmentButton extends AbstractButton {
                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.Paperclip;
                            }

                            @Override
                            protected boolean getConfiguredInheritAccessibility() {
                                return false;
                            }

                            @Override
                            protected boolean getConfiguredProcessButton() {
                                return false;
                            }

                            @Override
                            protected void execClickAction() {
                                List<BinaryResource> chooser = new FileChooser(true).startChooser();
                                if (!CollectionUtility.isEmpty(chooser)) return;
                            }
                        }

                        @Order(2000)
                        public class AddCommentButton extends AbstractButton {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Save");
                            }

                            @Override
                            public boolean isProcessButton() {
                                return false;
                            }

                            @Override
                            protected boolean getConfiguredInheritAccessibility() {
                                return false;
                            }

                            @Override
                            protected boolean getConfiguredEnabled() {
                                return false;
                            }

                            @Override
                            protected Boolean getConfiguredDefaultButton() {
                                return true;
                            }

                            @Override
                            public boolean isStatusVisible() {
                                return false;
                            }

                            @Override
                            protected void execClickAction() {
                                if (StringUtility.isNullOrEmpty(getCommentField().getValue())) {
                                    NotificationHelper.showErrorNotification(TEXTS.get("CommentIsEmpty"));
                                    return;
                                }

                                BEANS.get(ITaskViewService.class).addComment(getTaskId(), getCommentField().getValue());
                                getCommentField().setValue(null);

                                fetchActivityLogs();
                            }
                        }
                    }


                    @Order(3000)
                    public class ActivityLogTableField extends AbstractTableField<ActivityLogTableField.Table> {
                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 4;
                        }

                        @ClassId("938d486d-5c5b-471a-bc19-9dda44c9239e")
                        public class Table extends AbstractTable {

                            public boolean isMyComment(ITableRow row) {
                                if (getCreatedByIdColumn().getValue(row) == null) return false;

                                return getCreatedByIdColumn().getValue(row).equals(ClientSession.get().getCurrentUser().getId());
                            }

                            @Override
                            protected void execInitTable() {
                                super.execInitTable();

                                addRow();
                            }

                            @Override
                            protected boolean getConfiguredHeaderVisible() {
                                return false;
                            }

                            public ActivityLogColumn getActivityLogColumn() {
                                return getColumnSet().getColumnByClass(ActivityLogColumn.class);
                            }

                            public ActivityLogIdColumn getActivityLogIdColumn() {
                                return getColumnSet().getColumnByClass(ActivityLogIdColumn.class);
                            }

                            public CreatedAtColumn getCreatedAtColumn() {
                                return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                            }

                            public CreatedByColumn getCreatedByColumn() {
                                return getColumnSet().getColumnByClass(CreatedByColumn.class);
                            }

                            public CreatedByIdColumn getCreatedByIdColumn() {
                                return getColumnSet().getColumnByClass(CreatedByIdColumn.class);
                            }

                            @Override
                            protected boolean getConfiguredAutoResizeColumns() {
                                return true;
                            }

                            @Order(1000)
                            public class DeleteMenu extends AbstractDeleteMenu {

                                @Override
                                protected void execAction() {
                                    if (!isMyComment(getSelectedRow())) {
                                        NotificationHelper.showErrorNotification(TEXTS.get("ThisIsCommentFromAnotherUser"));
                                        return;
                                    }

                                    BEANS.get(ITaskViewService.class).deleteActivityLog(getActivityLogIdColumn().getSelectedValue());

                                    fetchActivityLogs();
                                }
                            }

                            @Order(-1000)
                            public class ActivityLogIdColumn extends AbstractIDColumn {

                            }

                            @Order(0)
                            public class CreatedAtColumn extends AbstractDateTimeColumn {
                                @Override
                                public boolean isDisplayable() {
                                    return false;
                                }
                            }

                            @Order(500)
                            public class CreatedByColumn extends AbstractStringColumn {
                                @Override
                                public boolean isDisplayable() {
                                    return false;
                                }
                            }

                            @Order(750)
                            public class CreatedByIdColumn extends AbstractIDColumn {

                            }

                            @Order(1000)
                            public class ActivityLogColumn extends AbstractStringColumn {

                                @Override
                                protected boolean getConfiguredHtmlEnabled() {
                                    return true;
                                }

                                @Override
                                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                                    super.execCompleteEdit(row, editingField);

                                    BEANS.get(ITaskViewService.class).updateActivityLog(getActivityLogIdColumn().getValue(row), getValue(row));
                                }


                                @Override
                                protected void execDecorateCell(Cell cell, ITableRow row) {
                                    super.execDecorateCell(cell, row);

                                    String comment = getActivityLogColumn().getValue(row);
                                    String createdBy = getCreatedByColumn().getValue(row);
                                    String createdAt = new PrettyTime().format(getCreatedAtColumn().getValue(row));

                                    IHtmlContent content = HTML.fragment(
                                            HTML.span(comment),
                                            HTML.br(),
                                            HTML.italic(createdBy, ", ", createdAt).style("margin-top:5px;margin-bottom:0px; color:#3a3a3a;font-size:10px;")
                                    );

                                    cell.setText(content.toHtml());
                                    cell.setEditable(isMyComment(row));
                                }
                            }
                        }
                    }

                }

            }

            @Order(2000)
            public class InformationsBox extends AbstractGroupBox {

                @Override
                protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                    return super.getConfiguredBodyLayoutConfig().withVGap(0);
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected String getConfiguredMenuBarPosition() {
                    return MENU_BAR_POSITION_TITLE;
                }

                @Order(1000)
                public class ActionsMenu extends AbstractMenu {
                    @Override
                    protected byte getConfiguredHorizontalAlignment() {
                        return 1;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Menu;
                    }

                    @Order(1000)
                    public class EditMenu extends AbstractEditMenu {


                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(2000)
                    public class CopyMenu extends AbstractEditMenu {
                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("Clone");
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Clone;
                        }

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(3000)
                    public class DeleteMenu extends AbstractDeleteMenu {


                        @Override
                        protected void execAction() {

                        }
                    }
                }

                @Order(0)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class StatusLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.span(HTML.icon(FontIcons.Star), HTML.appLink("status", HTML.span(" Status:").style("border-bottom:1px dashed #333;"))).style("font-size:13px;")
                        );

                        return content.toHtml();
                    }

                    @Override
                    protected void execAppLinkAction(String ref) {
                        super.execAppLinkAction(ref);
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("In progress")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }
                }

                @Order(1000)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class PriorityLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.span(HTML.icon(FontIcons.Star), HTML.appLink("priority", HTML.span(" Priority:").style("border-bottom:1px dashed #333;"))).style("font-size:13px;")
                        );

                        return content.toHtml();
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("Urgent").style("color:red;")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }


                }

                @Order(1500)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class StartDateLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Clock), HTML.span(" Start Date:")
                        );

                        return content.toHtml();
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("01.11.2023")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }
                }

                @Order(2000)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class DueDateLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Calendar), HTML.span(" Due date:")
                        );

                        return content.toHtml();
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("21.11.2023")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }
                }

                @Order(3000)
                public class RemindersTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<RemindersTableField.Table> {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Clock),
                                HTML.span(" "),
                                HTML.span("Reminders").style("font-weight:bold;font-size:13px;"),
                                HTML.span("  "),
                                HTML.appLink("addReminder", HTML.span("(Add Reminder)").style("color:#234d74;"))
                        );

                        return content.toHtml();
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }


                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @ClassId("41c152c7-66ec-498f-9ab1-7eaafc01441c")
                    public class Table extends AbstractTable {
                        @Override
                        protected boolean getConfiguredHeaderVisible() {
                            return false;
                        }
                    }
                }

                @Order(4000)
                public class FollowersTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<FollowersTableField.Table> {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Users1),
                                HTML.span(" "),
                                HTML.span("Followers").style("font-weight:bold;font-size:13px;"),
                                HTML.span("  "),
                                HTML.appLink("addReminder", HTML.span("(Add Follower)").style("color:#234d74;"))
                        );

                        return content.toHtml();
                    }


                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @ClassId("41c152c7-66ec-498f-9ab1-7eaafc01441c")
                    public class Table extends AbstractTable {
                        @Override
                        protected boolean getConfiguredHeaderVisible() {
                            return false;
                        }
                    }
                }


            }
        }


    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            renderForm();
        }

        @Override
        protected void execStore() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).store(formData);
            importFormData(formData);
        }
    }
}
