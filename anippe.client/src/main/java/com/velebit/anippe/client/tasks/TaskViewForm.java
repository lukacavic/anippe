package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.clients.ClientCardForm;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.leads.LeadViewForm;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.ActionsMenu.ArchiveMenu;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.*;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.CommentsBox.ShowSystemTasksMenu;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.InformationsBox.StartDateLabelField;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.InformationsBox.StatusLabelField;
import com.velebit.anippe.client.tickets.TicketViewForm;
import com.velebit.anippe.shared.PriorityEnum;
import com.velebit.anippe.shared.RelatedEnum;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.*;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.action.menu.*;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.cell.ICell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.*;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.desktop.datachange.DataChangeEvent;
import org.eclipse.scout.rt.client.ui.desktop.datachange.IDataChangeListener;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.HorizontalGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.notification.INotification;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.client.ui.popup.AbstractFormPopup;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.html.IHtmlElement;
import org.eclipse.scout.rt.platform.html.IHtmlListElement;
import org.eclipse.scout.rt.platform.html.internal.HtmlListElement;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.*;
import java.util.stream.Collectors;

@FormData(value = TaskViewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskViewForm extends AbstractForm {

    private Integer taskId;

    private Integer activeTimerId;
    private Task task;
    private boolean followingTask;

    protected final IDataChangeListener m_dataChangeListener = this::onDataChanged;

    private void onDataChanged(DataChangeEvent dataChangeEvent) {
        renderForm();
    }

    @Override
    protected void execDisposeForm() {
        IDesktop desktop = IDesktop.CURRENT.get();
        if (desktop != null) {
            desktop.removeDataChangeListener(m_dataChangeListener);
        }
    }

    @FormData
    public boolean isFollowingTask() {
        return followingTask;
    }

    @FormData
    public void setFollowingTask(boolean followingTask) {
        this.followingTask = followingTask;
    }

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

    public GroupBox.DetailsBox.CommentsBox.ActivityLogTableField getActivityLogTableField() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.ActivityLogTableField.class);
    }

    public GroupBox.DetailsBox.InformationsBox.AssignedUsersLabelField getAssignedUsersLabelField() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.AssignedUsersLabelField.class);
    }

    public GroupBox.DetailsBox.AttachmentsBox getAttachmentsBox() {
        return getFieldByClass(GroupBox.DetailsBox.AttachmentsBox.class);
    }

    public GroupBox.DetailsBox.AttachmentsBox.AttachmentsTableField getAttachmentsTableField() {
        return getFieldByClass(GroupBox.DetailsBox.AttachmentsBox.AttachmentsTableField.class);
    }

    public GroupBox.DetailsBox.ChildTasksContainerBox getChildTasksContainerBox() {
        return getFieldByClass(GroupBox.DetailsBox.ChildTasksContainerBox.class);
    }


    public GroupBox.DetailsBox.CommentsBox.CommentField getCommentField() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.CommentField.class);
    }

    public GroupBox.DetailsBox.CommentsBox getCommentsBox() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.class);
    }

    public DetailsBox.InformationsBox.CompletedAtLabelField getCompletedAtLabelField() {
        return getFieldByClass(DetailsBox.InformationsBox.CompletedAtLabelField.class);
    }

    public GroupBox.DetailsBox.InformationsBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.DescriptionField.class);
    }

    public GroupBox.DetailsBox getDetailsBox() {
        return getFieldByClass(GroupBox.DetailsBox.class);
    }

    public GroupBox.DetailsBox.InformationsBox.DueDateLabelField getDueDateLabelField() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.DueDateLabelField.class);
    }

    public GroupBox.DetailsBox.InformationsBox.CreatedByLabelField getCreatedByLabelField() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.CreatedByLabelField.class);
    }

    public DetailsBox.InformationsBox.DescriptionEditBox getDescriptionEditBox() {
        return getFieldByClass(DetailsBox.InformationsBox.DescriptionEditBox.class);
    }

    public DetailsBox.InformationsBox.DescriptionEditBox.DescriptionEditField getDescriptionEditField() {
        return getFieldByClass(DetailsBox.InformationsBox.DescriptionEditBox.DescriptionEditField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        IDesktop desktop = IDesktop.CURRENT.get();
        if (desktop != null) {
            desktop.addDataChangeListener(m_dataChangeListener);
        }
    }

    public GroupBox.DetailsBox.InformationsBox getInformationsBox() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.class);
    }

    public GroupBox.DetailsBox.InformationsBox.PriorityLabelField getPriorityLabelField() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.PriorityLabelField.class);
    }

    public DetailsBox.InformationsBox.RelatedNameLabelField getRelatedNameLabelField() {
        return getFieldByClass(DetailsBox.InformationsBox.RelatedNameLabelField.class);
    }

    public StartDateLabelField getStartDateLabelField() {
        return getFieldByClass(StartDateLabelField.class);
    }

    public StatusLabelField getStatusLabelField() {
        return getFieldByClass(StatusLabelField.class);
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

    public void reloadTaskInternal() {
        setTask(BEANS.get(ITaskViewService.class).find(getTaskId()));
    }

    public void renderForm() {
        setTask(BEANS.get(ITaskViewService.class).find(getTaskId()));

        MenuUtility.getMenuByClass(getGroupBox(), AssignToMeMenu.class).setEnabled(!getTask().isAssignedTo(ClientSession.get().getCurrentUser().getId()));

        //Populate attachments
        getAttachmentsTableField().getTable().discardAllRows();
        for (Attachment attachment : getTask().getAttachments()) {
            ITableRow row = getAttachmentsTableField().getTable().addRow();
            getAttachmentsTableField().getTable().getAttachmentColumn().setValue(row, attachment);
            getAttachmentsTableField().getTable().getAttachmentIdColumn().setValue(row, attachment.getId());
            getAttachmentsTableField().getTable().getNameColumn().setValue(row, attachment.getName());
            getAttachmentsTableField().getTable().getSizeColumn().setValue(row, attachment.getFileSize());
            getAttachmentsTableField().getTable().getFormatColumn().setValue(row, attachment.getFileExtension());
        }

        //Set archived notification for task
        MenuUtility.getMenuByClass(getGroupBox(), ArchiveMenu.class).setText(getTask().isArchived() ? TEXTS.get("Unarchive") : TEXTS.get("Archive"));
        INotification archivedNotification = getTask().isArchived() ? new Notification(new Status(TEXTS.get("TaskIsArchived"), IStatus.ERROR, FontIcons.History)) : null;
        getDetailsBox().setNotification(archivedNotification);
        getDetailsBox().setEnabled(!getTask().isArchived());
        getGroupBox().setEnabled(!getTask().isArchived());

        MenuUtility.getMenuByClass(getGroupBox(), WatchMenu.class).setSelected(isFollowingTask());

        //Attachments table
        getAttachmentsBox().setVisible(getTask().hasAttachments());
        getDescriptionField().setValue(getTask().getDescription());

        setTitle(getTask().getTitle());
        setSubTitle(TEXTS.get("RelatedFor") + "." + RelatedEnum.fromValue(getTask().getRelatedType()).getName());

        IMenu completedMenu = MenuUtility.getMenuByClass(getGroupBox(), MarkAsCompletedMenu.class);
        completedMenu.setText(getTask().isCompleted() ? TEXTS.get("MarkAsNotCompleted") : TEXTS.get("MarkAsCompleted"));
        completedMenu.setIconId(getTask().isCompleted() ? FontIcons.Remove : FontIcons.Check);

        //Timer menu
        ToggleTimerMenu timerMenu = MenuUtility.getMenuByClass(getGroupBox(), ToggleTimerMenu.class);
        timerMenu.setEnabled(getTask().isAssignedTo(ClientSession.get().getCurrentUser().getId()));
        timerMenu.renderTimerMenu();

        renderInformationLabels();
        renderTaskStatusMenu();
        renderTaskCheckLists();
    }

    private void renderTaskCheckLists() {
        for (IFormField field : getChildTasksContainerBox().getFields()) {
            getChildTasksContainerBox().removeField(field);
        }

        if (getTask().getTaskCheckLists().isEmpty()) return;

        for (TaskCheckList taskCheckList : getTask().getTaskCheckLists()) {
            IFormField field = new AbstractCheckListGroupBox() {
                @Override
                public Integer getTaskId() {
                    return TaskViewForm.this.getTaskId();
                }

                @Override
                public Integer getProjectId() {
                    return TaskViewForm.this.getTask().getProjectId();
                }

                @Override
                public void reloadComponent() {
                    reloadTaskInternal();

                    TaskViewForm.this.renderTaskCheckLists();
                }

                @Override
                public TaskCheckList getTaskCheckList() {
                    return taskCheckList;
                }

                @Override
                public String getFieldId() {
                    return StringUtility.randomId();
                }
            };

            getChildTasksContainerBox().addField(field);

        }
    }

    private void renderInformationLabels() {
        boolean isDueDatePassed = getTask().getDeadlineAt() != null && getTask().getDeadlineAt().before(new Date());

        String relatedType = RelatedEnum.fromValue(getTask().getRelatedType()).getName();
        getRelatedNameLabelField().setValue(HTML.appLink("relatedName", HTML.bold(relatedType + ": " + getTask().getRelatedName()).style("color:234d74;font-size:15px;")).toHtml());
        getCreatedByLabelField().setValue(getTask().getCreator().getFullName());
        getStatusLabelField().setValue(TaskStatusEnum.fromValue(getTask().getStatusId()).getName());
        getStartDateLabelField().setValue(DateUtility.formatDate(getTask().getStartAt()));

        getCompletedAtLabelField().setVisible(getTask().isCompleted());
        getCompletedAtLabelField().setValue(DateUtility.formatDateTime(getTask().getCompletedAt()));

        getDueDateLabelField().setValue(isDueDatePassed && !getTask().isCompleted() ? HTML.bold(DateUtility.formatDate(getTask().getDeadlineAt())).style("color:#FF5555;").toHtml() : DateUtility.formatDate(getTask().getDeadlineAt()));
        getPriorityLabelField().setValue(PriorityEnum.fromValue(getTask().getPriorityId()).getName());
        getAssignedUsersLabelField().setValue(getTask().getAssignedUsers().stream().map(User::getFullName).collect(Collectors.joining(", ")));
    }

    private void renderTaskStatusMenu() {
        IMenu parent = MenuUtility.getMenuByClass(getGroupBox(), StatusMenu.class);
        parent.setText(TaskStatusEnum.fromValue(getTask().getStatusId()).getName());
        parent.setIconId(TaskStatusEnum.fromValue(getTask().getStatusId()).getIconId());

        parent.removeChildActions(parent.getChildActions());

        if (getTask() == null) return;

        if (getTask().getStatusId() == null) return;

        List<TaskStatusEnum> statuses = CollectionUtility.arrayList(Arrays.asList(TaskStatusEnum.values()));
        for (TaskStatusEnum status : statuses) {
            if (TaskViewForm.this.getTask().getStatusId().equals(status.getValue())) continue;

            parent.addChildAction(new AbstractMenu() {

                @Override
                public String getText() {
                    return status.getName();
                }

                @Override
                public String getIconId() {
                    return status.getIconId();
                }

                @Override
                protected void execAction() {
                    super.execAction();

                    BEANS.get(ITaskViewService.class).changeStatus(getTaskId(), status.getValue());

                    renderForm();
                }

            });
        }
    }

    public void fetchActivityLogs() {
        boolean withSystemLog = MenuUtility.getMenuByClass(getCommentsBox(), ShowSystemTasksMenu.class).isSelected();

        getActivityLogTableField().getTable().discardAllRows();

        List<ActivityLogTableRowData> rows = BEANS.get(ITaskViewService.class).fetchTaskActivityLog(getTaskId(), withSystemLog);
        getActivityLogTableField().getTable().importFromTableRowBeanData(rows, ActivityLogTableRowData.class);

        getCommentsBox().setLabel(getCommentsBox().getConfiguredLabel() + " (" + rows.size() + ")");
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
                protected String getConfiguredCssClass() {
                    return "green-menu";
                }

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("MarkAsCompleted");
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

            @Order(100)
            public class TimerStatsMenu extends AbstractMenu {
                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Chart;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
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
                    popup.setClosable(false);
                    popup.setCloseOnOtherPopupOpen(true);
                    popup.setMovable(false);
                    popup.setResizable(true);
                    popup.open();
                }
            }

            @Order(0)
            public class ToggleTimerMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return null;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Clock;
                }

                private void renderTimerMenu() {
                    setCssClass(getActiveTimerId() != null ? "red-menu" : getConfiguredCssClass());
                    setText(getActiveTimerId() != null ? TEXTS.get("StopTimer") : TEXTS.get("StartTimer"));
                }

                @Override
                protected void execAction() {
                    super.execAction();

                    Integer activeTimerId = BEANS.get(ITaskViewService.class).toggleTimer(getTaskId(), getActiveTimerId());


                    if (getActiveTimerId() != null) {
                        AbstractFormPopup<TimerNoteForm> popup = new AbstractFormPopup<TimerNoteForm>() {
                            @Override
                            protected TimerNoteForm createForm() {

                                TimerNoteForm form = new TimerNoteForm();
                                form.setTaskTimerId(getActiveTimerId());

                                return form;
                            }

                            @Override
                            protected void startForm() {
                                getContent().startNew();
                            }
                        };


                        popup.setAnchor(this);
                        popup.setCloseOnMouseDownOutside(false);
                        popup.setAnimateOpening(true);
                        popup.setHorizontalSwitch(true);
                        popup.setTrimWidth(true);
                        popup.setTrimHeight(true);
                        popup.setWithArrow(true);
                        popup.setClosable(true);
                        popup.setCloseOnOtherPopupOpen(false);
                        popup.setMovable(false);
                        popup.setResizable(true);
                        popup.open();
                    }

                    setActiveTimerId(activeTimerId);

                    renderTimerMenu();
                }
            }

            @Order(300)
            public class AssignToMeMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("AssignToMe");
                }

                @Override
                protected String getConfiguredCssClass() {
                    return "blue-menu";
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.UserPlus;
                }

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected void execAction() {
                    BEANS.get(ITaskViewService.class).assignToMe(getTaskId());

                    NotificationHelper.showSaveSuccessNotification();

                    renderForm();
                }
            }

            @Order(400)
            public class SelectParticipantsMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return null;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Users1;
                }

                @Override
                protected void execAction() {
                    super.execAction();

                    AbstractFormPopup<SelectUserListBoxForm> popup = new AbstractFormPopup<SelectUserListBoxForm>() {
                        @Override
                        protected SelectUserListBoxForm createForm() {
                            List<Long> currentUsers = getTask().getAssignedUsers().stream().map(t -> t.getId().longValue()).collect(Collectors.toList());

                            SelectUserListBoxForm form = new SelectUserListBoxForm();
                            form.setUserIds(currentUsers);
                            form.setProjectId(getTask().getProjectId());
                            form.setTaskId(getTaskId());

                            return form;
                        }

                        @Override
                        protected void startForm() {
                            getContent().startNew();
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

            @Order(500)
            public class StatusMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("Status");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.List;
                }
            }

            @Order(650)
            public class AddCheckListMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return null;//TEXTS.get("AddCheckList");
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.ListNumbered;
                }

                @Override
                protected void execAction() {
                    AbstractFormPopup<CreateTaskCheckListForm> popup = getCreateTaskCheckListFormAbstractFormPopup();
                    popup.setAnchor(this);
                    popup.setCloseOnMouseDownOutside(true);
                    popup.setAnimateOpening(true);
                    popup.setHorizontalSwitch(true);
                    popup.setTrimWidth(true);
                    popup.setTrimHeight(true);
                    popup.setWithArrow(true);
                    popup.setClosable(false);
                    popup.setCloseOnOtherPopupOpen(true);
                    popup.setMovable(false);
                    popup.setResizable(true);
                    popup.open();

                    /*CreateTaskCheckListForm form = new CreateTaskCheckListForm();
                    form.setTaskId(getTaskId());
                    form.startNew();
                    form.waitFor();
                    if (form.isFormStored()) {
                        NotificationHelper.showSaveSuccessNotification();

                        renderForm();
                    }*/
                }

                private AbstractFormPopup<CreateTaskCheckListForm> getCreateTaskCheckListFormAbstractFormPopup() {
                    AbstractFormPopup<CreateTaskCheckListForm> popup = new AbstractFormPopup<CreateTaskCheckListForm>() {
                        @Override
                        protected CreateTaskCheckListForm createForm() {
                            CreateTaskCheckListForm form = new CreateTaskCheckListForm();
                            form.setTaskId(getTaskId());

                            return form;
                        }

                        @Override
                        protected void startForm() {
                            getContent().startNew();
                        }
                    };

                    return popup;
                }
            }

            @Order(700)
            public class AddAttachmentMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return null;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Paperclip;
                }

                @Override
                protected void execAction() {
                    FileChooser chooser = new FileChooser();
                    List<BinaryResource> items = chooser.startChooser();
                    if (CollectionUtility.isEmpty(items)) return;

                    BEANS.get(ITaskViewService.class).addAttachments(getTaskId(), items);

                    NotificationHelper.showSaveSuccessNotification();

                    renderForm();
                }
            }

            @Order(750)
            public class WatchMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return null;
                }

                @Override
                protected void execSelectionChanged(boolean selection) {
                    super.execSelectionChanged(selection);

                    setCssClass(selection ? "yellow-menu" : null);

                    BEANS.get(ITaskViewService.class).followTask(getTaskId(), selection);
                }

                @Override
                protected String getConfiguredTooltipText() {
                    return TEXTS.get("WatchTask");
                }

                @Override
                protected boolean getConfiguredToggleAction() {
                    return true;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Star;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(800)
            public class ActionsMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("Actions");
                }

                @Override
                protected boolean getConfiguredStackable() {
                    return false;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Menu;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }


                @Order(1000)
                public class ArchiveMenu extends AbstractMenu {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("Archive");
                    }

                    @Override
                    protected boolean getConfiguredInheritAccessibility() {
                        return false;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.History;
                    }

                    @Override
                    protected void execAction() {
                        BEANS.get(ITaskViewService.class).archiveTask(getTaskId(), !getTask().isArchived());

                        NotificationHelper.showSaveSuccessNotification();

                        reloadTaskInternal();

                        setText(getTask().isArchived() ? TEXTS.get("Unarchive") : TEXTS.get("Archive"));

                        renderForm();
                    }
                }

                @Order(1500)
                public class EditMenu extends AbstractEditMenu {

                    @Override
                    protected void execAction() {
                        TaskForm form = new TaskForm();
                        form.setTaskId(getTaskId());
                        form.startModify();
                        form.waitFor();
                        if (form.isFormStored()) {
                            NotificationHelper.showSaveSuccessNotification();

                            renderForm();
                        }
                    }
                }

                @Order(2000)
                public class DeleteMenu extends AbstractDeleteMenu {

                    @Override
                    protected boolean getConfiguredInheritAccessibility() {
                        return false;
                    }

                    @Override
                    protected void execAction() {
                        if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                            BEANS.get(ITaskViewService.class).deleteTask(getTaskId());

                            NotificationHelper.showDeleteSuccessNotification();

                            doClose();
                        }
                    }
                }
            }

            @Order(1000)
            public class DetailsBox extends AbstractGroupBox {
                @Override
                public boolean isBorderVisible() {
                    return false;
                }

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
                    return 1;
                }

                @Override
                protected boolean getConfiguredLabelHtmlEnabled() {
                    return true;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Order(-2000)
                public class InformationsBox extends AbstractGroupBox {
                    @Override
                    protected Class<? extends IGroupBoxBodyGrid> getConfiguredBodyGrid() {
                        return HorizontalGroupBoxBodyGrid.class;
                    }

                    @Override
                    protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                        return super.getConfiguredBodyLayoutConfig().withVGap(0);
                    }

                    @Override
                    protected String getConfiguredCssClass() {
                        return ICustomCssClasses.TOP_PADDING_INVISIBLE;
                    }

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 4;
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

                    @Order(-1000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class RelatedNameLabelField extends AbstractLabelField {

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected void execAppLinkAction(String ref) {
                            super.execAppLinkAction(ref);

                            if (!ref.startsWith("relatedName")) return;

                            if (getTask().getRelatedType().equals(Related.TICKET)) {
                                TicketViewForm form = new TicketViewForm();
                                form.setTicketId(getTask().getRelatedId());
                                form.startModify();
                            } else if (getTask().getRelatedType().equals(Related.CLIENT)) {
                                ClientCardForm form = new ClientCardForm();
                                form.setClientId(getTask().getRelatedId());
                                form.startModify();
                            } else if (getTask().getRelatedType().equals(Related.LEAD)) {
                                LeadViewForm form = new LeadViewForm();
                                form.setLeadId(getTask().getRelatedId());
                                form.startModify();
                            }

                        }

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("RelatedFor");
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 4;
                        }

                        @Override
                        protected String getConfiguredCssClass() {
                            return ICustomCssClasses.TOP_PADDING_INVISIBLE;
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
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
                        protected int getConfiguredLabelWidthInPixel() {
                            return 55;
                        }

                        @Override
                        protected void execInitField() {
                            super.execInitField();

                            setValue(HTML.bold("Poliklinika Sinteza").style("color:#1561a7;font-size:14px;").toHtml());
                        }
                    }

                    @Order(0)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class StatusLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.Info), " ", TEXTS.get("Status")).toHtml();
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(1000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class PriorityLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.DiagramLine), " ", TEXTS.get("Priority")).toHtml();
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(1500)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class StartDateLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.Calendar), " ", TEXTS.get("StartDate")).toHtml();
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(2000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class DueDateLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.Clock), " ", TEXTS.get("DueDate")).toHtml();
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(3000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class CreatedByLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.UserPlus), " ", TEXTS.get("CreatedBy")).toHtml();
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(4000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class AssignedUsersLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.Users1), " ", TEXTS.get("AssignedUser")).toHtml();
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(4500)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class CompletedAtLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return HTML.fragment(HTML.icon(FontIcons.Check), " ", TEXTS.get("CompletedAt")).toHtml();
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(4625)
                    public class DescriptionEditBox extends AbstractGroupBox {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("DescriptionEdit");
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Override
                        protected String getConfiguredMenuBarPosition() {
                            return MENU_BAR_POSITION_TITLE;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 4;
                        }

                        @Override
                        protected boolean getConfiguredVisible() {
                            return false;
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return -1;
                        }

                        @Override
                        public boolean isBorderVisible() {
                            return false;
                        }

                        @Order(1000)
                        public class SaveDescriptionMenu extends AbstractMenu {
                            @Override
                            protected String getConfiguredText() {
                                return TEXTS.get("Save");
                            }

                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.Check;
                            }

                            @Override
                            protected byte getConfiguredHorizontalAlignment() {
                                return 1;
                            }

                            @Override
                            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
                            }

                            @Override
                            protected void execAction() {
                                if (StringUtility.isNullOrEmpty(getDescriptionEditField().getValue())) {
                                    NotificationHelper.showErrorNotification("Zadatak mora imati opis.");
                                    return;
                                }

                                BEANS.get(ITaskViewService.class).updateDescription(getDescriptionEditField().getValue(), getTaskId());

                                getDescriptionEditBox().setVisible(false);
                                getDescriptionField().setVisible(true);

                                renderForm();
                            }
                        }

                        @Order(4750)
                        public class DescriptionEditField extends AbstractTextEditorField {

                            @Override
                            protected boolean getConfiguredStatusVisible() {
                                return false;
                            }

                            @Override
                            protected int getConfiguredGridH() {
                                return 4;
                            }

                            @Override
                            protected boolean getConfiguredLabelHtmlEnabled() {
                                return true;
                            }

                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Description");
                            }

                            @Override
                            protected byte getConfiguredLabelPosition() {
                                return LABEL_POSITION_TOP;
                            }

                        }
                    }


                    @Order(5000)
                    public class DescriptionField extends AbstractLabelField {
                        @Override
                        protected int getConfiguredGridW() {
                            return 4;
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return -1;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredWrapText() {
                            return true;
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
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
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Description");
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Order(1000)
                        public class EditMenu extends AbstractEditMenu {

                            @Override
                            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                return CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType.NotNull);
                            }

                            @Override
                            protected void execAction() {
                                getDescriptionField().setVisible(false);
                                getDescriptionEditBox().setVisible(true);

                                getDescriptionEditField().setValue(getDescriptionField().getValue());
                            }
                        }

                    }

                }


                @Order(1500)
                public class ChildTasksContainerBox extends org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox {
                    @Override
                    public boolean isBorderVisible() {
                        return false;
                    }

                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }
                }

                @Order(2500)
                public class AttachmentsBox extends AbstractGroupBox {

                    @Override
                    protected int getConfiguredGridH() {
                        return 2;
                    }

                    @Override
                    protected boolean getConfiguredExpanded() {
                        return false;
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
                    protected boolean getConfiguredVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 1;
                    }

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Attachments");
                    }

                    @Override
                    protected double getConfiguredGridWeightY() {
                        return -1;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    public AttachmentsTableField getAttachmentsTableField() {
                        return getFieldByClass(AttachmentsTableField.class);
                    }

                    @Order(1000)
                    public class AttachmentsTableField extends AbstractTableField<AttachmentsTableField.Table> {
                        @Override
                        protected boolean getConfiguredLabelVisible() {
                            return false;
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return -1;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Override
                        protected void execInitField() {
                            //this.setLabel(this.getConfiguredLabel() + " (" + getAttachmentsTableField().getTable().getRowCount() + ")");
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 6;
                        }

                        public class Table extends AbstractTable {

                            @Override
                            protected boolean getConfiguredAutoResizeColumns() {
                                return true;
                            }

                            public BinaryResource findBinaryResourceToManage() {
                                BinaryResource binaryResource = null;

                                if (getBinaryResourceColumn().getSelectedValue() != null) {
                                    binaryResource = new BinaryResource(getNameColumn().getSelectedValue(), (byte[]) getBinaryResourceColumn().getSelectedValue());
                                } else {
                                    binaryResource = BEANS.get(IAttachmentService.class).download(getAttachmentIdColumn().getSelectedValue());
                                }

                                if (binaryResource == null) {
                                    NotificationHelper.showErrorNotification(TEXTS.get("ErrorReadingFile"));
                                    return null;
                                }
                                return binaryResource;
                            }

                            @Override
                            protected boolean getConfiguredHeaderEnabled() {
                                return false;
                            }

                            @Override
                            protected boolean getConfiguredHeaderMenusEnabled() {
                                return false;
                            }

                            public AttachmentsTableField.Table.FormatColumn getFormatColumn() {
                                return getColumnSet().getColumnByClass(AttachmentsTableField.Table.FormatColumn.class);
                            }

                            public AttachmentsTableField.Table.SizeColumn getSizeColumn() {
                                return getColumnSet().getColumnByClass(AttachmentsTableField.Table.SizeColumn.class);
                            }

                            public AttachmentsTableField.Table.AttachmentColumn getAttachmentColumn() {
                                return getColumnSet().getColumnByClass(AttachmentsTableField.Table.AttachmentColumn.class);
                            }

                            public AttachmentsTableField.Table.BinaryResourceColumn getBinaryResourceColumn() {
                                return getColumnSet().getColumnByClass(AttachmentsTableField.Table.BinaryResourceColumn.class);
                            }

                            public AttachmentsTableField.Table.NameColumn getNameColumn() {
                                return getColumnSet().getColumnByClass(AttachmentsTableField.Table.NameColumn.class);
                            }

                            public AttachmentsTableField.Table.AttachmentIdColumn getAttachmentIdColumn() {
                                return getColumnSet().getColumnByClass(AttachmentsTableField.Table.AttachmentIdColumn.class);
                            }

                            @Order(1000)
                            public class AddMenu extends AbstractAddMenu {

                                @Override
                                protected void execAction() {
                                    FileChooser chooser = new FileChooser(true);
                                    List<BinaryResource> items = chooser.startChooser();
                                    if (!CollectionUtility.isEmpty(items)) {
                                        for (BinaryResource attachment : items) {
                                            ITableRow row = createRow();
                                            getAttachmentColumn().setValue(row, attachment.getContent());
                                            getBinaryResourceColumn().setValue(row, attachment);
                                            getNameColumn().setValue(row, attachment.getFilename());
                                            getFormatColumn().setValue(row, attachment.getContentType());
                                            getSizeColumn().setValue(row, attachment.getContentLength());
                                            addRow(row, true);
                                        }
                                    }
                                }
                            }

                            @Order(2000)
                            public class DeleteMenu extends AbstractDeleteMenu {

                                @Override
                                protected void execAction() {
                                    BEANS.get(IAttachmentService.class).deleteAttachments(getAttachmentIdColumn().getSelectedValues());

                                    NotificationHelper.showDeleteSuccessNotification();

                                    renderForm();
                                }
                            }

                            @Order(3000)
                            public class ViewMenu extends AbstractOpenMenu {

                                @Override
                                protected void execAction() {
                                    IDesktop desktop = IDesktop.CURRENT.get();
                                    if (desktop != null) {
                                        BinaryResource binaryResource = findBinaryResourceToManage();

                                        if (binaryResource != null) {
                                            desktop.openUri(binaryResource, OpenUriAction.OPEN);
                                        }

                                    }
                                }

                            }

                            @Order(3100)
                            public class DownloadMenu extends AbstractDownloadMenu {

                                @Override
                                protected void execAction() {
                                    IDesktop desktop = IDesktop.CURRENT.get();
                                    if (desktop != null) {
                                        BinaryResource binaryResource = findBinaryResourceToManage();

                                        if (binaryResource != null) {
                                            desktop.openUri(binaryResource, OpenUriAction.DOWNLOAD);
                                        }

                                    }
                                }
                            }

                            @Order(1000)
                            public class AttachmentIdColumn extends AbstractIDColumn {

                            }

                            @Order(1250)
                            public class BinaryResourceColumn extends AbstractObjectColumn {
                                @Override
                                protected boolean getConfiguredDisplayable() {
                                    return false;
                                }
                            }

                            @Order(1500)
                            public class AttachmentColumn extends AbstractObjectColumn {
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
                                    return 150;
                                }
                            }

                            @Order(3000)
                            public class FormatColumn extends AbstractStringColumn {
                                @Override
                                protected String getConfiguredHeaderText() {
                                    return TEXTS.get("Format");
                                }

                                @Override
                                protected int getConfiguredWidth() {
                                    return 70;
                                }

                            }

                            @Order(4000)
                            public class SizeColumn extends AbstractIntegerColumn {
                                @Override
                                protected String getConfiguredHeaderText() {
                                    return TEXTS.get("Size");
                                }

                                @Override
                                protected int getConfiguredWidth() {
                                    return 100;
                                }

                                @Override
                                protected void execDecorateCell(Cell cell, ITableRow row) {
                                    cell.setText(FileUtils.byteCountToDisplaySize(getValue(row)));
                                }
                            }
                        }
                    }
                }

                @Order(3000)
                public class CommentsBox extends AbstractGroupBox {

                    private List<BinaryResource> commentAttachments = new ArrayList<>();

                    public List<BinaryResource> getCommentAttachments() {
                        return commentAttachments;
                    }

                    public void setCommentAttachments(List<BinaryResource> commentAttachments) {
                        this.commentAttachments = commentAttachments;
                    }

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Comments");
                    }

                    @Override
                    protected boolean getConfiguredExpandable() {
                        return true;
                    }

                    @Override
                    protected String getConfiguredMenuBarPosition() {
                        return MENU_BAR_POSITION_TITLE;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 1;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Order(-1000)
                    public class ShowSystemTasksMenu extends AbstractMenu {

                        @Override
                        protected boolean getConfiguredToggleAction() {
                            return true;
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Info;
                        }

                        @Override
                        protected String getConfiguredTooltipText() {
                            return TEXTS.get("ShowDetailsOfLog");
                        }

                        @Override
                        protected byte getConfiguredHorizontalAlignment() {
                            return 1;
                        }

                        @Override
                        protected void execSelectionChanged(boolean selection) {
                            super.execSelectionChanged(selection);
                            fetchActivityLogs();
                        }

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(0)
                    public class AddCommentAttachmentMenu extends org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu {
                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Paperclip;
                        }

                        @Override
                        protected byte getConfiguredHorizontalAlignment() {
                            return 1;
                        }

                        @Override
                        protected void execAction() {
                            FileChooser chooser = new FileChooser(true);

                            if (!getCommentAttachments().isEmpty()) {
                                for (BinaryResource file : getCommentAttachments()) {
                                    chooser.getFiles().add(file);

                                }
                            }

                            chooser.setFiles(getCommentAttachments());

                            List<BinaryResource> files = chooser.startChooser();

                            if (files.isEmpty()) {
                                NotificationHelper.showNotification("Nisu odabrane datoteke");
                                return;
                            }

                            for (BinaryResource file : files) {
                                getCommentAttachments().add(file);
                            }

                            String comment = getCommentField().getValue();
                            if (StringUtility.isNullOrEmpty(comment)) {
                                getCommentField().setValue("Dodani dokumenti");
                            }

                            AddCommentAttachmentMenu menu = MenuUtility.getMenuByClass(getCommentsBox(), AddCommentAttachmentMenu.class);
                            menu.setText("(" + getCommentAttachments().size() + ") ");
                        }
                    }

                    @Order(1000)
                    public class AddCommentMenu extends AbstractAddMenu {
                        @Override
                        protected String getConfiguredText() {
                            return "Dodaj komentar";
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Note;
                        }

                        @Override
                        protected void execAction() {
                            if (StringUtility.isNullOrEmpty(getCommentField().getValue())) {
                                NotificationHelper.showErrorNotification(TEXTS.get("CommentIsEmpty"));
                                return;
                            }

                            BEANS.get(ITaskViewService.class).addComment(getTaskId(), getCommentField().getValue(), getCommentAttachments());
                            getCommentField().setValue(null);

                            getCommentAttachments().clear();
                            AddCommentAttachmentMenu menu = MenuUtility.getMenuByClass(getCommentsBox(), AddCommentAttachmentMenu.class);
                            menu.setText(null);

                            fetchActivityLogs();
                        }
                    }

                    @Order(1000)
                    public class CommentField extends AbstractTextAreaField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("AddComment");
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return 0;
                        }

                        @Override
                        protected void execChangedDisplayText() {
                            super.execChangedDisplayText();

                            IMenu addCommentMenu = MenuUtility.getMenuByClass(getCommentsBox(), AddCommentMenu.class);
                            IMenu addCommentAttachment = MenuUtility.getMenuByClass(getCommentsBox(), AddCommentAttachmentMenu.class);

                            addCommentMenu.setEnabled(!StringUtility.isNullOrEmpty(getDisplayText()));
                            addCommentAttachment.setEnabled(!StringUtility.isNullOrEmpty(getDisplayText()));
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
                            return 7;
                        }

                        @ClassId("938d486d-5c5b-471a-bc19-9dda44c9239e")
                        public class Table extends AbstractTable {

                            @Override
                            public void doAppLinkAction(String ref) {
                                super.doAppLinkAction(ref);

                                if (ref.startsWith("OpenAttachment_")) {
                                    Integer attachmentId = Integer.valueOf(ref.substring("OpenAttachment_".length()));
                                    BinaryResource attachment = BEANS.get(IAttachmentService.class).download(attachmentId);

                                    ClientSession.get().getDesktop().openUri(attachment);
                                }
                            }

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

                            @Order(0)
                            public class EditMenu extends AbstractEditMenu {

                                @Override
                                protected void execAction() {
                                    ICell iCell = getSelectedRow().getCell(getActivityLogColumn());
                                    Cell cell = (Cell) iCell;

                                    cell.setEditable(isMyComment(getSelectedRow()));
                                }
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
                            public class ActivityLogColumn extends AbstractColumn<TaskActivityLog> {

                                @Override
                                protected boolean getConfiguredHtmlEnabled() {
                                    return true;
                                }

                                @Override
                                protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                                    super.execCompleteEdit(row, editingField);

                                    BEANS.get(ITaskViewService.class).updateActivityLog(getActivityLogIdColumn().getValue(row), getDisplayText(row));
                                }

                                @Override
                                protected void execDecorateCell(Cell cell, ITableRow row) {
                                    super.execDecorateCell(cell, row);

                                    TaskActivityLog activityLog = getActivityLogColumn().getValue(row);
                                    boolean hasAttachments = !activityLog.getAttachments().isEmpty();

                                    String comment = activityLog.getContent();
                                    String createdBy = getCreatedByColumn().getValue(row);
                                    String createdAt = new PrettyTime().format(getCreatedAtColumn().getValue(row));
                                    IHtmlElement attachmentIcon = hasAttachments ? HTML.icon(FontIcons.Paperclip) : HTML.span("");

                                    List<IHtmlListElement> attachmentHtmlElements = getHtmlListElementsAttachments(activityLog);

                                    IHtmlContent content = HTML.fragment(
                                            HTML.span(attachmentIcon, " ", comment), HTML.ul(attachmentHtmlElements).style("margin:0px;"),
                                            HTML.br(),
                                            HTML.italic(createdBy, ", ", createdAt).style("margin-top:5px;margin-bottom:0px; color:#3a3a3a;font-size:10px;")
                                    );

                                    cell.setText(content.toHtml());
                                }

                                private List<IHtmlListElement> getHtmlListElementsAttachments(TaskActivityLog activityLog) {
                                    List<IHtmlListElement> attachmentHtmlElements = CollectionUtility.emptyArrayList();
                                    for (Attachment attachment : activityLog.getAttachments()) {

                                        IHtmlListElement element = new HtmlListElement(attachment.getName());
                                        element.appLink("OpenAttachment_" + attachment.getId());
                                        attachmentHtmlElements.add(element);

                                    }
                                    return attachmentHtmlElements;
                                }
                            }
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
