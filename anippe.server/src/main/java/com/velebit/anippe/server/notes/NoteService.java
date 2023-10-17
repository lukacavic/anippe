package com.velebit.anippe.server.notes;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.attachments.AbstractAttachmentsBoxData.AttachmentsTable.AttachmentsTableRowData;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.AttachmentRequest;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.notes.AbstractSidebarNotesGroupBoxData.NotesTable.NotesTableRowData;
import com.velebit.anippe.shared.notes.INoteService;
import com.velebit.anippe.shared.notes.Note;
import com.velebit.anippe.shared.notes.NoteFormData;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NoteService extends AbstractService implements INoteService {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void delete(List<Integer> noteIds) {
        BEANS.get(NoteDao.class).delete(noteIds);

        emitModuleEvent(Note.class, new Note(), ChangeStatus.DELETED);
    }

    @Override
    public NoteFormData prepareCreate(NoteFormData formData) {
        return formData;
    }

    @Override
    public NoteFormData create(NoteFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO notes ");
        varname1.append("            (title, ");
        varname1.append("             note, ");
        varname1.append("             created_at, ");
        varname1.append("             user_id, ");
        varname1.append("             related_id, ");
        varname1.append("             related_type, ");
        varname1.append("             organisation_id, ");
        varname1.append("             is_priority) ");
        varname1.append("VALUES      (:Title, ");
        varname1.append("             :Content, ");
        varname1.append("             Now(), ");
        varname1.append("             :userId, ");
        varname1.append("             :relatedId, ");
        varname1.append("             :relatedType, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :Priority) ");
        varname1.append("RETURNING id INTO :noteId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("userId", ServerSession.get().getCurrentUser().getId()), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        saveAttachments(formData);

        emitModuleEvent(Note.class, new Note(), ChangeStatus.INSERTED);

        return formData;
    }


    public Attachment mapRowDataToAttachment(NoteFormData formData, BinaryResource binaryResource) {
        Attachment attachment = new Attachment();
        attachment.setAttachment((binaryResource).getContent());
        attachment.setCreatedAt(new Date());
        attachment.setFileName(binaryResource.getFilename());
        attachment.setFileExtension(binaryResource.getContentType());
        attachment.setFileSize(binaryResource.getContentLength());
        attachment.setRelatedId(formData.getNoteId());
        attachment.setRelatedTypeId(Constants.Related.NOTE);
        attachment.setName(binaryResource.getFilename());
        return attachment;
    }

    private void saveAttachments(NoteFormData formData) {
        AttachmentsTableRowData[] rows = formData.getAttachmentsBox().getAttachmentsTable().getRows();

        for (AttachmentsTableRowData rowData : rows) {

            if (rowData.getRowState() == ITableBeanRowHolder.STATUS_INSERTED) {
                BinaryResource binaryResource = (BinaryResource) rowData.getBinaryResource();

                Attachment attachment = mapRowDataToAttachment(formData, binaryResource);

                BEANS.get(IAttachmentService.class).saveAttachment(attachment);
            }

            if (rowData.getRowState() == ITableBeanRowHolder.STATUS_DELETED) {
                BEANS.get(IAttachmentService.class).deleteAttachments(CollectionUtility.arrayList(rowData.getAttachmentId()));
            }

        }
    }

    private List<AttachmentsTableRowData> fetchAttachments(NoteFormData formData) {
        AttachmentRequest request = new AttachmentRequest();
        request.setRelatedId(formData.getNoteId());
        request.setRelatedType(Constants.Related.NOTE);

        List<Attachment> attachments = BEANS.get(IAttachmentService.class).fetchAttachments(request);
        List<AttachmentsTableRowData> rows = new ArrayList<>();

        for (Attachment attachment : attachments) {
            AttachmentsTableRowData row = new AttachmentsTableRowData();
            row.setAttachment(attachment);
            row.setAttachmentId(attachment.getId());
            row.setFormat(attachment.getFileExtension());
            row.setSize(attachment.getFileSize());
            row.setName(attachment.getName());
            rows.add(row);
        }

        return rows;
    }

    @Override
    public NoteFormData load(NoteFormData formData) {
        Note note = BEANS.get(NoteDao.class).find(formData.getNoteId());

        formData.getTitle().setValue(note.getTitle());
        formData.getContent().setValue(note.getNote());
        formData.getPriority().setValue(note.isPriority());

        // Load attachments when loading task.
        List<AttachmentsTableRowData> attachments = fetchAttachments(formData);
        formData.getAttachmentsBox().getAttachmentsTable().setRows(attachments.toArray(new AttachmentsTableRowData[attachments.size()]));

        return formData;
    }

    @Override
    public NoteFormData store(NoteFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE notes SET ");
        varname1.append("title = :Title, ");
        varname1.append("note = :Content, ");
        varname1.append("updated_at = now(), ");
        varname1.append("is_priority = :Priority ");
        varname1.append("WHERE id = :noteId");
        SQL.update(varname1.toString(), formData);

        saveAttachments(formData);

        emitModuleEvent(Note.class, new Note(), ChangeStatus.UPDATED);

        return formData;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<NotesTableRowData> fetchNotesForSidebar(Integer relatedId, Integer relatedType, List<Integer> temporaryNoteIds) {
        List<Note> notes = BEANS.get(NoteDao.class).findFor(relatedId, relatedType);

        List<Note> temporaryNotes = BEANS.get(NoteDao.class).findForTemporaryIds(temporaryNoteIds);

        Collection<Note> mergedNotes = CollectionUtils.union(notes, temporaryNotes);
        List<Note> allNotes = mergedNotes.stream().filter(distinctByKey(Note::getId)).collect(Collectors.toList());

        Collections.sort(allNotes, new Comparator<Note>() {
            @Override
            public int compare(Note p1, Note p2) {
                return p1.getId() - p2.getId();
            }
        });

        List<NotesTableRowData> rows = CollectionUtility.emptyArrayList();

        for (Note note : allNotes) {
            NotesTableRowData noteRow = new NotesTableRowData();
            noteRow.setNote(note);
            rows.add(noteRow);
        }

        return rows;
    }

}
