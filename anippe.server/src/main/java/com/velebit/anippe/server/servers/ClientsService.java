package com.velebit.anippe.server.servers;

import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.clients.ClientRequest;
import com.velebit.anippe.shared.clients.ClientsTablePageData;
import com.velebit.anippe.shared.clients.IClientsService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class ClientsService implements IClientsService {
    @Override
    public ClientsTablePageData getClientsTableData(SearchFilter filter) {
        ClientsTablePageData pageData = new ClientsTablePageData();

        ClientRequest request = new ClientRequest();

        List<Client> clients = BEANS.get(ClientDao.class).get(request);

        if (CollectionUtility.isEmpty(clients)) return pageData;

        for (Client client : clients) {
            ClientsTablePageData.ClientsTableRowData row = pageData.addRow();
            row.setClient(client);
            row.setName(client.getName());
            row.setActive(client.isActive());
            row.setCreatedAt(client.getCreatedAt());
            row.setPrimaryEmail(client.getPrimaryContact() != null ? client.getPrimaryContact().getEmail() : null);
            row.setPrimaryContact(client.getPrimaryContact() != null ? client.getPrimaryContact().getFullName() : null);
            row.setCreatedAt(client.getCreatedAt());
        }

        return pageData;
    }

    @Override
    public void delete(Integer clientId) {
        SQL.update("UPDATE clients SET deleted_at = now() WHERE id = :clientId", new NVPair("clientId", clientId));
    }

    @Override
    public void toggleActivated(Integer clientId, Boolean active) {
        String stmt = "UPDATE clients SET active = :active WHERE id = :clientId";
        SQL.update(stmt, new NVPair("active", active), new NVPair("clientId", clientId));
    }
}
