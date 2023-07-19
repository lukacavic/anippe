package com.velebit.anippe.server.servers;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.tickets.TicketDto;
import com.velebit.anippe.server.tickets.TicketMap;
import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.clients.ClientRequest;
import com.velebit.anippe.shared.tickets.Ticket;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class ClientDao {

    public List<Client> get(ClientRequest request) {
        BeanArrayHolder<ClientDto> dto = new BeanArrayHolder<ClientDto>(ClientDto.class);

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT          c.id, ");
        varname1.append("                c.NAME, ");
        varname1.append("                c.address, ");
        varname1.append("                c.city, ");
        varname1.append("                c.postal_code, ");
        varname1.append("                cr.id, ");
        varname1.append("                cr.NAME, ");
        varname1.append("                c.phone, ");
        varname1.append("                c.website, ");
        varname1.append("                c.active, ");
        varname1.append("                prc.id, ");
        varname1.append("                prc.first_name, ");
        varname1.append("                prc.last_name, ");
        varname1.append("                prc.email, ");
        varname1.append("                prc.phone, ");
        varname1.append("                c.created_at ");
        varname1.append("FROM            clients c ");
        varname1.append("LEFT OUTER JOIN countries cr ");
        varname1.append("ON              cr.id = c.country_id ");
        varname1.append("LEFT OUTER JOIN contacts prc ");
        varname1.append("ON              prc.id = c.primary_contact_id ");
        varname1.append("WHERE           c.deleted_at IS NULL ");
        varname1.append("AND             c.organisation_id = :organisationId ");
        varname1.append("ORDER BY        c.NAME ");
        varname1.append("into            :{holder.id}, ");
        varname1.append("                :{holder.name}, ");
        varname1.append("                :{holder.address}, ");
        varname1.append("                :{holder.city}, ");
        varname1.append("                :{holder.postalCode}, ");
        varname1.append("                :{holder.countryId}, ");
        varname1.append("                :{holder.countryName}, ");
        varname1.append("                :{holder.phone}, ");
        varname1.append("                :{holder.website}, ");
        varname1.append("                :{holder.active}, ");
        varname1.append("                :{holder.primaryContactId}, ");
        varname1.append("                :{holder.primaryContactFirstName}, ");
        varname1.append("                :{holder.primaryContactLastName}, ");
        varname1.append("                :{holder.primaryContactEmail}, ");
        varname1.append("                :{holder.primaryContactPhone}, ");
        varname1.append("                :{holder.createdAt} ");

        SQL.selectInto(varname1.toString(), new NVPair("holder", dto), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));
        List<ClientDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Client> clients = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.addMappings(new ClientMap());
        dtos.forEach(item -> clients.add(mapper.map(item, Client.class)));

        return clients;
    }

    public Client find(Integer clientId) {
        ClientDto item = new ClientDto();

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT          c.id, ");
        varname1.append("                c.NAME, ");
        varname1.append("                c.address, ");
        varname1.append("                c.city, ");
        varname1.append("                c.postal_code, ");
        varname1.append("                cr.id, ");
        varname1.append("                cr.NAME, ");
        varname1.append("                c.phone, ");
        varname1.append("                c.website, ");
        varname1.append("                c.active, ");
        varname1.append("                prc.id, ");
        varname1.append("                prc.first_name, ");
        varname1.append("                prc.last_name, ");
        varname1.append("                prc.email, ");
        varname1.append("                prc.phone, ");
        varname1.append("                c.created_at ");
        varname1.append("FROM            clients c ");
        varname1.append("LEFT OUTER JOIN countries cr ");
        varname1.append("ON              cr.id = c.country_id ");
        varname1.append("LEFT OUTER JOIN contacts prc ");
        varname1.append("ON              prc.id = c.primary_contact_id ");
        varname1.append("WHERE           c.deleted_at IS NULL ");
        varname1.append("AND             c.id = :clientId ");
        varname1.append("INTO            :{holder.id}, ");
        varname1.append("                :{holder.name}, ");
        varname1.append("                :{holder.address}, ");
        varname1.append("                :{holder.city}, ");
        varname1.append("                :{holder.postalCode}, ");
        varname1.append("                :{holder.countryId}, ");
        varname1.append("                :{holder.countryName}, ");
        varname1.append("                :{holder.phone}, ");
        varname1.append("                :{holder.website}, ");
        varname1.append("                :{holder.active}, ");
        varname1.append("                :{holder.primaryContactId}, ");
        varname1.append("                :{holder.primaryContactFirstName}, ");
        varname1.append("                :{holder.primaryContactLastName}, ");
        varname1.append("                :{holder.primaryContactEmail}, ");
        varname1.append("                :{holder.primaryContactPhone}, ");
        varname1.append("                :{holder.createdAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("holder", item), new NVPair("clientId", clientId));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.addMappings(new ClientMap());

        return mapper.map(item, Client.class);
    }
}
