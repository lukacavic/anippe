package com.velebit.anippe.server.settings.users;

import com.velebit.anippe.shared.beans.User;
import org.modelmapper.PropertyMap;

public class UserMap extends PropertyMap<UserDto, User> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setUsername(source.getUsername());
        map().setEmail(source.getEmail());
        map().setActive(source.isActive());
        map().setLastLoginAt(source.getLastLoginAt());
        map().setCreatedAt(source.getCreatedAt());
        map().setAdministrator(source.isAdministrator());
    }
}
