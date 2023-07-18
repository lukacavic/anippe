/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.velebit.anippe.server.email;

import com.velebit.anippe.shared.email.Email;
import org.modelmapper.PropertyMap;

public class EmailMap extends PropertyMap<EmailDto, Email> {

    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setCreatedAt(source.getCreatedAt());
        map().getSender().setId(source.getUserId());
        map().getSender().setFirstName(source.getUserFirstName());
        map().getSender().setLastName(source.getUserLastName());
        map().setAttachmentsCount(source.getAttachmentsCount());
        map().setSubject(source.getSubject());
        map().setContent(source.getMessage());
        map().setReceivers(source.getReceivers());
        map().setCcReceivers(source.getCcReceivers());
        map().setBccReceivers(source.getBccReceivers());
        map().setStatusId(source.getStatusId());
    }

}
