package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.tickets.TicketsTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class TicketsTablePageData extends AbstractTablePageData {
    private static final long serialVersionUID = 1L;

    @Override
    public TicketsTableRowData addRow() {
        return (TicketsTableRowData) super.addRow();
    }

    @Override
    public TicketsTableRowData addRow(int rowState) {
        return (TicketsTableRowData) super.addRow(rowState);
    }

    @Override
    public TicketsTableRowData createRow() {
        return new TicketsTableRowData();
    }

    @Override
    public Class<? extends AbstractTableRowData> getRowType() {
        return TicketsTableRowData.class;
    }

    @Override
    public TicketsTableRowData[] getRows() {
        return (TicketsTableRowData[]) super.getRows();
    }

    @Override
    public TicketsTableRowData rowAt(int index) {
        return (TicketsTableRowData) super.rowAt(index);
    }

    public void setRows(TicketsTableRowData[] rows) {
        super.setRows(rows);
    }

    public static class TicketsTableRowData extends AbstractTableRowData {
        private static final long serialVersionUID = 1L;
        public static final String parentID = "parentID";
        public static final String primaryID = "primaryID";
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
        private String m_parentID;
        private String m_primaryID;
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

        public String getParentID() {
            return m_parentID;
        }

        public void setParentID(String newParentID) {
            m_parentID = newParentID;
        }

        public String getPrimaryID() {
            return m_primaryID;
        }

        public void setPrimaryID(String newPrimaryID) {
            m_primaryID = newPrimaryID;
        }

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
