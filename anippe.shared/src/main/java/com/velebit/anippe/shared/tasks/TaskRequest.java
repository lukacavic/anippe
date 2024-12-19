package com.velebit.anippe.shared.tasks;

import com.velebit.anippe.shared.AbstractRequest;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class TaskRequest extends AbstractRequest {

    private List<Integer> statusIds = CollectionUtility.emptyArrayList();
    private List<Integer> priorityIds = CollectionUtility.emptyArrayList();

    public List<Integer> getPriorityIds() {
        return priorityIds;
    }

    public void setPriorityIds(List<Integer> priorityIds) {
        this.priorityIds = priorityIds;
    }

    public List<Integer> getStatusIds() {
        return statusIds;
    }

    public void setStatusIds(List<Integer> statusIds) {
        this.statusIds = statusIds;
    }
}
