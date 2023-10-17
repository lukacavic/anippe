package com.velebit.anippe.client.gantt;

import java.util.Collection;
import java.util.Date;

public interface GanttItem {

    public String getId();

    public Date getStartDate();

    public Date getEndDate();

    public String getTitle();

    public Integer getProgress();

    public Collection<GanttItem> getDependencies();
}
