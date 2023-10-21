package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import com.velebit.anippe.shared.tickets.TicketFormData.NotesTable.NotesTableRowData;
import com.velebit.anippe.shared.tickets.TicketFormData.RepliesTable.RepliesTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ITicketService extends IService {
    TicketFormData prepareCreate(TicketFormData formData);

    TicketFormData create(TicketFormData formData);

    TicketFormData load(TicketFormData formData);

    TicketFormData store(TicketFormData formData);

    String fetchPredefinedReplyContent(Long predefinedReplyId);

    List<NotesTableRowData> fetchNotes(Integer ticketId);

    List<TasksTableRowData> fetchTasks(Integer ticketId);

    void deleteNote(Integer noteId);

    List<RepliesTableRowData> fetchReplies(Integer ticketId);

    void addReply(TicketFormData formData);

    String generateSequence();

    void deleteReply(Integer ticketReplyId);

    void changeStatus(Integer ticketId, Integer statusId);

    void delete(Integer ticketId);
}
