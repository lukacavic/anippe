package com.velebit.anippe.server.contacts;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.contacts.ContactRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class ContactDao {

    public List<Contact> get(ContactRequest request) {
        BeanArrayHolder<ContactDto> dto = new BeanArrayHolder<ContactDto>(ContactDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   c.id, ");
        varname1.append("         c.first_name, ");
        varname1.append("         c.last_name, ");
        varname1.append("         c.email, ");
        varname1.append("         c.phone, ");
        varname1.append("         c.position, ");
        varname1.append("         c.active, ");
        varname1.append("         c.last_login_at ");
        varname1.append("FROM     contacts c ");
        varname1.append("WHERE    c.deleted_at IS NULL ");

        if (request.getClientId() != null) {
            varname1.append(" AND c.client_id = :{request.clientId} ");
        }

        varname1.append("ORDER BY c.first_name, ");
        varname1.append("         c.last_name ");
        varname1.append("into     :{holder.id}, ");
        varname1.append("         :{holder.firstName}, ");
        varname1.append("         :{holder.lastName}, ");
        varname1.append("         :{holder.email}, ");
        varname1.append("         :{holder.phone}, ");
        varname1.append("         :{holder.position}, ");
        varname1.append("         :{holder.active}, ");
        varname1.append("         :{holder.lastLoginAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("dto", dto),
                new NVPair("request", request),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("holder", dto)
        );

        List<ContactDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Contact> contacts = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new ContactMap());
        dtos.forEach(item -> contacts.add(mapper.map(item, Contact.class)));

        return contacts;
    }
}
