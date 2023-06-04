package com.velebit.anippe.server.settings.servergroups;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.settings.sharedgroups.ClientGroupsTablePageData;
import com.velebit.anippe.shared.settings.sharedgroups.IClientGroupsService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class ClientGroupsService extends AbstractService implements IClientGroupsService {
    @Override
    public ClientGroupsTablePageData getClientGroupsTableData(SearchFilter filter) {
        ClientGroupsTablePageData pageData = new ClientGroupsTablePageData();

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT id, ");
        varname1.append("       name ");
        varname1.append("FROM   client_groups ");
        varname1.append("WHERE  organisation_id = :organisationId ");
        varname1.append("       AND deleted_at IS NULL ");
        varname1.append("INTO   :ClientGroupId, :Name");
        SQL.selectInto(varname1.toString(), pageData, new NVPair("organisationId", getCurrentOrganisationId()));

        return pageData;
    }

    @Override
    public void delete(Integer clientGroupId) {
        SQL.update("UPDATE client_groups SET deleted_at = now() WHERE id = :clientGroupId", new NVPair("clientGroupId", clientGroupId));
    }
}
