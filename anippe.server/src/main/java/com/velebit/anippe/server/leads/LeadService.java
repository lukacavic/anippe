package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class LeadService extends AbstractService implements ILeadService {
    @Override
    public LeadFormData prepareCreate(LeadFormData formData) {
        return formData;
    }

    @Override
    public LeadFormData create(LeadFormData formData) {
        if (formData.getLeadId() != null) {
            return store(formData);
        }

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO leads ");
        varname1.append("            (name, ");
        varname1.append("             company, ");
        varname1.append("             position, ");
        varname1.append("             description, ");
        varname1.append("             address, ");
        varname1.append("             city, ");
        varname1.append("             postal_code, ");
        varname1.append("             country_id, ");
        varname1.append("             status_id, ");
        varname1.append("             source_id, ");
        varname1.append("             assigned_user_id, ");
        varname1.append("             email, ");
        varname1.append("             website, ");
        varname1.append("             phone, ");
        varname1.append("             created_at, ");
        varname1.append("             project_id, ");
        varname1.append("             last_contact_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Company, ");
        varname1.append("             :Position, ");
        varname1.append("             :Description, ");
        varname1.append("             :Address, ");
        varname1.append("             :City, ");
        varname1.append("             :PostalCode, ");
        varname1.append("             :Country, ");
        varname1.append("             :Status, ");
        varname1.append("             :Source, ");
        varname1.append("             :AssignedUser, ");
        varname1.append("             :Email, ");
        varname1.append("             :Website, ");
        varname1.append("             :Phone, ");
        varname1.append("             now(), ");
        varname1.append("             :projectId, ");
        varname1.append("             :LastContactAt, ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :leadId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        //Insert activity log
        BEANS.get(LeadDao.class).addActivityLog(formData.getLeadId(), TEXTS.get("CreatedLead"));

        return formData;
    }

    @Override
    public LeadFormData load(LeadFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       company, ");
        varname1.append("       position, ");
        varname1.append("       client_id, ");
        varname1.append("       lost, ");
        varname1.append("       description, ");
        varname1.append("       address, ");
        varname1.append("       city, ");
        varname1.append("       postal_code, ");
        varname1.append("       country_id, ");
        varname1.append("       status_id, ");
        varname1.append("       source_id, ");
        varname1.append("       assigned_user_id, ");
        varname1.append("       project_id, ");
        varname1.append("       project_id, ");
        varname1.append("       email, ");
        varname1.append("       website, ");
        varname1.append("       phone, ");
        varname1.append("       last_contact_at ");
        varname1.append("FROM   leads ");
        varname1.append("WHERE  id = :leadId ");
        varname1.append("INTO   :Name, ");
        varname1.append("       :Company, ");
        varname1.append("       :Position, ");
        varname1.append("       :clientId, ");
        varname1.append("       :lost, ");
        varname1.append("       :Description, ");
        varname1.append("       :Address, ");
        varname1.append("       :City, ");
        varname1.append("       :PostalCode, ");
        varname1.append("       :Country, ");
        varname1.append("       :Status, ");
        varname1.append("       :Source, ");
        varname1.append("       :AssignedUser, ");
        varname1.append("       :projectId, ");
        varname1.append("       :Project, ");
        varname1.append("       :Email, ");
        varname1.append("       :Website, ");
        varname1.append("       :Phone, ");
        varname1.append("       :LastContactAt ");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public LeadFormData store(LeadFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE leads ");
        varname1.append("SET name    = :Name, ");
        varname1.append("    company = :Company, ");
        varname1.append("    position = :Position, ");
        varname1.append("    description = :Description, ");
        varname1.append("    address = :Address, ");
        varname1.append("    city = :City, ");
        varname1.append("    postal_code = :PostalCode, ");
        varname1.append("    country_id = :Country, ");
        varname1.append("    status_id = :Status, ");
        varname1.append("    source_id = :Source, ");
        varname1.append("    assigned_user_id = :AssignedUser, ");
        varname1.append("    email = :Email, ");
        varname1.append("    website = :Website, ");
        varname1.append("    phone = :Phone, ");
        varname1.append("    last_contact_at = :LastContactAt ");
        varname1.append("WHERE id = :leadId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public Lead find(Integer leadId) {
        return BEANS.get(LeadDao.class).find(leadId);
    }

    @Override
    public boolean isEmailUnique(String email, Integer leadId) {
        IntegerHolder holder = new IntegerHolder();

        String stmt = "SELECT COUNT(*) FROM leads WHERE organisation_id = :organisationId AND deleted_at is null AND email = :email ";

        if (leadId != null) {
            stmt += " AND id != :leadId ";
        }
        stmt += " INTO :Holder ";

        SQL.selectInto(stmt,
                new NVPair("email", email),
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("leadId", leadId),
                new NVPair("Holder", holder)
        );

        return holder.getValue() != null && holder.getValue() > 0;
    }

    @Override
    public boolean isPhoneUnique(String phone, Integer leadId) {
        IntegerHolder holder = new IntegerHolder();

        String stmt = "SELECT COUNT(*) FROM leads WHERE organisation_id = :organisationId AND deleted_at is null AND phone = :phone ";

        if (leadId != null) {
            stmt += " AND id != :leadId ";
        }
        stmt += " INTO :Holder ";

        SQL.selectInto(stmt,
                new NVPair("phone", phone),
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("leadId", leadId),
                new NVPair("Holder", holder)
        );

        return holder.getValue() != null && holder.getValue() > 0;
    }

}
