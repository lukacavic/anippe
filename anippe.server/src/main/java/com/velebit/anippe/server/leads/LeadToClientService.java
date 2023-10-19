package com.velebit.anippe.server.leads;

import com.velebit.anippe.shared.leads.ILeadToClientService;
import com.velebit.anippe.shared.leads.LeadToClientFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class LeadToClientService implements ILeadToClientService {
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
        varname1.append("INTO   :holder, :Position, :Email, :Company, ");
        varname1.append(":Phone, :Website, :Address, :City, ");
        varname1.append(":PostalCode, :Country");
        SQL.selectInto(varname1.toString(), formData, new NVPair("holder", holder));

        String[] fullName = holder.getValue().split(" ");
        if (fullName[0] != null) {
            formData.getFirstName().setValue(fullName[0]);
        }

        if (fullName[1] != null) {
            formData.getLastName().setValue(fullName[1]);
        }

        return formData;
    }

    @Override
    public LeadToClientFormData create(LeadToClientFormData formData) {
        //Kreiraj kompaniju po nazivu + adresa polja i ostalo

        //Kreiraj kontakt i vezi na kompaniju

        //Stavi kontakt da je primarni za kompaniju.

        //Prebaci sa lead-a sve napomene, privitke i preostale zadatke ako je označeno u formi.

        return formData;
    }

}
