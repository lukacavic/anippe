package com.velebit.anippe.server.settings.servergroups;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.settings.sharedgroups.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ClientGroupService extends AbstractService implements IClientGroupService {
    @Override
    public ClientGroupFormData prepareCreate(ClientGroupFormData formData) {
        return formData;
    }

    @Override
    public ClientGroupFormData create(ClientGroupFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO client_groups ");
        varname1.append("            (NAME, ");
        varname1.append("             organisation_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :organisationId, ");
        varname1.append("             Now())");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()));

        return formData;
    }

    @Override
    public ClientGroupFormData load(ClientGroupFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT name ");
        varname1.append("FROM client_groups ");
        varname1.append("WHERE id = :clientGroupId ");
        varname1.append("INTO :Name");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public ClientGroupFormData store(ClientGroupFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("UPDATE client_groups ");
        varname1.append("SET    NAME = :Name, ");
        varname1.append("       updated_at = Now() ");
        varname1.append("WHERE  id = :clientGroupId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
