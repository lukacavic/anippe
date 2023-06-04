package com.velebit.anippe.server.organisations;

import com.velebit.anippe.shared.organisations.Organisation;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class OrganisationDao {
	public Organisation findById(Integer organisationId) {
		return all().stream().filter(o -> o.getId().equals(organisationId)).findFirst().orElse(null);
	}

	public List<Organisation> all() {
		BeanArrayHolder<OrganisationDto> rows = new BeanArrayHolder<>(OrganisationDto.class);

		List<Organisation> organisations = new ArrayList<>();

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT   o.id, ");
		varname1.append("         o.name, ");
		varname1.append("         o.subdomain, ");
		varname1.append("         o.created_at, ");
		varname1.append("         o.email, ");
		varname1.append("         o.phone, ");
		varname1.append("         o.website, ");
		varname1.append("         o.street, ");
		varname1.append("         o.city, ");
		varname1.append("         o.postal_code, ");
		varname1.append("         c.id, ");
		varname1.append("         c.name ");
		varname1.append("FROM     organisations o, ");
		varname1.append("         countries c ");
		varname1.append("WHERE    o.country_id = c.id ");
		varname1.append("AND      o.deleted_at IS NULL ");
		varname1.append("ORDER BY o.created_at, ");
		varname1.append("         o.name ");
		varname1.append("into     :{rows.id}, ");
		varname1.append("         :{rows.name}, ");
		varname1.append("         :{rows.subdomain}, ");
		varname1.append("         :{rows.createdAt}, ");
		varname1.append("         :{rows.email}, ");
		varname1.append("         :{rows.phone}, ");
		varname1.append("         :{rows.website},");
		varname1.append("         :{rows.street},");
		varname1.append("         :{rows.city},");
		varname1.append("         :{rows.postalCode},");
		varname1.append("         :{rows.countryId},");
		varname1.append("         :{rows.countryName}");
		SQL.selectInto(varname1.toString(), new NVPair("rows", rows));

		List<OrganisationDto> dtos = Arrays.asList(rows.getBeans());

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.addMappings(new OrganisationMap());

		dtos.forEach(item -> organisations.add(modelMapper.map(item, Organisation.class)));

		return organisations;
	}
}
