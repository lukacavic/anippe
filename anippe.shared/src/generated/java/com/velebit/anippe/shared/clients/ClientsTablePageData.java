package com.velebit.anippe.shared.clients;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.clients.ClientsTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class ClientsTablePageData extends AbstractTablePageData {
    private static final long serialVersionUID = 1L;

    @Override
    public ClientsTableRowData addRow() {
        return (ClientsTableRowData) super.addRow();
    }

    @Override
    public ClientsTableRowData addRow(int rowState) {
        return (ClientsTableRowData) super.addRow(rowState);
    }

    @Override
    public ClientsTableRowData createRow() {
        return new ClientsTableRowData();
    }

    @Override
    public Class<? extends AbstractTableRowData> getRowType() {
        return ClientsTableRowData.class;
    }

    @Override
    public ClientsTableRowData[] getRows() {
        return (ClientsTableRowData[]) super.getRows();
    }

    @Override
    public ClientsTableRowData rowAt(int index) {
        return (ClientsTableRowData) super.rowAt(index);
    }

    public void setRows(ClientsTableRowData[] rows) {
        super.setRows(rows);
    }

    public static class ClientsTableRowData extends AbstractTableRowData {
        private static final long serialVersionUID = 1L;
        public static final String client = "client";
        public static final String name = "name";
        public static final String primaryContact = "primaryContact";
        public static final String primaryEmail = "primaryEmail";
        public static final String phone = "phone";
        public static final String active = "active";
        public static final String groups = "groups";
        public static final String createdAt = "createdAt";
        private Client m_client;
        private String m_name;
        private String m_primaryContact;
        private String m_primaryEmail;
        private String m_phone;
        private Boolean m_active;
        private String m_groups;
        private Date m_createdAt;

        public Client getClient() {
            return m_client;
        }

        public void setClient(Client newClient) {
            m_client = newClient;
        }

        public String getName() {
            return m_name;
        }

        public void setName(String newName) {
            m_name = newName;
        }

        public String getPrimaryContact() {
            return m_primaryContact;
        }

        public void setPrimaryContact(String newPrimaryContact) {
            m_primaryContact = newPrimaryContact;
        }

        public String getPrimaryEmail() {
            return m_primaryEmail;
        }

        public void setPrimaryEmail(String newPrimaryEmail) {
            m_primaryEmail = newPrimaryEmail;
        }

        public String getPhone() {
            return m_phone;
        }

        public void setPhone(String newPhone) {
            m_phone = newPhone;
        }

        public Boolean getActive() {
            return m_active;
        }

        public void setActive(Boolean newActive) {
            m_active = newActive;
        }

        public String getGroups() {
            return m_groups;
        }

        public void setGroups(String newGroups) {
            m_groups = newGroups;
        }

        public Date getCreatedAt() {
            return m_createdAt;
        }

        public void setCreatedAt(Date newCreatedAt) {
            m_createdAt = newCreatedAt;
        }
    }
}
