package com.velebit.anippe.server.reminders;

import com.velebit.anippe.shared.reminders.Reminder;
import org.modelmapper.PropertyMap;

public class ReminderMap extends PropertyMap<ReminderDto, Reminder> {

	@Override
	protected void configure() {
		map().setId(source.getId());
		map().getUserCreated().setId(source.getUserCreatedId());
		map().getUserCreated().setFirstName(source.getUserCreatedFirstName());
		map().getUserCreated().setLastName(source.getUserCreatedLastName());

		map().setContent(source.getContent());
		map().setCompletedAt(source.getCompletedAt());
		map().setTitle(source.getTitle());
		map().setCreatedAt(source.getCreatedAt());
		map().setRemindAt(source.getRemindAt());

		map().getUser().setId(source.getUserId());
		map().getUser().setFirstName(source.getUserFirstName());
		map().getUser().setLastName(source.getUserLastName());

		map().setSystemGenerated(source.isSystemGenerated());
		map().setOrganisationId(source.getOrganisationId());
		map().setSendEmail(source.isSendEmail());
	}

}
