package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.tasks.AbstractTasksTable;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.tickets.TicketForm.MainBox.MainTabBox.RemindersBox.RemindersTableField;
import com.velebit.anippe.client.tickets.TicketForm.MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.AddReplyButton;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tickets.ITicketService;
import com.velebit.anippe.shared.tickets.TicketFormData;
import com.velebit.anippe.shared.tickets.TicketReply;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.form.fields.AbstractFormFieldMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.HeaderCell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSaveButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.popup.AbstractFormPopup;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = TicketFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TicketForm extends AbstractForm {

    private Integer ticketId;
    private Integer projectId;

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

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Info;
    }

    @FormData
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        setLabels();
    }

    public void setLabels() {
        int tasksCount = getTasksTableField().getTable().getRowCount();
        getTasksBox().setLabel(getTasksBox().getConfiguredLabel() + " (" + tasksCount + ")");

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

    public MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.AddAttachmentsButton getAddAttachmentsButton() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.AddAttachmentsButton.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.AssignedToField getAssignedToField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.AssignedToField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public AddReplyButton getAddReplyButton() {
        return getFieldByClass(AddReplyButton.class);
    }

    public MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.CCField getCCField() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.CCField.class);
    }

    public MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.ChangeStatusField getChangeStatusField() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.ChangeStatusField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    public MainBox.MainTabBox.MainInformationsBox.ContactField getContactField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.ContactField.class);
    }

    public MainBox.MainTabBox.ReplyBox.KnowledgeBaseArticleField getKnowledgeBaseArticleField() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.KnowledgeBaseArticleField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox getMainInformationsBox() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.ReplyBox.NotesTableField getNotesTableField() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.NotesTableField.class);
    }

    public MainBox.MainTabBox.OtherTicketsBox getOtherTicketsBox() {
        return getFieldByClass(MainBox.MainTabBox.OtherTicketsBox.class);
    }

    public MainBox.MainTabBox.OtherTicketsBox.OtherTicketsTableField getOtherTicketsTableField() {
        return getFieldByClass(MainBox.MainTabBox.OtherTicketsBox.OtherTicketsTableField.class);
    }

    public MainBox.MainTabBox.ReplyBox.PredefinedReplyField getPredefinedReplyField() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.PredefinedReplyField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.PriorityField getPriorityField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PriorityField.class);
    }

    public MainBox.MainTabBox.RemindersBox getRemindersBox() {
        return getFieldByClass(MainBox.MainTabBox.RemindersBox.class);
    }


    public RemindersTableField getRemindersTableField() {
        return getFieldByClass(RemindersTableField.class);
    }

    public MainBox.RepliesBox getRepliesBox() {
        return getFieldByClass(MainBox.RepliesBox.class);
    }

    public MainBox.RepliesBox.RepliesTableField getRepliesTableField() {
        return getFieldByClass(MainBox.RepliesBox.RepliesTableField.class);
    }

    public MainBox.MainTabBox.ReplyBox getReplyBox() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.class);
    }

    public MainBox.MainTabBox.ReplyBox.ReplyField getReplyField() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.ReplyField.class);
    }

    public MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox getSendOptionsSequenceBox() {
        return getFieldByClass(MainBox.MainTabBox.ReplyBox.SendOptionsSequenceBox.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.SubjectField getSubjectField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.SubjectField.class);
    }

    public MainBox.MainTabBox.TasksBox getTasksBox() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.class);
    }

    public MainBox.MainTabBox.TasksBox.TasksTableField getTasksTableField() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.TasksTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
            return 5;
        }

        @Order(1000)
        public class ActionsMenu extends AbstractActionsMenu {

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


                @Override
                protected int getConfiguredGridColumnCount() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
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

                        @Override
                        protected void execInitTable() {
                            super.execInitTable();

                            ITableRow row = addRow();
                            getNoteColumn().setValue(row, "Ovo je moja napomena.");
                        }

                        @Override
                        protected boolean getConfiguredAutoResizeColumns() {
                            return true;
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

                                IHtmlContent title = HTML.div(
                                        HTML.bold("Napomena od: Luka Čavić").style("color:#b45f0e;"),
                                        HTML.br(),
                                        HTML.div("Vrijeme kreiranja: 21.11.1989").style("color:#bb7e43;"),
                                        HTML.br(),
                                        HTML.div("Ovo je moja napomena primjer..").style("color:#444444;")
                                ).style("background-color:#f8f8b4;padding:10px;");

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
                            return TEXTS.get("ChangeStatus");
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
                    public class CCField extends AbstractStringField {
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
                    public class AddReplyButton extends AbstractSaveButton {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("AddReply");
                        }

                        @Override
                        protected Boolean getConfiguredDefaultButton() {
                            return true;
                        }

                        @Override
                        public boolean isProcessButton() {
                            return false;
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


                }

                @Order(2000)
                public class ContactField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Contact");
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
                }
            }

            @Order(2000)
            public class TasksBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Tasks");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class AddTaskMenu extends AbstractAddMenu {

                    @Override
                    protected void execAction() {
                        TaskForm form = new TaskForm();
                        form.setRelatedId(getTicketId().longValue());
                        form.setRelatedType(Constants.Related.TICKET);
                        form.startNew();
                        form.waitFor();
                        if (form.isFormStored()) {
                            NotificationHelper.showSaveSuccessNotification();
                            fetchTasks();
                        }
                    }
                }

                @Order(1000)
                public class TasksTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<TasksTableField.Table> {
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
                        return 6;
                    }

                    @ClassId("d28eba7f-3183-4ac2-8171-4cb7a0d4a2ae")
                    public class Table extends AbstractTasksTable {


                    }
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
                public class OtherTicketsTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<OtherTicketsTableField.Table> {
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

                        }
                    }
                }
            }

            @Order(3000)
            public class RemindersBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Reminders");
                }

                @Override
                public boolean isVisibleGranted() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class RemindersTableField extends AbstractTableField<RemindersTableField.Table> {
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
                        return 6;
                    }

                    @ClassId("993d612b-52c0-4e0a-8378-bd73789fddf5")
                    public class Table extends AbstractTable {

                    }
                }
            }

        }

        @Order(1000)
        public class RepliesBox extends org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox {
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

            @Order(1000)
            public class RepliesTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<RepliesTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
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

                    @Order(1000)
                    public class ConvertToTaskMenu extends AbstractEditMenu {
                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("ConvertToTask");
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Tasks;
                        }

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(2000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    @Override
                    protected void execInitTable() {
                        super.execInitTable();

                        ITableRow row = addRow();
                        getReplyColumn().setValue(row, "Ovo je moj odgovor.");
                    }

                    public InformationsColumn getInformationsColumn() {
                        return getColumnSet().getColumnByClass(InformationsColumn.class);
                    }

                    public ReplyColumn getReplyColumn() {
                        return getColumnSet().getColumnByClass(ReplyColumn.class);
                    }

                    public TicketReplyColumn getTicketReplyColumn() {
                        return getColumnSet().getColumnByClass(TicketReplyColumn.class);
                    }

                    @Order(1000)
                    public class TicketReplyColumn extends AbstractColumn<TicketReply> {
                        @Override
                        protected boolean getConfiguredDisplayable() {
                            return false;
                        }
                    }

                    @Order(1500)
                    public class InformationsColumn extends AbstractStringColumn {
                        @Override
                        public boolean isFixedWidth() {
                            return true;
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
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(2000)
                    public class ReplyColumn extends AbstractStringColumn {
                        @Override
                        protected void execDecorateHeaderCell(HeaderCell cell) {
                            super.execDecorateHeaderCell(cell);
                        }

                        @Override
                        protected boolean getConfiguredTextWrap() {
                            return true;
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

                            IHtmlContent title = HTML.div(
                                    HTML.span(HTML.bold("Luka Čavić").style("color:#337ab7;"), HTML.span(", "), HTML.span("Poliklinika Sinteza").style("color:#4d4d4d;font-size:11px;")),
                                    HTML.br(),HTML.br(),
                                    HTML.div("Ovo je moja napomena primjer..Ovo je moja napomena primjer..Ovo je moja napomena primjer..Ovo je moja napomena primjer..Ovo je moja napomena primjer..").style("color:#444444;"),
                                    HTML.div("Vrijeme odgovora: 21.11.2023 08:24").style("font-size:11px; color:#4d4d4d;font-style:italic;margin-top:10px;")
                            ).style("background-color:#f8f8b4;padding:10px;");

                            cell.setText(title.toHtml());
                        }


                    }
                }
            }
        }

        @Order(2000)
        public class TicketTitleFormFieldMenu extends AbstractFormFieldMenu {

            @Order(1500)
            public class TicketTitleLabelField extends AbstractLabelField {
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

                @Override
                protected void execInitField() {
                    IHtmlContent content = HTML.bold("#1 - Dodavanje novih usluga u sustav").style("font-size:14px;");
                    setValue(content.toHtml());
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
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).prepareCreate(formData);
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
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).store(formData);
            importFormData(formData);
        }
    }

    public void fetchTasks() {

    }
}
