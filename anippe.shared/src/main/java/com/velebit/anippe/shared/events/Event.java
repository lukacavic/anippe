package com.velebit.anippe.shared.events;

import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.constants.Constants.EventType;
import com.velebit.anippe.shared.icons.FontIcons;

import java.util.Date;

public class Event implements java.io.Serializable {

    private Integer id;
    private String name;
    private String description;
    private Date startAt;
    private Date endsAt;
    private boolean publicEvent;
    private Long typeId;
    private boolean fullDay;
    private User user = new User();

    public boolean isFullDay() {
        return fullDay;
    }

    public void setFullDay(boolean fullDay) {
        this.fullDay = fullDay;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String calculateCssClass() {
        if (this.typeId == null) return null;

        if (typeId.equals(EventType.FREE_DAY)) {
            return "calendar-type-free-day";
        } else if (typeId.equals(EventType.MEETING)) {
            return "calendar-type-appointment";
        } else if (typeId.equals(EventType.VACATION)) {
            return "calendar-type-vacation";
        }

        return null;
    }

    public String calculateIcon() {
        if (this.typeId == null) return null;

        if (typeId.equals(EventType.FREE_DAY)) {
            return FontIcons.UserMinus;
        } else if (typeId.equals(EventType.MEETING)) {
            return FontIcons.Calendar;
        } else if (typeId.equals(EventType.VACATION)) {
            return FontIcons.UserMinus;
        }

        return null;
    }
}
