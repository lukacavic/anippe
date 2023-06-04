package com.velebit.anippe.server.leads;

import com.velebit.anippe.shared.leads.Lead;
import org.modelmapper.PropertyMap;

public class LeadMap extends PropertyMap<LeadDto, Lead> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
        map().setWebsite(source.getWebsite());
        map().setPhone(source.getPhone());
        map().setEmail(source.getEmail());
        map().getStatus().setId(source.getStatusId());
        map().getStatus().setName(source.getStatusName());
        map().getSource().setId(source.getSourceId());
        map().getSource().setName(source.getSourceName());
        map().setCompany(source.getCompany());
        map().setDescription(source.getDescription());
        map().getCountry().setId(source.getCountryId());
        map().getCountry().setName(source.getCountryName());
    }
}
