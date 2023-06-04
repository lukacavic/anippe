package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.servers.ClientDto;
import com.velebit.anippe.server.servers.ClientMap;
import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class LeadDao {

	public List<Lead> get(LeadRequest request) {
		BeanArrayHolder<LeadDto> dto = new BeanArrayHolder<LeadDto>(LeadDto.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT          l.id, ");
		varname1.append("                l.name ");
		varname1.append("FROM            leads l ");
		varname1.append("LEFT OUTER JOIN countries cr ");
		varname1.append("ON              cr.id = l.country_id ");
		varname1.append("WHERE           l.deleted_at IS NULL ");
		varname1.append("AND             l.organisation_id = :organisationId ");
		varname1.append("ORDER BY        l.NAME ");
		varname1.append("into            :{holder.id}, ");
		varname1.append("                :{holder.name} ");
		SQL.selectInto(varname1.toString(), new NVPair("holder", dto), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));
		List<LeadDto> dtos = CollectionUtility.arrayList(dto.getBeans());

		List<Lead> leads = CollectionUtility.emptyArrayList();

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.addMappings(new LeadMap());
		dtos.forEach(item -> leads.add(mapper.map(item, Lead.class)));

		return leads;
	}
}
