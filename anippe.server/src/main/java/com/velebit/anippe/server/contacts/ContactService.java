package com.velebit.anippe.server.contacts;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.servers.ClientDao;
import com.velebit.anippe.shared.contacts.*;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ContactService extends AbstractService implements IContactService {
    @Override
    public ContactFormData prepareCreate(ContactFormData formData) {
        return formData;
    }

    @Override
    public ContactFormData create(ContactFormData formData) {
        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO contacts ");
        varname1.append("            (first_name, ");
        varname1.append("             last_name, ");
        varname1.append("             client_id, ");
        varname1.append("             email, ");
        varname1.append("             phone, ");
        varname1.append("             active, ");
        varname1.append("             position, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:FirstName, ");
        varname1.append("             :LastName, ");
        varname1.append("             :clientId, ");
        varname1.append("             :Email, ");
        varname1.append("             :Phone, ");
        varname1.append("             :Active, ");
        varname1.append("             :Position, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :contactId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        if(formData.getPrimaryContact().getValue()) {
            BEANS.get(ClientDao.class).updatePrimaryContact(formData.getContactId(), formData.getClientId());
        }

        return formData;
    }

    @Override
    public ContactFormData load(ContactFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT first_name, ");
        varname1.append("       last_name, ");
        varname1.append("       client_id, ");
        varname1.append("       email, ");
        varname1.append("       phone, ");
        varname1.append("       active, ");
        varname1.append("       position ");
        varname1.append("FROM   contacts ");
        varname1.append("WHERE  id = :contactId ");
        varname1.append("into   :FirstName, ");
        varname1.append("       :LastName, ");
        varname1.append("       :clientId, ");
        varname1.append("       :Email, ");
        varname1.append("       :Phone, ");
        varname1.append("       :Active, ");
        varname1.append("       :Position");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public ContactFormData store(ContactFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("UPDATE contacts ");
        varname1.append("SET    first_name = :FirstName, ");
        varname1.append("       last_name = :LastName, ");
        varname1.append("       position = :Position, ");
        varname1.append("       email = :Email, ");
        varname1.append("       active = :Active, ");
        varname1.append("       phone = :Phone, ");
        varname1.append("       updated_at = Now() ");
        varname1.append("WHERE  id = :contactId");
        SQL.update(varname1.toString(), formData);

        if(formData.getPrimaryContact().getValue()) {
            BEANS.get(ClientDao.class).updatePrimaryContact(formData.getContactId(), formData.getClientId());
        }

        return formData;
    }

    @Override
    public void delete(Integer contactId) {
        String stmt = "UPDATE contacts SET deleted_at = now() WHERE id = :contactId";
        SQL.update(stmt, new NVPair("contactId", contactId));
    }

    @Override
    public void toggleActivated(Integer contactId, Boolean active) {
        String stmt = "UPDATE contacts SET active = :active WHERE id = :contactId";
        SQL.update(stmt, new NVPair("active", active), new NVPair("contactId", contactId));
    }

    @Override
    public boolean isEmailUnique(String email, Integer contactId) {
        IntegerHolder holder = new IntegerHolder();

        String stmt = "SELECT COUNT(*) FROM contacts WHERE organisation_id = :organisationId AND deleted_at is null AND email = :email ";

        if (contactId != null) {
            stmt += " AND id != :contactId ";
        }
        stmt += " INTO :holder ";

        SQL.selectInto(stmt, new NVPair("email", email), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("contactId", contactId), new NVPair("holder", holder));

        return holder.getValue() != null && holder.getValue() > 0;
    }
}
