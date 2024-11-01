package com.velebit.anippe.server.servers;

import com.velebit.anippe.shared.clients.Client;
import org.modelmapper.PropertyMap;

public class ClientMap extends PropertyMap<ClientDto, Client> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
        map().setAddress(source.getAddress());
        map().setCity(source.getCity());
        map().setWebsite(source.getWebsite());
        map().setCreatedAt(source.getCreatedAt());
        map().setPhone(source.getPhone());
        map().setActive(source.isActive());
        map().getCountry().setId(source.getCountryId());
        map().getCountry().setName(source.getCountryName());
        map().getPrimaryContact().setId(source.getPrimaryContactId());
        map().getPrimaryContact().setName(source.getPrimaryContactName());
        map().getPrimaryContact().setEmail(source.getPrimaryContactEmail());
        map().getPrimaryContact().setPhone(source.getPrimaryContactPhone());
        map().setPostalCode(source.getPostalCode());
    }
}
