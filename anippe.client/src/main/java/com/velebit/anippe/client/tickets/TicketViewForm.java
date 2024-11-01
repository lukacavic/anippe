package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.fields.AbstractEmailField;
import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.reminders.AbstractRemindersGroupBox;
import com.velebit.anippe.client.tasks.AbstractTasksGroupBox;
import com.velebit.anippe.client.tasks.AbstractTasksGroupBox.TasksTableField;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.FollowMenu;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.HeaderBox;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.HeaderBox.ClientLabelField;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.HeaderBox.TicketTitleLabelField;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.MainTabBox;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.MainTabBox.*;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.MainTabBox.MainInformationsBox.*;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.MainTabBox.ReplyBox.*;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.LeftBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.AddReplyButton;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.RepliesBox.RepliesSplitBox;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.SplitBox.RepliesBox.RepliesSplitBox.PreviewReplyField;
import com.velebit.anippe.client.tickets.TicketViewForm.MainBox.StatusMenu.StatusField;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.ColorConstants.Orange;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.constants.Constants.TicketStatus;
import com.velebit.anippe.shared.contacts.ContactLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.knowledgebase.IArticleService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeArticleLookupCall;
import com.velebit.anippe.shared.projects.ProjectLookupCall;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import com.velebit.anippe.shared.tickets.*;
import com.velebit.anippe.shared.tickets.TicketViewFormData.NotesTable.NotesTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.OtherTicketsTable.OtherTicketsTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.RepliesTable.RepliesTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.ReplyAttachmentsTable.ReplyAttachmentsTableRowData;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.MouseButton;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.form.fields.AbstractFormFieldMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.HeaderCell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.*;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
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
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.Set;

