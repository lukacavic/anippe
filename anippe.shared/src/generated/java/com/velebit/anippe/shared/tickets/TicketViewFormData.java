package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.reminders.AbstractRemindersGroupBoxData;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.tickets.TicketViewForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class TicketViewFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public AssignToMe getAssignToMe() {
        return getFieldByClass(AssignToMe.class);
    }

    public AssignedTo getAssignedTo() {
        return getFieldByClass(AssignedTo.class);
    }

    public AttachmentsTable getAttachmentsTable() {
        return getFieldByClass(AttachmentsTable.class);
    }

    public CC getCC() {
        return getFieldByClass(CC.class);
    }

    public ChangeStatus getChangeStatus() {
        return getFieldByClass(ChangeStatus.class);
    }

    public CloseAfterReply getCloseAfterReply() {
        return getFieldByClass(CloseAfterReply.class);
    }

    public Code getCode() {
        return getFieldByClass(Code.class);
    }

    public Contact getContact() {
        return getFieldByClass(Contact.class);
    }

    public Department getDepartment() {
        return getFieldByClass(Department.class);
    }

    public KnowledgeBaseArticle getKnowledgeBaseArticle() {
        return getFieldByClass(KnowledgeBaseArticle.class);
    }

    public NotesTable getNotesTable() {
        return getFieldByClass(NotesTable.class);
    }

    public OtherTicketsTable getOtherTicketsTable() {
        return getFieldByClass(OtherTicketsTable.class);
    }

    public PredefinedReply getPredefinedReply() {
        return getFieldByClass(PredefinedReply.class);
    }

    public PreviewReply getPreviewReply() {
        return getFieldByClass(PreviewReply.class);
    }

    public Priority getPriority() {
        return getFieldByClass(Priority.class);
    }

    public Project getProject() {
        return getFieldByClass(Project.class);
    }

    /**
     * access method for property ProjectId.
     */
    public Integer getProjectId() {
        return getProjectIdProperty().getValue();
    }

    /**
     * access method for property ProjectId.
     */
    public void setProjectId(Integer projectId) {
        getProjectIdProperty().setValue(projectId);
    }

    public ProjectIdProperty getProjectIdProperty() {
        return getPropertyByClass(ProjectIdProperty.class);
    }

    public RemindersBox getRemindersBox() {
        return getFieldByClass(RemindersBox.class);
    }

    public RepliesTable getRepliesTable() {
        return getFieldByClass(RepliesTable.class);
    }

    public Reply getReply() {
        return getFieldByClass(Reply.class);
    }

    public ReplyAttachmentsTable getReplyAttachmentsTable() {
        return getFieldByClass(ReplyAttachmentsTable.class);
    }

    public Status getStatus() {
        return getFieldByClass(Status.class);
    }

    public Subject getSubject() {
        return getFieldByClass(Subject.class);
    }

    public TasksBox getTasksBox() {
        return getFieldByClass(TasksBox.class);
    }

    /**
     * access method for property TicketId.
     */
    public Integer getTicketId() {
        return getTicketIdProperty().getValue();
    }

    /**
     * access method for property TicketId.
     */
    public void setTicketId(Integer ticketId) {
        getTicketIdProperty().setValue(ticketId);
    }

    public TicketIdProperty getTicketIdProperty() {
        return getPropertyByClass(TicketIdProperty.class);
    }

    public TicketTitleLabel getTicketTitleLabel() {
        return getFieldByClass(TicketTitleLabel.class);
    }

    public static class AssignToMe extends AbstractValueFieldData<Boolean> {
        private static final long serialVersionUID = 1L;
    }

    public static class AssignedTo extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class AttachmentsTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public AttachmentsTableRowData addRow() {
            return (AttachmentsTableRowData) super.addRow();
        }

        @Override
        public AttachmentsTableRowData addRow(int rowState) {
            return (AttachmentsTableRowData) super.addRow(rowState);
        }

        @Override
        public AttachmentsTableRowData createRow() {
            return new AttachmentsTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return AttachmentsTableRowData.class;
        }

        @Override
        public AttachmentsTableRowData[] getRows() {
            return (AttachmentsTableRowData[]) super.getRows();
        }

        @Override
        public AttachmentsTableRowData rowAt(int index) {
            return (AttachmentsTableRowData) super.rowAt(index);
        }

        public void setRows(AttachmentsTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class AttachmentsTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String binaryResource = "binaryResource";
            public static final String name = "name";
            public static final String format = "format";
            public static final String size = "size";
            private BinaryResource m_binaryResource;
            private String m_name;
            private String m_format;
            private Integer m_size;

            public BinaryResource getBinaryResource() {
                return m_binaryResource;
            }

            public void setBinaryResource(BinaryResource newBinaryResource) {
                m_binaryResource = newBinaryResource;
            }

            public String getName() {
                return m_name;
            }

            public void setName(String newName) {
                m_name = newName;
            }

            public String getFormat() {
                return m_format;
            }

            public void setFormat(String newFormat) {
                m_format = newFormat;
            }

            public Integer getSize() {
                return m_size;
            }

            public void setSize(Integer newSize) {
                m_size = newSize;
            }
        }
    }

    public static class CC extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class ChangeStatus extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class CloseAfterReply extends AbstractValueFieldData<Boolean> {
        private static final long serialVersionUID = 1L;
    }

    public static class Code extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Contact extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Department extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class KnowledgeBaseArticle extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class NotesTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public NotesTableRowData addRow() {
            return (NotesTableRowData) super.addRow();
        }

        @Override
        public NotesTableRowData addRow(int rowState) {
            return (NotesTableRowData) super.addRow(rowState);
        }

        @Override
        public NotesTableRowData createRow() {
            return new NotesTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return NotesTableRowData.class;
        }

        @Override
        public NotesTableRowData[] getRows() {
            return (NotesTableRowData[]) super.getRows();
        }

        @Override
        public NotesTableRowData rowAt(int index) {
            return (NotesTableRowData) super.rowAt(index);
        }

        public void setRows(NotesTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class NotesTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String noteId = "noteId";
            public static final String user = "user";
            public static final String createdAt = "createdAt";
            public static final String note = "note";
            public static final String userId = "userId";
            private Integer m_noteId;
            private String m_user;
            private Date m_createdAt;
            private String m_note;
            private Integer m_userId;

            public Integer getNoteId() {
                return m_noteId;
            }

            public void setNoteId(Integer newNoteId) {
                m_noteId = newNoteId;
            }

            public String getUser() {
                return m_user;
            }

            public void setUser(String newUser) {
                m_user = newUser;
            }

            public Date getCreatedAt() {
                return m_createdAt;
            }

            public void setCreatedAt(Date newCreatedAt) {
                m_createdAt = newCreatedAt;
            }

            public String getNote() {
                return m_note;
            }

            public void setNote(String newNote) {
                m_note = newNote;
            }

            public Integer getUserId() {
                return m_userId;
            }

            public void setUserId(Integer newUserId) {
                m_userId = newUserId;
            }
        }
    }

    public static class OtherTicketsTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public OtherTicketsTableRowData addRow() {
            return (OtherTicketsTableRowData) super.addRow();
        }

        @Override
        public OtherTicketsTableRowData addRow(int rowState) {
            return (OtherTicketsTableRowData) super.addRow(rowState);
        }

        @Override
        public OtherTicketsTableRowData createRow() {
            return new OtherTicketsTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return OtherTicketsTableRowData.class;
        }

        @Override
        public OtherTicketsTableRowData[] getRows() {
            return (OtherTicketsTableRowData[]) super.getRows();
        }

        @Override
        public OtherTicketsTableRowData rowAt(int index) {
            return (OtherTicketsTableRowData) super.rowAt(index);
        }

        public void setRows(OtherTicketsTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class OtherTicketsTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String ticket = "ticket";
            public static final String code = "code";
            public static final String subject = "subject";
            public static final String createdAt = "createdAt";
            public static final String department = "department";
            public static final String contact = "contact";
            public static final String status = "status";
            public static final String priority = "priority";
            public static final String assignedUser = "assignedUser";
            public static final String lastReply = "lastReply";
            private Ticket m_ticket;
            private String m_code;
            private String m_subject;
            private Date m_createdAt;
            private TicketDepartment m_department;
            private String m_contact;
            private Integer m_status;
            private Integer m_priority;
            private Long m_assignedUser;
            private Date m_lastReply;

            public Ticket getTicket() {
                return m_ticket;
            }

            public void setTicket(Ticket newTicket) {
                m_ticket = newTicket;
            }

            public String getCode() {
                return m_code;
            }

            public void setCode(String newCode) {
                m_code = newCode;
            }

            public String getSubject() {
                return m_subject;
            }

            public void setSubject(String newSubject) {
                m_subject = newSubject;
            }

            public Date getCreatedAt() {
                return m_createdAt;
            }

            public void setCreatedAt(Date newCreatedAt) {
                m_createdAt = newCreatedAt;
            }

            public TicketDepartment getDepartment() {
                return m_department;
            }

            public void setDepartment(TicketDepartment newDepartment) {
                m_department = newDepartment;
            }

            public String getContact() {
                return m_contact;
            }

            public void setContact(String newContact) {
                m_contact = newContact;
            }

            public Integer getStatus() {
                return m_status;
            }

            public void setStatus(Integer newStatus) {
                m_status = newStatus;
            }

            public Integer getPriority() {
                return m_priority;
            }

            public void setPriority(Integer newPriority) {
                m_priority = newPriority;
            }

            public Long getAssignedUser() {
                return m_assignedUser;
            }

            public void setAssignedUser(Long newAssignedUser) {
                m_assignedUser = newAssignedUser;
            }

            public Date getLastReply() {
                return m_lastReply;
            }

            public void setLastReply(Date newLastReply) {
                m_lastReply = newLastReply;
            }
        }
    }

    public static class PredefinedReply extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class PreviewReply extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;

        /**
         * access method for property ReplyId.
         */
        public Integer getReplyId() {
            return getReplyIdProperty().getValue();
        }

        /**
         * access method for property ReplyId.
         */
        public void setReplyId(Integer replyId) {
            getReplyIdProperty().setValue(replyId);
        }

        public ReplyIdProperty getReplyIdProperty() {
            return getPropertyByClass(ReplyIdProperty.class);
        }

        public static class ReplyIdProperty extends AbstractPropertyData<Integer> {
            private static final long serialVersionUID = 1L;
        }
    }

    public static class Priority extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Project extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class RemindersBox extends AbstractRemindersGroupBoxData {
        private static final long serialVersionUID = 1L;
    }

    public static class RepliesTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public RepliesTableRowData addRow() {
            return (RepliesTableRowData) super.addRow();
        }

        @Override
        public RepliesTableRowData addRow(int rowState) {
            return (RepliesTableRowData) super.addRow(rowState);
        }

        @Override
        public RepliesTableRowData createRow() {
            return new RepliesTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return RepliesTableRowData.class;
        }

        @Override
        public RepliesTableRowData[] getRows() {
            return (RepliesTableRowData[]) super.getRows();
        }

        @Override
        public RepliesTableRowData rowAt(int index) {
            return (RepliesTableRowData) super.rowAt(index);
        }

        public void setRows(RepliesTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class RepliesTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String hasAttachments = "hasAttachments";
            public static final String ticketReplyId = "ticketReplyId";
            public static final String ticketReply = "ticketReply";
            public static final String sender = "sender";
            public static final String userId = "userId";
            public static final String createdAt = "createdAt";
            public static final String contact = "contact";
            public static final String reply = "reply";
            private Boolean m_hasAttachments;
            private Integer m_ticketReplyId;
            private TicketReply m_ticketReply;
            private String m_sender;
            private Integer m_userId;
            private Date m_createdAt;
            private String m_contact;
            private String m_reply;

            public Boolean getHasAttachments() {
                return m_hasAttachments;
            }

            public void setHasAttachments(Boolean newHasAttachments) {
                m_hasAttachments = newHasAttachments;
            }

            public Integer getTicketReplyId() {
                return m_ticketReplyId;
            }

            public void setTicketReplyId(Integer newTicketReplyId) {
                m_ticketReplyId = newTicketReplyId;
            }

            public TicketReply getTicketReply() {
                return m_ticketReply;
            }

            public void setTicketReply(TicketReply newTicketReply) {
                m_ticketReply = newTicketReply;
            }

            public String getSender() {
                return m_sender;
            }

            public void setSender(String newSender) {
                m_sender = newSender;
            }

            public Integer getUserId() {
                return m_userId;
            }

            public void setUserId(Integer newUserId) {
                m_userId = newUserId;
            }

            public Date getCreatedAt() {
                return m_createdAt;
            }

            public void setCreatedAt(Date newCreatedAt) {
                m_createdAt = newCreatedAt;
            }

            public String getContact() {
                return m_contact;
            }

            public void setContact(String newContact) {
                m_contact = newContact;
            }

            public String getReply() {
                return m_reply;
            }

            public void setReply(String newReply) {
                m_reply = newReply;
            }
        }
    }

    public static class Reply extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class ReplyAttachmentsTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public ReplyAttachmentsTableRowData addRow() {
            return (ReplyAttachmentsTableRowData) super.addRow();
        }

        @Override
        public ReplyAttachmentsTableRowData addRow(int rowState) {
            return (ReplyAttachmentsTableRowData) super.addRow(rowState);
        }

        @Override
        public ReplyAttachmentsTableRowData createRow() {
            return new ReplyAttachmentsTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return ReplyAttachmentsTableRowData.class;
        }

        @Override
        public ReplyAttachmentsTableRowData[] getRows() {
            return (ReplyAttachmentsTableRowData[]) super.getRows();
        }

        @Override
        public ReplyAttachmentsTableRowData rowAt(int index) {
            return (ReplyAttachmentsTableRowData) super.rowAt(index);
        }

        public void setRows(ReplyAttachmentsTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class ReplyAttachmentsTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String attachmentId = "attachmentId";
            public static final String binaryResource = "binaryResource";
            public static final String attachment = "attachment";
            public static final String name = "name";
            public static final String format = "format";
            public static final String size = "size";
            private Integer m_attachmentId;
            private Object m_binaryResource;
            private Object m_attachment;
            private String m_name;
            private String m_format;
            private Integer m_size;

            public Integer getAttachmentId() {
                return m_attachmentId;
            }

            public void setAttachmentId(Integer newAttachmentId) {
                m_attachmentId = newAttachmentId;
            }

            public Object getBinaryResource() {
                return m_binaryResource;
            }

            public void setBinaryResource(Object newBinaryResource) {
                m_binaryResource = newBinaryResource;
            }

            public Object getAttachment() {
                return m_attachment;
            }

            public void setAttachment(Object newAttachment) {
                m_attachment = newAttachment;
            }

            public String getName() {
                return m_name;
            }

            public void setName(String newName) {
                m_name = newName;
            }

            public String getFormat() {
                return m_format;
            }

            public void setFormat(String newFormat) {
                m_format = newFormat;
            }

            public Integer getSize() {
                return m_size;
            }

            public void setSize(Integer newSize) {
                m_size = newSize;
            }
        }
    }

    public static class Status extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Subject extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class TasksBox extends AbstractTasksGroupBoxData {
        private static final long serialVersionUID = 1L;
    }

    public static class TicketIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class TicketTitleLabel extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }
}
