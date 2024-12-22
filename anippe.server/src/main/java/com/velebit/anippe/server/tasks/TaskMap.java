package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.Task;
import org.modelmapper.PropertyMap;

public class TaskMap extends PropertyMap<TaskDto, Task> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setTitle(source.getName());
        map().setDescription(source.getDescription());
        map().getCreator().setId(source.getUserCreatedId());
        map().getCreator().setFirstName(source.getUserCreatedFirstName());
        map().getCreator().setLastName(source.getUserCreatedLastName());
        map().setStartAt(source.getStartAt());
        map().setDeadlineAt(source.getDeadlineAt());
        map().setStatusId(source.getStatusId());
        map().setPriorityId(source.getPriorityId());
        map().setArchivedAt(source.getArchivedAt());
        map().setAttachmentsCount(source.getAttachmentsCount());
        map().setProjectId(source.getProjectId());
    }
}