@FormData(value = TicketViewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TicketViewForm extends AbstractForm {

    private Integer ticketId;
    private Integer projectId;

    @Override
    public Object computeExclusiveKey() {
        return getTicketId();
    }

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @FormData
    public Integer getTicketId() {
        return ticketId;
    }

    @FormData
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Info;
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        getRemindersBox().setRelatedType(Related.TICKET);
        getRemindersBox().setRelatedId(getTicketId());
        getRemindersBox().fetchReminders();
        fetchNotes();
        setLabels();
    }

    public void setLabels() {
        int tasksCount = getTasksTableField().getTable().getRowCount();
        getTasksBox().setLabel(getTasksBox().getConfiguredLabel() + " (" + tasksCount + ")");

        int otherTicketsCount = getOtherTicketsTableField().getTable().getRowCount();
        getOtherTicketsBox().setLabel(getOtherTicketsBox().getConfiguredLabel() + " (" + otherTicketsCount + ")");

        int relatedTicketsCount = getOtherTicketsTableField().getTable().getRowCount();
        getOtherTicketsBox().setLabel(getOtherTicketsBox().getConfiguredLabel() + " (" + relatedTicketsCount + ")");
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Ticket");
    }

    public TicketTitleLabelField getTicketTitleLabelField() {
        return getFieldByClass(TicketTitleLabelField.class);
    }

    public SendOptionsSequenceBox.AddAttachmentsButton getAddAttachmentsButton() {
        return getFieldByClass(SendOptionsSequenceBox.AddAttachmentsButton.class);
    }

    public AssignedToField getAssignedToField() {
        return getFieldByClass(AssignedToField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public AddReplyButton getAddReplyButton() {
        return getFieldByClass(AddReplyButton.class);
    }

    public ClientLabelField getClientLabelField() {
        return getFieldByClass(ClientLabelField.class);
    }

    public SendOptionsSequenceBox.AssignToMeField getAssignToMeField() {
        return getFieldByClass(SendOptionsSequenceBox.AssignToMeField.class);
    }

    public HeaderBox.AssignedUserLabelField getAssignedUserLabelField() {
        return getFieldByClass(HeaderBox.AssignedUserLabelField.class);
    }

    public AttachmentsTableField getAttachmentsTableField() {
        return getFieldByClass(AttachmentsTableField.class);
    }

    public SendOptionsSequenceBox.CCField getCCField() {
        return getFieldByClass(SendOptionsSequenceBox.CCField.class);
    }

    public SendOptionsSequenceBox.ChangeStatusField getChangeStatusField() {
        return getFieldByClass(SendOptionsSequenceBox.ChangeStatusField.class);
    }


    public SendOptionsSequenceBox.CloseAfterReplyField getCloseAfterReplyField() {
        return getFieldByClass(SendOptionsSequenceBox.CloseAfterReplyField.class);
    }

    public CodeField getCodeField() {
        return getFieldByClass(CodeField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    public ContactField getContactField() {
        return getFieldByClass(ContactField.class);
    }

    public DepartmentField getDepartmentField() {
        return getFieldByClass(DepartmentField.class);
    }

    public HeaderBox getHeaderBox() {
        return getFieldByClass(HeaderBox.class);
    }

    public KnowledgeBaseArticleField getKnowledgeBaseArticleField() {
        return getFieldByClass(KnowledgeBaseArticleField.class);
    }

    public MainBox.SplitBox.LeftBox getLeftBox() {
        return getFieldByClass(MainBox.SplitBox.LeftBox.class);
    }

    public MainInformationsBox getMainInformationsBox() {
        return getFieldByClass(MainInformationsBox.class);
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
        return false;
    }

    public MainTabBox getMainTabBox() {
        return getFieldByClass(MainTabBox.class);
    }

    public NotesTableField getNotesTableField() {
        return getFieldByClass(NotesTableField.class);
    }

    public OtherTicketsBox getOtherTicketsBox() {
        return getFieldByClass(OtherTicketsBox.class);
    }

    public OtherTicketsBox.OtherTicketsTableField getOtherTicketsTableField() {
        return getFieldByClass(OtherTicketsBox.OtherTicketsTableField.class);
    }

    public PredefinedReplyField getPredefinedReplyField() {
        return getFieldByClass(PredefinedReplyField.class);
    }

    public PriorityField getPriorityField() {
        return getFieldByClass(PriorityField.class);
    }

    public HeaderBox.PriorityLabelField getPriorityLabelField() {
        return getFieldByClass(HeaderBox.PriorityLabelField.class);
    }

    public ProjectField getProjectField() {
        return getFieldByClass(ProjectField.class);
    }

    public RemindersBox getRemindersBox() {
        return getFieldByClass(RemindersBox.class);
    }

    public MainBox.SplitBox.RepliesBox getRepliesBox() {
        return getFieldByClass(MainBox.SplitBox.RepliesBox.class);
    }

    public RepliesSplitBox.RepliesTableField getRepliesTableField() {
        return getFieldByClass(RepliesSplitBox.RepliesTableField.class);
    }

    public ReplyBox getReplyBox() {
        return getFieldByClass(ReplyBox.class);
    }

    public ReplyField getReplyField() {
        return getFieldByClass(ReplyField.class);
    }

    public StatusField getStatusField() {
        return getFieldByClass(StatusField.class);
    }

    public SendOptionsSequenceBox getSendOptionsSequenceBox() {
        return getFieldByClass(SendOptionsSequenceBox.class);
    }

    public HeaderBox.StatusLabelField getStatusLabelField() {
        return getFieldByClass(HeaderBox.StatusLabelField.class);
    }

    public SubjectField getSubjectField() {
        return getFieldByClass(SubjectField.class);
    }

    public PreviewReplyField getPreviewReplyField() {
        return getFieldByClass(PreviewReplyField.class);
    }

    public RepliesSplitBox getRepliesSplitBox() {
        return getFieldByClass(RepliesSplitBox.class);
    }

    public RepliesSplitBox.ReplyAttachmentsTableField getReplyAttachmentsTableField() {
        return getFieldByClass(RepliesSplitBox.ReplyAttachmentsTableField.class);
    }

    public TasksBox getTasksBox() {
        return getFieldByClass(TasksBox.class);
    }

    public TasksTableField getTasksTableField() {
        return getFieldByClass(TasksTableField.class);
    }

    private void fetchOtherReplies() {
        List<OtherTicketsTableRowData> rows = BEANS.get(ITicketViewService.class).fetchOtherTicketRows(getContactField().getValue(), getTicketId());
        getOtherTicketsTableField().getTable().importFromTableRowBeanData(rows, OtherTicketsTableRowData.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public void fetchReplies() {
        List<RepliesTableRowData> rows = BEANS.get(ITicketViewService.class).fetchReplies(getTicketId());
        getRepliesTableField().getTable().importFromTableRowBeanData(rows, RepliesTableRowData.class);
    }

    public void fetchNotes() {
        List<NotesTableRowData> rows = BEANS.get(ITicketViewService.class).fetchNotes(getTicketId());
        getNotesTableField().getTable().importFromTableRowBeanData(rows, NotesTableRowData.class);
    }

    public void fetchTasks() {
        List<TasksTableRowData> rows = BEANS.get(ITicketViewService.class).fetchTasks(getTicketId());
        getTasksTableField().getTable().importFromTableRowBeanData(rows, TasksTableRowData.class);
    }

    public void renderFollowingIcon() {
        String cssClass = "TicketFormFollowingIconActive";
        boolean isFollowing = BEANS.get(ITicketViewService.class).isUserFollowerOfTicket(getTicketId());
        MenuUtility.getMenuByClass(getMainBox(), FollowMenu.class).setCssClass(isFollowing ? cssClass : null);
    }

    public void renderHeaderLabels() {
        getAssignedUserLabelField().renderHtml(getAssignedToField().getDisplayText());
        getPriorityLabelField().renderHtml(getPriorityField().getDisplayText());
        getClientLabelField().renderHtml(getContactField().getDisplayText());
        getStatusLabelField().renderHtml(getStatusField().getDisplayText());
    }

    public void renderTicketClosedNotification() {
        boolean isClosed = getStatusField().getValue() != null && getStatusField().getValue().equals(TicketStatus.CLOSED);
        getReplyBox().setNotification(isClosed ? new Notification(new Status(TEXTS.get("TicketIsClosed"), IStatus.OK, FontIcons.Check)) : null);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
            return 5;
        }

        @Order(-1000)
        public class SplitBox extends AbstractSplitBox {
            @Override
            protected double getConfiguredSplitterPosition() {
                return 0.65;
            }

            @Override
            protected boolean getConfiguredSplitterEnabled() {
                return false;
            }

            @Override
            protected String getConfiguredSplitterPositionType() {
                return super.getConfiguredSplitterPositionType();
            }

            @Override
            protected boolean getConfiguredSplitHorizontal() {
                return true;
            }

            @Order(-1000)
            public class LeftBox extends AbstractGroupBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                public boolean isBorderVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 3;
                }

                @Order(-1)
                public class HeaderBox extends AbstractGroupBox {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                        return super.getConfiguredBodyLayoutConfig().withVGap(0);
                    }

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 4;
                    }

                    @Override
                    public boolean isBorderVisible() {
                        return false;
                    }

                    @Order(900)
                    public class TicketTitleLabelField extends AbstractLabelField {

                        private String contentToRender;

                        public String getContentToRender() {
                            return contentToRender;
                        }

                        public void setContentToRender(String contentToRender) {
                            this.contentToRender = contentToRender;

                            renderContent();
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 4;
                        }

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Subject");
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        private void renderContent() {
                            IHtmlContent content = HTML.bold(contentToRender).style("font-size:20px;color:#234d74;");
                            setValue(content.toHtml());
                        }
                    }

                    @Order(1000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class ClientLabelField extends AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Client");
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        @Override
                        protected void execInitField() {
                            addCssClass(CssClasses.TOP_PADDING_INVISIBLE);
                            addCssClass(CssClasses.BOTTOM_PADDING_INVISIBLE);

                        }

                        public void renderHtml(String displayText) {
                            setValue(HTML.fragment(HTML.bold(displayText).style("font-size:14px;").style("padding-top:0px;")).toHtml());
                        }
                    }


                    @Order(2000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class StatusLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Status");
                        }


                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        @Override
                        protected void execInitField() {
                            addCssClass(CssClasses.TOP_PADDING_INVISIBLE);
                            addCssClass(CssClasses.BOTTOM_PADDING_INVISIBLE);
                        }

                        public void renderHtml(String displayText) {
                            setValue(HTML.fragment(HTML.bold(displayText)
                                    .style("background-color: #f25757;padding: 5px;color: white;border-radius: 5px;font-size: 10px;").style("padding-top:0px;")).toHtml());
                        }
                    }

                    @Order(3000)
                    @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                    public class AssignedUserLabelField extends AbstractLabelField {

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Assigned");
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        public void renderHtml(String value) {
                            setValue(HTML.fragment(HTML.bold(value).style("font-size:14px;").style("padding-top:0px;")).toHtml());
                        }

                        @Override
                        protected void execInitField() {
                            addCssClass(CssClasses.TOP_PADDING_INVISIBLE);
                            addCssClass(CssClasses.BOTTOM_PADDING_INVISIBLE);

                        }
                    }

                    @Order(4000)
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
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }

                        @Override
                        protected void execInitField() {
                            addCssClass(CssClasses.TOP_PADDING_INVISIBLE);
                            addCssClass(CssClasses.BOTTOM_PADDING_INVISIBLE);

                        }

                        public void renderHtml(String value) {
                            setValue(HTML.fragment(HTML.bold(value).style("font-size:14px;").style("padding-top:0px;")).toHtml());
                        }
                    }
                }

                @Order(0)
                public class MainTabBox extends AbstractTabBox {

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 3;
                    }

                    @Order(0)
                    public class ReplyBox extends AbstractGroupBox {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Reply");
                        }

                        @Override
                        protected int getConfiguredGridColumnCount() {
                            return 2;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Order(0)
                        public class AddNoteMenu extends AbstractMenu {
                            @Override
                            protected String getConfiguredText() {
                                return TEXTS.get("AddNote");
                            }

                            @Override
                            protected byte getConfiguredHorizontalAlignment() {
                                return 1;
                            }

                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.Note;
                            }

                            @Override
                            protected void execAction() {
                                AbstractFormPopup<QuickNoteForm> popup = new AbstractFormPopup<QuickNoteForm>() {
                                    @Override
                                    protected QuickNoteForm createForm() {
                                        QuickNoteForm form = new QuickNoteForm();
                                        form.setTicketId(getTicketId());
                                        form.getCancelButton().setVisible(false);

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

                        @Order(1000)
                        public class TogglePrivateNotesMenu extends AbstractMenu {

                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.List;
                            }

                            @Override
                            protected boolean setSelectedInternal(boolean b) {
                                return true;
                            }

                            @Override
                            protected int getConfiguredActionStyle() {
                                return ACTION_STYLE_BUTTON;
                            }

                            @Override
                            protected String getConfiguredTooltipText() {
                                return TEXTS.get("ShowHidePrivateNotes");
                            }

                            @Override
                            protected boolean getConfiguredToggleAction() {
                                return true;
                            }

                            @Override
                            protected byte getConfiguredHorizontalAlignment() {
                                return 1;
                            }

                            @Override
                            protected void execSelectionChanged(boolean selection) {
                                getNotesTableField().setVisible(selection);
                            }

                        }

                        @Order(0)
                        public class NotesTableField extends AbstractTableField<NotesTableField.Table> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Notes");
                            }

                            @Override
                            public boolean isPreventInitialFocus() {
                                return true;
                            }

                            @Override
                            public boolean isLabelVisible() {
                                return false;
                            }

                            @Override
                            protected boolean getConfiguredVisible() {
                                return false;
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
                            protected int getConfiguredGridH() {
                                return 3;
                            }

                            @Override
                            protected boolean getConfiguredStatusVisible() {
                                return false;
                            }

                            @ClassId("aa24534e-4e2a-4e88-b3e1-58d0b1fe8cc9")
                            public class Table extends AbstractTable {

                                @Override
                                protected boolean getConfiguredHeaderVisible() {
                                    return false;
                                }

                                public NoteColumn getNoteColumn() {
                                    return getColumnSet().getColumnByClass(NoteColumn.class);
                                }

                                public UserColumn getUserColumn() {
                                    return getColumnSet().getColumnByClass(UserColumn.class);
                                }

                                public NoteIdColumn getNoteIdColumn() {
                                    return getColumnSet().getColumnByClass(NoteIdColumn.class);
                                }

                                public CreatedAtColumn getCreatedAtColumn() {
                                    return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                                }

                                public UserIdColumn getUserIdColumn() {
                                    return getColumnSet().getColumnByClass(UserIdColumn.class);
                                }

                                @Override
                                protected boolean getConfiguredAutoResizeColumns() {
                                    return true;
                                }

                                @Order(1000)
                                public class EditMenu extends AbstractEditMenu {

                                    @Override
                                    protected void execAction() {

                                    }
                                }

                                @Order(2000)
                                public class DeleteMenu extends AbstractDeleteMenu {

                                    @Override
                                    protected void execAction() {
                                        if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                            BEANS.get(ITicketViewService.class).deleteNote(getNoteIdColumn().getSelectedValue());

                                            NotificationHelper.showDeleteSuccessNotification();

                                            fetchNotes();
                                        }
                                    }
                                }

                                @Order(-1000)
                                public class NoteIdColumn extends AbstractIDColumn {

                                }

                                @Order(1000)
                                public class UserIdColumn extends AbstractIDColumn {

                                }

                                @Order(0)
                                public class UserColumn extends AbstractStringColumn {
                                    @Override
                                    protected boolean getConfiguredDisplayable() {
                                        return false;
                                    }
                                }

                                @Order(500)
                                public class CreatedAtColumn extends AbstractDateTimeColumn {
                                    @Override
                                    protected boolean getConfiguredDisplayable() {
                                        return false;
                                    }
                                }

                                @Order(1000)
                                public class NoteColumn extends AbstractStringColumn {
                                    @Override
                                    protected void execDecorateHeaderCell(HeaderCell cell) {
                                        super.execDecorateHeaderCell(cell);
                                    }

                                    @Override
                                    protected boolean getConfiguredHtmlEnabled() {
                                        return true;
                                    }

                                    @Override
                                    protected String getConfiguredCssClass() {
                                        return "cell-no-padding";
                                    }

                                    @Override
                                    protected void execDecorateCell(Cell cell, ITableRow row) {
                                        super.execDecorateCell(cell, row);

                                        String user = getUserColumn().getValue(row);
                                        String createdAt = DateUtility.formatDateTime(getCreatedAtColumn().getValue(row));
                                        String note = getValue(row);

                                        boolean isMyNote = getUserIdColumn().getValue(row).equals(ClientSession.get().getCurrentUser().getId());

                                        IHtmlContent title = HTML.div(
                                                HTML.bold(user).style("color:#b45f0e;"),
                                                HTML.br(),
                                                HTML.div(createdAt).style("color:#bb7e43;"),
                                                HTML.br(),
                                                HTML.div(note).style("color:#444444;")
                                        ).style(isMyNote ? "background-color:#f8f8b4;padding:10px;" : "padding:10px;");

                                        cell.setText(title.toHtml());
                                    }

                                    @Override
                                    protected int getConfiguredWidth() {
                                        return 100;
                                    }
                                }
                            }
                        }

                        @Order(1000)
                        public class PredefinedReplyField extends AbstractSmartField<Long> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("PredefinedReply");
                            }

                            @Override
                            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                                return PredefinedReplyLookupCall.class;
                            }

                            @Override
                            protected void execPrepareLookup(ILookupCall<Long> call) {
                                super.execPrepareLookup(call);

                                PredefinedReplyLookupCall c = (PredefinedReplyLookupCall) call;
                                c.setProjectId(getProjectId());
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
                                return LABEL_POSITION_ON_FIELD;
                            }

                            @Override
                            protected void execChangedValue() {
                                super.execChangedValue();
                                Long predefinedReplyId = getValue();

                                if (predefinedReplyId == null) return;

                                String content = BEANS.get(ITicketViewService.class).fetchPredefinedReplyContent(predefinedReplyId);

                                getReplyField().setValue(content);

                                ModelJobs.schedule(() -> setValue(null), ModelJobs.newInput(ClientRunContexts.copyCurrent()));
                            }
                        }

                        @Order(2000)
                        public class KnowledgeBaseArticleField extends AbstractSmartField<Long> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("KnowledgeBaseArticle");
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
                            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                                return KnowledgeArticleLookupCall.class;
                            }

                            @Override
                            protected void execPrepareLookup(ILookupCall<Long> call) {
                                super.execPrepareLookup(call);

                                KnowledgeArticleLookupCall c = (KnowledgeArticleLookupCall) call;
                                if (getProjectId() != null) {
                                    c.setProjectId(getProjectId());
                                }
                            }

                            @Override
                            protected void execChangedValue() {
                                super.execChangedValue();

                                if (getValue() != null) {
                                    String content = BEANS.get(IArticleService.class).fetchContent(getValue());

                                    getReplyField().setValue(content);

                                    ModelJobs.schedule(() -> setValue(null), ModelJobs.newInput(ClientRunContexts.copyCurrent()));

                                }
                            }

                            @Override
                            protected byte getConfiguredLabelPosition() {
                                return LABEL_POSITION_ON_FIELD;
                            }
                        }

                        @Order(3000)
                        public class ReplyField extends AbstractTextEditorField {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Reply");
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

                            @Override
                            protected int getConfiguredGridW() {
                                return 2;
                            }
                        }

                        @Order(3250)
                        public class AttachmentsTableField extends AbstractTableField<AttachmentsTableField.Table> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Attachments");
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
                            protected boolean getConfiguredVisible() {
                                return false;
                            }

                            @Override
                            protected boolean getConfiguredStatusVisible() {
                                return false;
                            }

                            @Override
                            protected int getConfiguredGridH() {
                                return 3;
                            }

                            @ClassId("cfa70168-40b8-4c33-acfe-18e528e900ff")
                            public class Table extends AbstractTable {

                                @Override
                                protected boolean getConfiguredAutoResizeColumns() {
                                    return true;
                                }

                                public BinaryResource findBinaryResourceToManage() {
                                    BinaryResource binaryResource = null;

                                    binaryResource = getBinaryResourceColumn().getSelectedValue();

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

                                public FormatColumn getFormatColumn() {
                                    return getColumnSet().getColumnByClass(FormatColumn.class);
                                }

                                public SizeColumn getSizeColumn() {
                                    return getColumnSet().getColumnByClass(SizeColumn.class);
                                }

                                public BinaryResourceColumn getBinaryResourceColumn() {
                                    return getColumnSet().getColumnByClass(BinaryResourceColumn.class);
                                }

                                public NameColumn getNameColumn() {
                                    return getColumnSet().getColumnByClass(NameColumn.class);
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
                                                getBinaryResourceColumn().setValue(row, attachment);
                                                getNameColumn().setValue(row, attachment.getFilename());
                                                getFormatColumn().setValue(row, attachment.getContentType());
                                                getSizeColumn().setValue(row, attachment.getContentLength());
                                                addRow(row, true);
                                            }
                                        }
                                    }
                                }

                                @Order(6000)
                                public class DeleteMenu extends AbstractDeleteMenu {

                                    @Override
                                    protected void execAction() {
                                        for (ITableRow row : getSelectedRows()) {
                                            row.setStatusDeleted();
                                            row.delete();
                                        }
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

                                @Order(1250)
                                public class BinaryResourceColumn extends AbstractColumn<BinaryResource> {
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

                        @Order(3500)
                        public class SendOptionsSequenceBox extends AbstractSequenceBox {
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
                            protected boolean getConfiguredAutoCheckFromTo() {
                                return false;
                            }

                            @Order(4000)
                            public class ChangeStatusField extends AbstractSmartField<Integer> {
                                @Override
                                protected String getConfiguredLabel() {
                                    return TEXTS.get("NewStatus");
                                }

                                @Override
                                protected void execInitField() {
                                    super.execInitField();

                                    setValue(TicketStatus.ANSWERED);
                                }

                                @Override
                                protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                                    return TicketStatusLookupCall.class;
                                }

                                @Override
                                protected boolean getConfiguredStatusVisible() {
                                    return false;
                                }


                            }

                            @Order(5000)
                            public class CCField extends AbstractEmailField {
                                @Override
                                protected String getConfiguredLabel() {
                                    return TEXTS.get("CC");
                                }

                                @Override
                                protected boolean getConfiguredStatusVisible() {
                                    return false;
                                }

                                @Override
                                protected int getConfiguredMaxLength() {
                                    return 128;
                                }
                            }

                            @Order(5500)
                            public class CloseAfterReplyField extends AbstractBooleanField {
                                @Override
                                protected String getConfiguredLabel() {
                                    return TEXTS.get("MyNlsKey");
                                }

                                @Override
                                protected void execInitField() {
                                    super.execInitField();

                                    setValue(true);
                                }

                                @Override
                                public int getLabelWidthInPixel() {
                                    return 120;
                                }

                                @Override
                                protected byte getConfiguredLabelPosition() {
                                    return LABEL_POSITION_ON_FIELD;
                                }
                            }

                            @Order(5750)
                            public class AssignToMeField extends AbstractBooleanField {
                                @Override
                                protected String getConfiguredLabel() {
                                    return TEXTS.get("AssignToMe");
                                }

                                @Override
                                public int getLabelWidthInPixel() {
                                    return 120;
                                }

                                @Override
                                protected byte getConfiguredLabelPosition() {
                                    return LABEL_POSITION_ON_FIELD;
                                }
                            }

                            @Order(6000)
                            public class AddAttachmentsButton extends AbstractButton {
                                @Override
                                public boolean isLabelVisible() {
                                    return false;
                                }

                                @Override
                                protected String getConfiguredIconId() {
                                    return FontIcons.Paperclip;
                                }

                                @Override
                                protected int getConfiguredDisplayStyle() {
                                    return DISPLAY_STYLE_TOGGLE;
                                }

                                @Override
                                protected void execSelectionChanged(boolean selection) {
                                    super.execSelectionChanged(selection);

                                    setDefaultButton(selection);
                                    getAttachmentsTableField().setVisible(selection);
                                }

                                @Override
                                public boolean isProcessButton() {
                                    return false;
                                }

                                @Override
                                protected void execClickAction() {

                                }
                            }

                            @Order(10000)
                            public class AddReplyButton extends AbstractButton {
                                @Override
                                protected String getConfiguredLabel() {
                                    return TEXTS.get("AddReply");
                                }

                                @Override
                                protected String getConfiguredIconId() {
                                    return FontIcons.Email;
                                }

                                @Override
                                protected Boolean getConfiguredDefaultButton() {
                                    return true;
                                }

                                @Override
                                public boolean isProcessButton() {
                                    return false;
                                }

                                @Override
                                protected void execClickAction() {
                                    super.execClickAction();

                                    if (StringUtility.isNullOrEmpty(getReplyField().getValue())) {
                                        NotificationHelper.showErrorNotification(TEXTS.get("ReplyIsEmpty"));

                                        return;
                                    }

                                    validateForm();

                                    TicketViewFormData formData = new TicketViewFormData();
                                    exportFormData(formData);

                                    BEANS.get(ITicketViewService.class).addReply(formData);

                                    NotificationHelper.showSaveSuccessNotification();

                                    if (getCloseAfterReplyField().getValue()) {
                                        ModelJobs.schedule(() -> getForm().doClose(), ModelJobs.newInput(ClientRunContexts.copyCurrent()));
                                    } else {
                                        resetFormAfterReply();
                                    }

                                }

                                private void resetFormAfterReply() {
                                    getAttachmentsTableField().getTable().discardAllRows();
                                    getStatusField().setValue(getChangeStatusField().getValue());
                                    getCCField().setValue(null);
                                    getReplyField().setValue(null);

                                    fetchReplies();
                                }
                            }
                        }


                    }

                    @Order(1000)
                    public class MainInformationsBox extends AbstractGroupBox {

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("MainInformations");
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
                        public class SaveMenu extends AbstractMenu {
                            @Override
                            protected String getConfiguredText() {
                                return TEXTS.get("Save");
                            }

                            @Override
                            protected byte getConfiguredHorizontalAlignment() {
                                return 1;
                            }

                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.Check;
                            }

                            @Override
                            protected void execAction() {
                                TicketViewFormData formData = new TicketViewFormData();
                                exportFormData(formData);
                                BEANS.get(ITicketViewService.class).store(formData);

                                NotificationHelper.showSaveSuccessNotification();
                            }
                        }

                        @Order(0)
                        public class CodeField extends AbstractStringField {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Code");
                            }

                            @Override
                            public boolean isEnabled() {
                                return false;
                            }
                        }

                        @Order(1000)
                        public class SubjectField extends AbstractStringField {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Subject");
                            }

                            @Override
                            protected boolean getConfiguredMandatory() {
                                return true;
                            }

                            @Override
                            protected int getConfiguredMaxLength() {
                                return 128;
                            }

                            @Override
                            protected void execChangedValue() {
                                super.execChangedValue();

                                getTicketTitleLabelField().setContentToRender(getValue());
                            }
                        }

                        @Order(2000)
                        public class ContactField extends AbstractSmartField<Long> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Contact");
                            }

                            @Override
                            public boolean isEnabled() {
                                return false;
                            }

                            @Override
                            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                                return ContactLookupCall.class;
                            }

                            @Override
                            protected void execChangedValue() {
                                super.execChangedValue();

                                renderHeaderLabels();
                            }
                        }

                        @Order(3000)
                        public class AssignedToField extends AbstractSmartField<Long> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Assigned");
                            }

                            @Override
                            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                                return UserLookupCall.class;
                            }

                            @Override
                            protected void execPrepareLookup(ILookupCall<Long> call) {
                                super.execPrepareLookup(call);

                                UserLookupCall c = (UserLookupCall) call;
                                if (getProjectField().getValue() != null) {
                                    c.setProjectId(getProjectField().getValue().intValue());
                                }
                            }

                            @Override
                            protected void execChangedValue() {
                                super.execChangedValue();

                                renderHeaderLabels();
                            }
                        }

                        @Order(4000)
                        public class PriorityField extends AbstractSmartField<Integer> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Priority");
                            }

                            @Override
                            protected boolean getConfiguredMandatory() {
                                return true;
                            }

                            @Override
                            protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                                return PriorityLookupCall.class;
                            }

                            @Override
                            protected void execChangedValue() {
                                super.execChangedValue();

                                renderHeaderLabels();
                            }
                        }

                        @Order(5000)
                        public class ProjectField extends AbstractSmartField<Long> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Project");
                            }

                            @Override
                            public boolean isEnabled() {
                                return false;
                            }

                            @Override
                            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                                return ProjectLookupCall.class;
                            }
                        }

                        @Order(6000)
                        public class DepartmentField extends AbstractSmartField<Long> {
                            @Override
                            protected String getConfiguredLabel() {
                                return TEXTS.get("Department");
                            }

                            @Override
                            protected boolean getConfiguredMandatory() {
                                return true;
                            }

                            @Override
                            protected void execPrepareLookup(ILookupCall<Long> call) {
                                super.execPrepareLookup(call);

                                TicketDepartmentLookupCall c = (TicketDepartmentLookupCall) call;

                                if (getProjectField().getValue() != null) {
                                    c.setProjectId(getProjectField().getValue().intValue());
                                }

                            }

                            @Override
                            protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                                return TicketDepartmentLookupCall.class;
                            }
                        }
                    }

                    @Order(1500)
                    public class RemindersBox extends AbstractRemindersGroupBox {
                        @Override
                        public Integer getRelatedId() {
                            return TicketViewForm.this.getTicketId();
                        }

                        @Override
                        public String getSubLabel() {
                            return null;
                        }

                        @Override
                        protected String getConfiguredSubLabel() {
                            return TEXTS.get("LeadReminders");
                        }

                        @Override
                        public Integer getRelatedType() {
                            return Related.TICKET;
                        }
                    }

                    @Order(2000)
                    public class TasksBox extends AbstractTasksGroupBox {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Tasks");
                        }

                        @Override
                        public Integer getRelatedType() {
                            return Related.TICKET;
                        }

                        @Override
                        public Integer getRelatedId() {
                            return getTicketId();
                        }

                        @Override
                        protected void reloadTasks() {
                            fetchTasks();
                        }
                    }

                    @Order(2500)
                    public class OtherTicketsBox extends AbstractGroupBox {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("OtherTickets");
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Order(1000)
                        public class OtherTicketsTableField extends AbstractTableField<OtherTicketsTableField.Table> {
                            @Override
                            protected boolean getConfiguredStatusVisible() {
                                return false;
                            }

                            @Override
                            public boolean isLabelVisible(String dimension) {
                                return false;
                            }

                            @Override
                            protected int getConfiguredGridH() {
                                return 6;
                            }

                            @Override
                            public boolean isLabelVisible() {
                                return false;
                            }

                            @ClassId("9867065c-15c5-436c-a2ed-5323f9510715")
                            public class Table extends AbstractTicketsTable {

                                @Override
                                public void reloadData() {
                                    fetchOtherReplies();
                                }
                            }
                        }
                    }

                }

            }

            @Order(1000)
            public class RepliesBox extends AbstractGroupBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Order(0)
                public class RepliesSplitBox extends AbstractGroupBox {
                    @Override
                    public boolean isBorderVisible() {
                        return false;
                    }


                    @Order(1000)
                    public class RepliesTableField extends AbstractTableField<RepliesTableField.Table> {
                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_TOP;
                        }

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("TicketReplies");
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return 0;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 5;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @ClassId("a475f4ec-4cfe-43db-aa69-ce58828192e9")
                        public class Table extends AbstractTable {
                            @Override
                            protected boolean getConfiguredHeaderVisible() {
                                return false;
                            }

                            @Override
                            protected void execDecorateRow(ITableRow row) {
                                super.execDecorateRow(row);

                                row.setBackgroundColor(getUserIdColumn().getValue(row) != null ? Orange.Orange1 : null);
                            }

                            @Override
                            protected void execRowClick(ITableRow row, MouseButton mouseButton) {
                                super.execRowClick(row, mouseButton);

                                String reply = BEANS.get(ITicketViewService.class).findReplyById(getTicketReplyIdColumn().getValue(row));

                                //Load reply attachments
                                List<ReplyAttachmentsTableRowData> rows = BEANS.get(ITicketViewService.class).fetchReplyAttachments(getTicketReplyIdColumn().getValue(row));
                                getReplyAttachmentsTableField().getTable().importFromTableRowBeanData(rows, ReplyAttachmentsTableRowData.class);

                                getReplyAttachmentsTableField().setVisible(getHasAttachmentsColumn().getValue(row) != null);
                                getPreviewReplyField().setReplyId(getTicketReplyIdColumn().getValue(row));
                                getPreviewReplyField().setValue(reply);
                            }

                            @Override
                            protected boolean getConfiguredAutoResizeColumns() {
                                return true;
                            }

                            public HasAttachmentsColumn getHasAttachmentsColumn() {
                                return getColumnSet().getColumnByClass(HasAttachmentsColumn.class);
                            }

                            public ContactColumn getContactColumn() {
                                return getColumnSet().getColumnByClass(ContactColumn.class);
                            }

                            public CreatedAtColumn getCreatedAtColumn() {
                                return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                            }

                            public ReplyColumn getReplyColumn() {
                                return getColumnSet().getColumnByClass(ReplyColumn.class);
                            }

                            public SenderColumn getSenderColumn() {
                                return getColumnSet().getColumnByClass(SenderColumn.class);
                            }

                            public TicketReplyColumn getTicketReplyColumn() {
                                return getColumnSet().getColumnByClass(TicketReplyColumn.class);
                            }

                            public TicketReplyIdColumn getTicketReplyIdColumn() {
                                return getColumnSet().getColumnByClass(TicketReplyIdColumn.class);
                            }

                            public UserIdColumn getUserIdColumn() {
                                return getColumnSet().getColumnByClass(UserIdColumn.class);
                            }

                            @Order(1000)
                            public class CreateTaskFromReplyMenu extends AbstractMenu {
                                @Override
                                protected String getConfiguredText() {
                                    return TEXTS.get("CreateTask");
                                }

                                @Override
                                protected String getConfiguredIconId() {
                                    return FontIcons.Tasks;
                                }

                                @Override
                                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                    return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.SingleSelection, org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.MultiSelection);
                                }

                                @Override
                                protected void execAction() {
                                    TaskForm form = new TaskForm();
                                    form.getDescriptionField().setValue(getPreviewReplyField().getValue());
                                    form.setRelatedType(Related.TICKET);
                                    form.setRelatedId(getTicketId().longValue());
                                    form.startNew();
                                    form.waitFor();
                                    if (form.isFormStored()) {
                                        fetchTasks();
                                    }
                                }
                            }

                            @Order(2000)
                            public class DeleteMenu extends AbstractDeleteMenu {

                                @Override
                                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                    return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(TableMenuType.SingleSelection);
                                }

                                @Override
                                protected void execAction() {
                                    if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                        BEANS.get(ITicketViewService.class).deleteReply(getPreviewReplyField().getReplyId());

                                        NotificationHelper.showDeleteSuccessNotification();

                                        fetchReplies();
                                    }
                                }
                            }

                            @Order(-1000)
                            public class HasAttachmentsColumn extends AbstractBooleanColumn {
                                @Override
                                protected void execDecorateCell(Cell cell, ITableRow row) {
                                    super.execDecorateCell(cell, row);

                                    cell.setText("");
                                    cell.setIconId(getValue(row).equals(Boolean.TRUE)? FontIcons.Paperclip : null);
                                }

                                @Override
                                protected boolean getConfiguredDisplayable() {
                                    return false;
                                }

                                @Override
                                public boolean isFixedPosition() {
                                    return true;
                                }

                                @Override
                                public boolean isFixedWidth() {
                                    return true;
                                }

                                @Override
                                protected int getConfiguredWidth() {
                                    return 40;
                                }

                                @Override
                                protected int getConfiguredHorizontalAlignment() {
                                    return 1;
                                }

                            }

                            @Order(0)
                            public class TicketReplyIdColumn extends AbstractIDColumn {

                            }

                            @Order(1000)
                            public class TicketReplyColumn extends AbstractColumn<TicketReply> {
                                @Override
                                protected boolean getConfiguredDisplayable() {
                                    return false;
                                }
                            }

                            @Order(1250)
                            public class SenderColumn extends AbstractStringColumn {
                                @Override
                                protected boolean getConfiguredDisplayable() {
                                    return false;
                                }
                            }

                            @Order(1375)
                            public class UserIdColumn extends AbstractIDColumn {
                                @Override
                                protected boolean getConfiguredDisplayable() {
                                    return false;
                                }
                            }

                            @Order(1437)
                            public class CreatedAtColumn extends AbstractDateTimeColumn {
                                @Override
                                public boolean isDisplayable() {
                                    return false;
                                }
                            }

                            @Order(1750)
                            public class ContactColumn extends AbstractStringColumn {
                                @Override
                                protected boolean getConfiguredDisplayable() {
                                    return false;
                                }
                            }

                            @Order(2000)
                            public class ReplyColumn extends AbstractStringColumn {

                                @Override
                                protected boolean getConfiguredHtmlEnabled() {
                                    return true;
                                }

                                @Override
                                protected String getConfiguredCssClass() {
                                    return "table-cell-padding-10";
                                }

                                @Override
                                protected void execDecorateCell(Cell cell, ITableRow row) {
                                    super.execDecorateCell(cell, row);

                                    String creator = getContactColumn().getValue(row) != null ? getContactColumn().getValue(row) : getSenderColumn().getValue(row);
                                    String createdAt = new PrettyTime().format(getCreatedAtColumn().getValue(row));
                                    boolean hasAttachment = getHasAttachmentsColumn().getValue(row);

                                    IHtmlContent content = HTML.fragment(
                                            HTML.span(hasAttachment ? HTML.icon(FontIcons.Paperclip) : null, HTML.span(" "), HTML.span(creator).style("color:#234d74;font-size:11px;font-weight:bold;"),
                                                    HTML.italic(createdAt).style("font-size:12px;color:#333;text-allign:right;float:right;")
                                            ).style("padding:10px !important;"));

                                    cell.setText(content.toHtml());
                                }
                            }
                        }
                    }

                    @Order(1550)
                    public class ReplyAttachmentsTableField extends AbstractTableField<ReplyAttachmentsTableField.Table> {
                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @Override
                        protected boolean getConfiguredVisible() {
                            return false;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 2;
                        }

                        @ClassId("9ce7e3e5-63bd-4bb2-a953-88db5f442290")
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

                            public FormatColumn getFormatColumn() {
                                return getColumnSet().getColumnByClass(FormatColumn.class);
                            }

                            public SizeColumn getSizeColumn() {
                                return getColumnSet().getColumnByClass(SizeColumn.class);
                            }

                            public AttachmentColumn getAttachmentColumn() {
                                return getColumnSet().getColumnByClass(AttachmentColumn.class);
                            }

                            public BinaryResourceColumn getBinaryResourceColumn() {
                                return getColumnSet().getColumnByClass(BinaryResourceColumn.class);
                            }

                            public NameColumn getNameColumn() {
                                return getColumnSet().getColumnByClass(NameColumn.class);
                            }

                            public AttachmentIdColumn getAttachmentIdColumn() {
                                return getColumnSet().getColumnByClass(AttachmentIdColumn.class);
                            }

                            @Order(2000)
                            public class DeleteMenu extends AbstractDeleteMenu {

                                @Override
                                protected void execAction() {
                                    for (ITableRow row : getSelectedRows()) {
                                        row.setStatusDeleted();
                                        row.delete();
                                    }
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
                                    if (getValue(row) != null) {
                                        cell.setText(FileUtils.byteCountToDisplaySize(getValue(row)));
                                    }

                                }
                            }

                        }
                    }

                    @Order(2000)
                    public class PreviewReplyField extends AbstractHtmlField {

                        private Integer replyId;

                        @FormData
                        public Integer getReplyId() {
                            return replyId;
                        }

                        @FormData
                        public void setReplyId(Integer replyId) {
                            this.replyId = replyId;
                        }

                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 2;
                        }

                        @Override
                        protected boolean getConfiguredScrollBarEnabled() {
                            return true;
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return -1;
                        }

                        @Override
                        protected String getConfiguredCssClass() {
                            return "TicketForm_preview_reply";
                        }

                        @Override
                        protected void execInitField() {
                            super.execInitField();

                            IHtmlContent content = HTML.p("Click on reply to view content").style("padding-top:30px;font-size:13px;text-align:center;color:#9f9f9f;");
                            setValue(content.toHtml());
                        }


                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                    }
                }


            }
        }

        @Order(0)
        public class FollowMenu extends AbstractMenu {
            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.StarSolid;
            }

            @Override
            protected void execAction() {
                super.execAction();

                AbstractFormPopup<FollowersForm> popup = new AbstractFormPopup<FollowersForm>() {
                    @Override
                    protected FollowersForm createForm() {
                        FollowersForm form = new FollowersForm();
                        form.setParentInternal(TicketViewForm.this);
                        form.setTicketId(getTicketId());
                        //form.startNew();

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

        @Order(1000)
        public class ActionsMenu extends AbstractActionsMenu {

            @Override
            protected boolean getConfiguredVisible() {
                return true;
            }

            @Order(1000)
            public class DeleteMenu extends AbstractDeleteMenu {

                @Override
                protected void execAction() {
                    if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                        BEANS.get(ITicketViewService.class).delete(getTicketId());
                        NotificationHelper.showDeleteSuccessNotification();

                        getForm().doClose();
                    }
                }
            }
        }


        @Order(2000)
        public class StatusMenu extends AbstractFormFieldMenu {
            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Order(1500)
            public class StatusField extends AbstractSmartField<Integer> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Status");
                }

                @Override
                protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                    return TicketStatusLookupCall.class;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_ON_FIELD;
                }

                @Override
                protected void execChangedValue() {
                    super.execChangedValue();
                    if (getValue() == null) return;

                    BEANS.get(ITicketViewService.class).changeStatus(getTicketId(), getValue());

                    renderTicketClosedNotification();
                }
            }
        }


    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TicketViewFormData formData = new TicketViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketViewService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            getRepliesBox().setVisible(false);
            getTasksBox().setVisible(false);
            getRemindersBox().setVisible(false);
            getReplyBox().setVisible(false);
            getOtherTicketsBox().setVisible(false);
        }

        @Override
        protected void execStore() {
            TicketViewFormData formData = new TicketViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketViewService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TicketViewFormData formData = new TicketViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketViewService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            renderFollowingIcon();
            renderHeaderLabels();
            setLabels();
            getTicketTitleLabelField().setContentToRender(StringUtility.join(" - ", getCodeField().getValue(), getSubjectField().getValue()));
            setTitle(getSubjectField().getValue());
            setSubTitle(TEXTS.get("PreviewTicket"));

            getAssignToMeField().setEnabled(getAssignedToField().getValue() == null);
            renderTicketClosedNotification();
        }

        @Override
        protected boolean getConfiguredOpenExclusive() {
            return true;
        }

        @Override
        protected void execStore() {
            TicketViewFormData formData = new TicketViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketViewService.class).store(formData);
            importFormData(formData);
        }
    }

}
