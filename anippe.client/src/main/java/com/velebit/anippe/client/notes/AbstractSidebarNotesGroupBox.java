package com.velebit.anippe.client.notes;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.RelatedLookupCall;
import com.velebit.anippe.client.notes.AbstractSidebarNotesGroupBox.NotesTableField.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.notes.AbstractSidebarNotesGroupBoxData;
import com.velebit.anippe.shared.notes.AbstractSidebarNotesGroupBoxData.NotesTable.NotesTableRowData;
import com.velebit.anippe.shared.notes.INoteService;
import com.velebit.anippe.shared.notes.Note;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@FormData(value = AbstractSidebarNotesGroupBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractSidebarNotesGroupBox extends AbstractGroupBox {

    private Integer relatedId;
    private Integer relatedType;
    private Map<Integer, List<Integer>> related = new HashMap<>();

    private List<Integer> temporaryNoteIds = CollectionUtility.emptyArrayList();

    @FormData
    public List<Integer> getTemporaryNoteIds() {
        return temporaryNoteIds;
    }

    @FormData
    public void setTemporaryNoteIds(List<Integer> temporaryNoteIds) {
        this.temporaryNoteIds = temporaryNoteIds;
    }

    @FormData
    public Map<Integer, List<Integer>> getRelated() {
        return related;
    }

    @FormData
    public void setRelated(Map<Integer, List<Integer>> related) {
        this.related = related;
    }

    @FormData
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    @Override
    protected int getConfiguredGridColumnCount() {
        return 1;
    }

    @Override
    protected void execInitField() {
        registerDataChangeListener(Note.class);
    }

    @Override
    protected void execDataChanged(Object... dataTypes) {
        fetchNotes();
    }

    @Override
    protected void execDisposeField() {
        unregisterDataChangeListener(Note.class);
    }

    public void fetchNotes() {
        if ((getRelatedId() == null && getRelatedType() == null) && getTemporaryNoteIds().isEmpty())
            return;

        List<NotesTableRowData> rowData = BEANS.get(INoteService.class).fetchNotesForSidebar(getRelatedId(), getRelatedType(), temporaryNoteIds);
        getNotesTableField().getTable().importFromTableRowBeanData(rowData, NotesTableRowData.class);
    }

    @Override
    protected String getConfiguredLabel() {
        return TEXTS.get("Notes");
    }

    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }

    public NotesTableField getNotesTableField() {
        return getFieldByClass(NotesTableField.class);
    }

    @Order(1000)
    public class NotesTableField extends AbstractTableField<Table> {

        @Override
        protected int getConfiguredGridH() {
            return 6;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @Override
        public boolean isLabelVisible() {
            return false;
        }

        public class Table extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
                return true;
            }

            @Override
            protected boolean getConfiguredHeaderVisible() {
                return false;
            }

            @Override
            protected void execRowAction(ITableRow row) {
                super.execRowAction(row);

                getMenuByClass(EditMenu.class).doAction();
            }

            public NoteColumn getNoteColumn() {
                return getColumnSet().getColumnByClass(NoteColumn.class);
            }

            @Order(-2000)
            public class RefreshMenu extends AbstractMenu {
                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Spinner1;
                }

                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("Refresh");
                }

                @Override
                protected void execAction() {
                    fetchNotes();
                }
            }

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("NewNote");
                }

                @Override
                protected void execAction() {
                    NoteForm form = new NoteForm();
                    form.startNew();
                    form.setRelatedId(relatedId);
                    form.setRelatedType(relatedType);
                    form.waitFor();
                    if (form.isFormStored()) {

                        if (getRelatedId() == null) {
                            getTemporaryNoteIds().add(form.getNoteId());
                        }

                        fetchNotes();
                    }
                }
            }

            @Order(1000)
            public class NoteColumn extends AbstractColumn<Note> {

                @Override
                protected String getConfiguredHeaderText() {
                    return TEXTS.get("Note");
                }

                @Override
                protected boolean getConfiguredHtmlEnabled() {
                    return true;
                }

                @Override
                protected int getConfiguredWidth() {
                    return 100;
                }

                private String findRelatedName(Note note) {
                    RelatedLookupCall call = new RelatedLookupCall();
                    call.setKey(note.getRelatedTypeId());
                    List<? extends ILookupRow<Integer>> callRow = call.getDataByKey();
                    if (callRow.size() > 0) {
                        return callRow.get(0).getText();
                    }

                    return null;
                }

                @Override
                protected void execDecorateCell(Cell cell, ITableRow row) {
                    Note note = getValue(row);
                    String related = findRelatedName(note);

                    String createdAt = DateUtility.formatDateTime(note.getCreatedAt());
                    IHtmlContent title = HTML.fragment(HTML.bold(note.hasAttachment() ? HTML.span(HTML.icon(FontIcons.Paperclip)) : null, " ", note.getTitle()));

                    String content = HTML.fragment(title,
                                    HTML.p(note.getNote()),
                                    HTML.div(note.getUser(), " ", HTML.italic(createdAt)), HTML.div(related).style("font-size:8px;text-transform:uppercase;text-align:right;float:right;"))
                            .toString();

                    if (note.isPriority()) {
                        cell.setCssClass("cell-border-red-left");
                    }

                    cell.setText(content);
                }
            }

            @Order(1000)
            public class EditMenu extends AbstractEditMenu {

                @Override
                protected void execAction() {
                    Note note = (Note) getNoteColumn().getSelectedValue();
                    NoteForm form = new NoteForm();
                    form.setNoteId(note.getId());
                    form.startModify();
                    form.waitFor();
                    if (form.isFormStored()) {
                        fetchNotes();
                    }
                }
            }

            @Order(2000)
            public class DeleteMenu extends AbstractDeleteMenu {

                @Override
                protected void execAction() {
                    if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                        List<Note> notes = getNoteColumn().getSelectedValues().stream().map(e -> (Note) e).collect(Collectors.toList());

                        BEANS.get(INoteService.class).delete(notes.stream().map(Note::getId).collect(Collectors.toList()));

                        NotificationHelper.showDeleteSuccessNotification();

                        fetchNotes();
                    }
                }
            }
        }
    }
}
