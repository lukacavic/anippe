package com.velebit.anippe.shared.notes;

import com.velebit.anippe.shared.notes.AbstractSidebarNotesGroupBoxData.NotesTable.NotesTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;
import java.util.Map;

@TunnelToServer
public interface INoteService extends IService {
    NoteFormData prepareCreate(NoteFormData formData);

    NoteFormData create(NoteFormData formData);

    NoteFormData load(NoteFormData formData);

    NoteFormData store(NoteFormData formData);

    void delete(List<Integer> noteId);

    List<NotesTableRowData> fetchNotesForSidebar(Integer relatedId, Integer relatedType, List<Integer> temporaryNoteIds);

}
