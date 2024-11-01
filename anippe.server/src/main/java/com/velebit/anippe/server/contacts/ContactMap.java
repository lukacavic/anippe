package com.velebit.anippe.server.contacts;

import com.velebit.anippe.shared.clients.Contact;
import org.modelmapper.PropertyMap;

public class ContactMap extends PropertyMap<ContactDto, Contact> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
        map().setEmail(source.getEmail());
        map().setPhone(source.getPhone());
        map().setPosition(source.getPosition());
        map().setActive(source.isActive());
        map().setLastLoginAt(source.getLastLoginAt());
        map().setPrimaryContact(source.isPrimaryContact());
        map().getClient().setId(source.getClientId());
        map().getClient().setName(source.getClientName());
    }
}
