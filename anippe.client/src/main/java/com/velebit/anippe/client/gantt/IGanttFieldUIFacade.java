package com.velebit.anippe.client.gantt;

import java.util.Date;

public interface IGanttFieldUIFacade {

    void handleItemClick(Integer itemId);

    void handleItemDragged(Integer itemId, Date startAt, Date endAt);
}
