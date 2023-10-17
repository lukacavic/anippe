package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.gantt.GanttItem;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Collection;
import java.util.Date;

public class GanttTask implements GanttItem {
    private String id;
    private Date startDate;
    private Date endDate;
    private Integer progress;
    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Integer getProgress() {
        return progress;
    }

    @Override
    public Collection<GanttItem> getDependencies() {
        return CollectionUtility.emptyArrayList();
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

}
