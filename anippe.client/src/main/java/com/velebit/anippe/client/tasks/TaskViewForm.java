package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.attachments.AbstractAttachmentsBox;
import com.velebit.anippe.client.attachments.AbstractAttachmentsBox.AttachmentsTableField.Table.AddMenu;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.*;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.ActionsMenu.ArchiveMenu;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.CommentsBox.ShowSystemTasksMenu;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.InformationsBox.StartDateLabelField;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox.DetailsBox.InformationsBox.StatusLabelField;
import com.velebit.anippe.shared.PriorityEnum;
import com.velebit.anippe.shared.RelatedEnum;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.*;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
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
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FormData(value = TaskViewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskViewForm extends AbstractForm {

    private Integer taskId;

    private Integer activeTimerId;
    private Task task;
    private boolean followingTask;

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

    public GroupBox.DetailsBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DetailsBox.DescriptionField.class);
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

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();
    }

    public GroupBox.DetailsBox.InformationsBox getInformationsBox() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.class);
    }

    public GroupBox.DetailsBox.InformationsBox.PriorityLabelField getPriorityLabelField() {
        return getFieldByClass(GroupBox.DetailsBox.InformationsBox.PriorityLabelField.class);
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
        MenuUtility.getMenuByClass(getGroupBox(), ToggleTimerMenu.class).renderTimerMenu();

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
        getCreatedByLabelField().setValue(getTask().getCreator().getFullName());
        getStatusLabelField().setValue(TaskStatusEnum.fromValue(getTask().getStatusId()).getName());
        getStartDateLabelField().setValue(DateUtility.formatDate(getTask().getStartAt()));
        getDueDateLabelField().setValue(DateUtility.formatDate(getTask().getDeadlineAt()));
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

        List<ActivityLogTableRowData> rows = BEANS.get(ITaskViewService.class).fetchComments(getTaskId(), withSystemLog);
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

               /* @Override
                protected String getConfiguredCssClass() {
                    return "green-menu";
                }

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }*/

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Clock;
                }

                private void renderTimerMenu() {
                    setCssClass(getActiveTimerId() != null ? "red-menu" : getConfiguredCssClass());
                }

                @Override
                protected void execAction() {
                    super.execAction();

                    setActiveTimerId(BEANS.get(ITaskViewService.class).toggleTimer(getTaskId(), getActiveTimerId()));

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
                    return null; //TEXTS.get("SelectParticipants");
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
                    /*AbstractFormPopup<CreateTaskCheckListForm> popup = getCreateTaskCheckListFormAbstractFormPopup();
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
                    popup.open();*/

                    CreateTaskCheckListForm form = new CreateTaskCheckListForm();
                    form.setTaskId(getTaskId());
                    form.startNew();
                    form.waitFor();
                    if (form.isFormStored()) {
                        NotificationHelper.showSaveSuccessNotification();

                        renderForm();
                    }
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

                    popup.ensureFormStarted();
                    return popup;
                }
            }

            @Order(700)
            public class AddAttachmentMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return null; //TEXTS.get("AddAttachment");
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
                    protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                        return super.getConfiguredBodyLayoutConfig().withVGap(0);
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

                    @Order(0)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class StatusLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Status");
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
                            return TEXTS.get("Priority");
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

                    @Order(1500)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class StartDateLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("StartDate");
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
                    public class DueDateLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("DueDate");
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
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class CreatedByLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("CreatedBy");
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
                        protected void execInitField() {
                            setValue("Amel Jakupović");
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
                            return TEXTS.get("AssignedUser");
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
                        protected void execInitField() {
                            setValue("Amel Jakupović, Luka Čavić");
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                }

                @Order(1000)
                public class DescriptionField extends AbstractLabelField {

                    @Override
                    protected boolean getConfiguredEnabled() {
                        return false;
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
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Description");
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
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
                public class AttachmentsBox extends AbstractAttachmentsBox {
                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        getAttachmentsTableField().getTable().getMenuByClass(AddMenu.class).setVisible(false);
                        getAttachmentsTableField().setGridDataHints(getAttachmentsTableField().getGridData().withH(3));
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
                }

                @Order(3000)
                public class CommentsBox extends AbstractGroupBox {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Comments");
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
                            List<BinaryResource> chooser = new FileChooser(true).startChooser();
                            if (!CollectionUtility.isEmpty(chooser)) return;
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

                            BEANS.get(ITaskViewService.class).addComment(getTaskId(), getCommentField().getValue());
                            getCommentField().setValue(null);

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
