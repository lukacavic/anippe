package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.NotesTable.NotesTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.OtherTicketsTable.OtherTicketsTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.RepliesTable.RepliesTableRowData;
import com.velebit.anippe.shared.tickets.TicketViewFormData.ReplyAttachmentsTable.ReplyAttachmentsTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ITicketViewService extends IService {
    TicketViewFormData prepareCreate(TicketViewFormData formData);

    TicketViewFormData create(TicketViewFormData formData);

    TicketViewFormData load(TicketViewFormData formData);

    boolean isUserFollowerOfTicket(Integer ticketId);

    List<OtherTicketsTableRowData> fetchOtherTicketRows(Long contactId, Integer ticketId);

    TicketViewFormData store(TicketViewFormData formData);

    String fetchPredefinedReplyContent(Long predefinedReplyId);

    List<NotesTableRowData> fetchNotes(Integer ticketId);

    List<TasksTableRowData> fetchTasks(Integer ticketId);

    void deleteNote(Integer noteId);

    List<ReplyAttachmentsTableRowData> fetchReplyAttachments(Integer replyId);

    List<RepliesTableRowData> fetchReplies(Integer ticketId);

    void addReply(TicketViewFormData formData);

    void deleteReply(Integer ticketReplyId);

    void changeStatus(Integer ticketId, Integer statusId);

    void delete(Integer ticketId);

    String findReplyById(Integer replyId);

    void changePriority(Integer ticketId, Integer priorityId);

    void changeAssignedUser(Integer ticketId, Integer assignedUserId);

}
