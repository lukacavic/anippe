package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.leads.ILeadToClientService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadToClientFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class LeadToClientService extends AbstractService implements ILeadToClientService {
    @Override
    public LeadToClientFormData prepareCreate(LeadToClientFormData formData) {
        StringHolder holder = new StringHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       position, ");
        varname1.append("       email, ");
        varname1.append("       company, ");
        varname1.append("       phone, ");
        varname1.append("       website, ");
        varname1.append("       address, ");
        varname1.append("       city, ");
        varname1.append("       postal_code, ");
        varname1.append("       country_id ");
        varname1.append("FROM   leads ");
        varname1.append("WHERE  id = :leadId ");
        varname1.append("INTO   :Name, :Position, :Email, :Company, ");
        varname1.append(":Phone, :Website, :Address, :City, ");
        varname1.append(":PostalCode, :Country");
        SQL.selectInto(varname1.toString(), formData, new NVPair("holder", holder));

        return formData;
    }

    private void markContactAsPrimaryForClient(Integer clientId, Integer contactId) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE clients ");
        varname1.append("SET    primary_contact_id = :contactId ");
        varname1.append("WHERE  id = :clientId");
        SQL.update(varname1.toString(), new NVPair("clientId", clientId), new NVPair("contactId", contactId));
    }

    private void addClientIdToLead(Integer clientId, Integer leadId) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE leads ");
        varname1.append("SET    client_id = :clientId ");
        varname1.append("WHERE  id = :leadId");
        SQL.update(varname1.toString(), new NVPair("clientId", clientId), new NVPair("leadId", leadId));
    }

    @Override
    public LeadToClientFormData create(LeadToClientFormData formData) {
        Integer clientId = createClient(formData);

        Integer contactId = createContact(formData, clientId);

        markContactAsPrimaryForClient(clientId, contactId);

        addClientIdToLead(clientId, formData.getLeadId());

        assignCreatedStatusToLead(formData.getLeadId());

        if (formData.getTransferNotesToClient().getValue()) {
            transferNotesToClient(formData.getLeadId(), clientId);
        }

        if (formData.getTransferAttachmentsToClient().getValue()) {
            transferAttachmentsToClient(formData.getLeadId(), clientId);
        }

        //Add activity log for lead.
        BEANS.get(LeadDao.class).addActivityLog(formData.getLeadId(), TEXTS.get("ConvertedToClient"));

        return formData;
    }

    private void transferAttachmentsToClient(Integer leadId, Integer clientId) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE attachments ");
        varname1.append("SET    related_id = :clientId, ");
        varname1.append("       related_type = :relatedClient ");
        varname1.append("WHERE  related_id = :leadId ");
        varname1.append("       AND related_type = :relatedLead");
        SQL.update(varname1.toString(),
                new NVPair("clientId", clientId),
                new NVPair("leadId", leadId),
                new NVPair("relatedClient", Related.CLIENT),
                new NVPair("relatedLead", Related.LEAD)
        );
    }

    private void transferNotesToClient(Integer leadId, Integer clientId) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE notes ");
        varname1.append("SET    related_id = :clientId, ");
        varname1.append("       related_type = :relatedClient ");
        varname1.append("WHERE  related_id = :leadId ");
        varname1.append("       AND related_type = :relatedLead");
        SQL.update(varname1.toString(),
                new NVPair("clientId", clientId),
                new NVPair("leadId", leadId),
                new NVPair("relatedClient", Related.CLIENT),
                new NVPair("relatedLead", Related.LEAD)
        );
    }

    private void assignCreatedStatusToLead(Integer leadId) {
        Lead lead = BEANS.get(LeadDao.class).find(leadId);

        Integer statusId = BEANS.get(LeadDao.class).findStatusConverted(lead);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE leads ");
        varname1.append("SET    status_id = :statusId ");
        varname1.append("WHERE  id = :leadId");
        SQL.update(varname1.toString(), new NVPair("statusId", statusId), new NVPair("leadId", leadId));
    }

    private Integer createContact(LeadToClientFormData formData, Integer clientId) {
        IntegerHolder holder = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO contacts ");
        varname1.append("            (name, ");
        varname1.append("             client_id, ");
        varname1.append("             active, ");
        varname1.append("             email, ");
        varname1.append("             phone, ");
        varname1.append("             position, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :newClientId, ");
        varname1.append("             true, ");
        varname1.append("             :Email, ");
        varname1.append("             :Phone, ");
        varname1.append("             :Position, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) RETURNING id INTO :holder");
        SQL.selectInto(varname1.toString(), formData,
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("holder", holder),
                new NVPair("newClientId", clientId));

        return holder.getValue();
    }

    private Integer createClient(LeadToClientFormData formData) {
        IntegerHolder holder = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO clients ");
        varname1.append("            (name, ");
        varname1.append("             address, ");
        varname1.append("             city, ");
        varname1.append("             postal_code, ");
        varname1.append("             country_id, ");
        varname1.append("             website, ");
        varname1.append("             phone, ");
        varname1.append("             active, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Company, ");
        varname1.append("             :Address, ");
        varname1.append("             :City, ");
        varname1.append("             :PostalCode, ");
        varname1.append("             :Country, ");
        varname1.append("             :Website, ");
        varname1.append("             :Phone, ");
        varname1.append("             true, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :holder");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("holder", holder));

        return holder.getValue();
    }

}
