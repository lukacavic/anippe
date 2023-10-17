package com.velebit.anippe.server.notes;

import com.velebit.anippe.server.attachments.AttachmentDao;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.notes.Note;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Bean
public class NoteDao  {

	public Note find(Integer noteId) {
		Note note = new Note();

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT n.id, ");
		varname1.append("       n.title, ");
		varname1.append("       n.note, ");
		varname1.append("       u.last_name ");
		varname1.append("              || ' ' ");
		varname1.append("              || u.first_name, ");
		varname1.append("       u.id, ");
		varname1.append("       n.is_priority, ");
		varname1.append("       n.created_at ");
		varname1.append("FROM   notes n, ");
		varname1.append("       users u ");
		varname1.append("WHERE  n.deleted_at IS NULL ");
		varname1.append("AND    n.user_id = u.id ");
		varname1.append("AND    n.id = :noteId ");
		varname1.append("INTO   :{note.id}, ");
		varname1.append("       :{note.title}, ");
		varname1.append("       :{note.note}, ");
		varname1.append("       :{note.user}, ");
		varname1.append("       :{note.userId}, ");
		varname1.append("       :{note.priority}, ");
		varname1.append("       :{note.createdAt}");
		SQL.selectInto(varname1.toString(), new NVPair("noteId", noteId), new NVPair("note", note));

		// Find attachments for note
		List<Attachment> attachments = findAttachments(noteId);

		if (!CollectionUtility.isEmpty(attachments)) {
			note.setAttachments(attachments);
		}

		return note;
	}

	/**
	 * Find attachments for note
	 *
	 * @param noteId
	 * @return
	 */
	private List<Attachment> findAttachments(Integer noteId) {
		return BEANS.get(AttachmentDao.class).get(noteId, Constants.Related.NOTE);
	}

