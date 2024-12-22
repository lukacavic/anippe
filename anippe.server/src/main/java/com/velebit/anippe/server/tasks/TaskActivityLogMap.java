package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.TaskActivityLog;
import org.modelmapper.PropertyMap;

public class TaskActivityLogMap extends PropertyMap<TaskActivityLogDto, TaskActivityLog> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setContent(source.getContent());
        map().setCreatedAt(source.getCreatedAt());
        map().getUser().setId(source.getUserCreatedId());
        map().getUser().setFirstName(source.getUserCreatedFirstName());
        map().getUser().setLastName(source.getUserCreatedLastName());
        map().setTaskId(source.getTaskId());
    }
}
