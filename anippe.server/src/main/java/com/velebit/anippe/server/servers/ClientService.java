package com.velebit.anippe.server.servers;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.clients.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ClientService extends AbstractService implements IClientService {
    @Override
    public ClientFormData prepareCreate(ClientFormData formData) {
        return formData;
    }

    @Override
    public ClientFormData create(ClientFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO clients ");
        varname1.append("            (name, ");
        varname1.append("             address, ");
        varname1.append("             city, ");
        varname1.append("             postal_code, ");
        varname1.append("             country_id, ");
        varname1.append("             website, ");
        varname1.append("             organisation_id, ");
        varname1.append("             phone) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Address, ");
        varname1.append("             :City, ");
        varname1.append("             :PostalCode, ");
        varname1.append("             :Country, ");
        varname1.append("             :Website, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :Phone ) ");
        varname1.append("RETURNING id INTO :clientId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        emitModuleEvent(Client.class, new Client(), ChangeStatus.INSERTED);

        return formData;
    }

    @Override
    public ClientFormData load(ClientFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       address, ");
        varname1.append("       city, ");
        varname1.append("       postal_code, ");
        varname1.append("       country_id, ");
        varname1.append("       website, ");
        varname1.append("       phone ");
        varname1.append("FROM   clients ");
        varname1.append("WHERE  id = :clientId ");
        varname1.append("INTO   :Name, :Address, :City, :PostalCode, ");
        varname1.append(":Country, :Website, :Phone ");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public ClientFormData store(ClientFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("UPDATE clients ");
        varname1.append("SET    NAME = :Name, ");
        varname1.append("       address = :Address, ");
        varname1.append("       city = :City, ");
        varname1.append("       postal_code = :PostalCode, ");
        varname1.append("       country_id = :Country, ");
        varname1.append("       website = :Website, ");
        varname1.append("       phone = :Phone ");
        varname1.append("WHERE  id = :clientId");
        SQL.update(varname1.toString(), formData);

        emitModuleEvent(Client.class, new Client(), ChangeStatus.UPDATED);

        return formData;
    }

    @Override
    public void delete(Integer clientId) {
        SQL.update("UPDATE clients SET deleted_at = now() WHERE id = :clientId", new NVPair("clientId", clientId));

        emitModuleEvent(Client.class, new Client(), ChangeStatus.DELETED);
    }
}