	/**
	 * Find notes for related entity
	 *
	 * @param relatedId
	 * @param relatedTypeId
	 * @return
	 */
	public List<Note> findFor(Integer relatedId, Integer relatedTypeId) {
		BeanArrayHolder<Note> notesResultSet = new BeanArrayHolder<>(Note.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT n.id, ");
		varname1.append("       n.title, ");
		varname1.append("       n.note, ");
		varname1.append("       u.last_name ");
		varname1.append("              || ' ' ");
		varname1.append("              || u.first_name, ");
		varname1.append("       u.id, ");
		varname1.append("       n.is_priority, ");
		varname1.append("       n.created_at ");
		varname1.append("FROM   notes n, ");
		varname1.append("       users u ");
		varname1.append("WHERE  n.deleted_at IS NULL ");
		varname1.append("AND    n.related_id = :relatedId ");
		varname1.append("AND    n.user_id = u.id ");
		varname1.append("AND    n.related_type = :relatedType ");
		varname1.append("ORDER BY n.is_priority DESC, n.created_at DESC ");
		varname1.append("INTO   :{note.id}, ");
		varname1.append("       :{note.title}, ");
		varname1.append("       :{note.note}, ");
		varname1.append("       :{note.user}, ");
		varname1.append("       :{note.userId}, ");
		varname1.append("       :{note.priority}, ");
		varname1.append("       :{note.createdAt}");
		SQL.selectInto(varname1.toString(), new NVPair("relatedId", relatedId), new NVPair("relatedType", relatedTypeId), new NVPair("note", notesResultSet));

		// Append attachment to each note.
		List<Note> notes = new ArrayList<>();

		for (Note note : notesResultSet.getBeans()) {
			List<Attachment> attachments = findAttachments(note.getId());
			if (!CollectionUtility.isEmpty(attachments)) {
				note.setAttachments(attachments);
			}

			notes.add(note);
		}

		return notes;
	}

	public void delete(List<Integer> noteId) {

		StringBuffer varname1 = new StringBuffer();
		varname1.append("UPDATE notes ");
		varname1.append("SET    deleted_at = Now() ");
		varname1.append("WHERE  id = :noteId");
		SQL.update(varname1.toString(), new NVPair("noteId", noteId));
	}

	public List<Note> findFor(Map<Integer, List<Integer>> related) {
		BeanArrayHolder<Note> notesResultSet = new BeanArrayHolder<>(Note.class);

		// No data if related is not specified.
		if (CollectionUtility.isEmpty(related)) {
			return Arrays.asList(notesResultSet.getBeans());
		}

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT n.id, ");
		varname1.append("       n.title, ");
		varname1.append("       n.note, ");
		varname1.append("       u.last_name ");
		varname1.append("              || ' ' ");
		varname1.append("              || u.first_name, ");
		varname1.append("       u.id, ");
		varname1.append("       n.is_priority, ");
		varname1.append("       n.created_at, ");
		varname1.append("       n.related_type ");
		varname1.append("FROM   notes n, ");
		varname1.append("       users u ");
		varname1.append("WHERE  n.deleted_at IS NULL ");
		varname1.append("AND    n.user_id = u.id ");

		// Filter by related modules
		if (related.size() > 1) {
			boolean isFirst = true;
			varname1.append(" AND ( ");
			for (Map.Entry<Integer, List<Integer>> entry : related.entrySet()) {
				String appendOr = isFirst ? "" : " OR ";
				varname1.append(appendOr);

				varname1.append("  ( n.related_type  = " + entry.getKey() + " AND n.related_id " + formatInClause(entry.getValue()) + ") ");

				isFirst = false;
			}
			varname1.append(") ");

		} else if (related.size() == 1) {
			for (Map.Entry<Integer, List<Integer>> entry : related.entrySet()) {
				varname1.append(" AND n.related_type = " + entry.getKey() + " AND n.related_id " + formatInClause(entry.getValue()));
			}
		}

		varname1.append("ORDER BY n.is_priority DESC, n.created_at DESC ");
		varname1.append("INTO   :{note.id}, ");
		varname1.append("       :{note.title}, ");
		varname1.append("       :{note.note}, ");
		varname1.append("       :{note.user}, ");
		varname1.append("       :{note.userId}, ");
		varname1.append("       :{note.priority}, ");
		varname1.append("       :{note.createdAt},");
		varname1.append("       :{note.relatedTypeId}");
		SQL.selectInto(varname1.toString(), new NVPair("note", notesResultSet));

		// Append attachment to each note.
		List<Note> notes = CollectionUtility.emptyArrayList();

		for (Note note : notesResultSet.getBeans()) {
			List<Attachment> attachments = findAttachments(note.getId());
			if (!CollectionUtility.isEmpty(attachments)) {
				note.setAttachments(attachments);
			}

			notes.add(note);
		}

		return notes;
	}

	private String formatInClause(List<Integer> list) {
		String format = CollectionUtility.format(list, ",");

		return StringUtility.box(" in(", format, ") ");
	}

	public List<Note> findForTemporaryIds(List<Integer> temporaryNoteIds) {
		BeanArrayHolder<Note> notesResultSet = new BeanArrayHolder<>(Note.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT n.id, ");
		varname1.append("       n.title, ");
		varname1.append("       n.note, ");
		varname1.append("       u.last_name ");
		varname1.append("              || ' ' ");
		varname1.append("              || u.first_name, ");
		varname1.append("       u.id, ");
		varname1.append("       n.is_priority, ");
		varname1.append("       n.created_at, ");
		varname1.append("       n.related_type ");
		varname1.append("FROM   notes n, ");
		varname1.append("       users u ");
		varname1.append("WHERE  n.deleted_at IS NULL ");
		varname1.append("AND    n.user_id = u.id ");
		varname1.append("AND    n.id = :temporaryNoteIds ");
		varname1.append("ORDER BY n.is_priority DESC, n.created_at DESC ");
		varname1.append("INTO   :{note.id}, ");
		varname1.append("       :{note.title}, ");
		varname1.append("       :{note.note}, ");
		varname1.append("       :{note.user}, ");
		varname1.append("       :{note.userId}, ");
		varname1.append("       :{note.priority}, ");
		varname1.append("       :{note.createdAt},");
		varname1.append("       :{note.relatedTypeId}");
		SQL.selectInto(varname1.toString(), new NVPair("note", notesResultSet), new NVPair("temporaryNoteIds", temporaryNoteIds));

		// Append attachment to each note.
		List<Note> notes = CollectionUtility.emptyArrayList();

		for (Note note : notesResultSet.getBeans()) {
			List<Attachment> attachments = findAttachments(note.getId());
			if (!CollectionUtility.isEmpty(attachments)) {
				note.setAttachments(attachments);
			}

			notes.add(note);
		}

		return notes;
	}

	public void linkTemporaryNotesWithEntity(List<Integer> temporaryNoteIds, Integer relatedType, Integer relatedId) {
		StringBuffer varname1 = new StringBuffer();
		varname1.append("UPDATE notes ");
		varname1.append("SET    related_id = :relatedId, ");
		varname1.append("       related_type = :relatedType ");
		varname1.append("WHERE  id = :noteIds");
		SQL.update(varname1.toString(), new NVPair("noteIds", temporaryNoteIds), new NVPair("relatedType", relatedType), new NVPair("relatedId", relatedId));
	}
}
