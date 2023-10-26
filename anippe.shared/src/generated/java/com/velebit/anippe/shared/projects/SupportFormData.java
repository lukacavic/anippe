package com.velebit.anippe.shared.projects;

import com.velebit.anippe.shared.tickets.Ticket;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.projects.SupportForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class SupportFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    /**
     * access method for property ClientId.
     */
    public Integer getClientId() {
        return getClientIdProperty().getValue();
    }

    /**
     * access method for property ClientId.
     */
    public void setClientId(Integer clientId) {
        getClientIdProperty().setValue(clientId);
    }

    public ClientIdProperty getClientIdProperty() {
        return getPropertyByClass(ClientIdProperty.class);
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

    public TicketsTable getTicketsTable() {
        return getFieldByClass(TicketsTable.class);
    }

    public static class ClientIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class TicketsTable extends AbstractTableFieldBeanData {
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
            private Long m_department;
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

            public Long getDepartment() {
                return m_department;
            }

            public void setDepartment(Long newDepartment) {
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
}
