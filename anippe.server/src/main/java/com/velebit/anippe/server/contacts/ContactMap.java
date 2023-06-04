package com.velebit.anippe.server.contacts;

import com.velebit.anippe.shared.clients.Contact;
import org.modelmapper.PropertyMap;

public class ContactMap extends PropertyMap<ContactDto, Contact> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setEmail(source.getEmail());
        map().setPhone(source.getPhone());
        map().setPosition(source.getPosition());
        map().setActive(source.isActive());
        map().setLastLoginAt(source.getLastLoginAt());
    }
}
