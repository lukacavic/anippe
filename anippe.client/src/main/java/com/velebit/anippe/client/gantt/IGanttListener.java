package com.velebit.anippe.client.gantt;

import java.util.Date;
import java.util.EventListener;

public interface IGanttListener extends EventListener {

    void onItemClick(Integer itemId);

    void onItemDragged(Integer itemId, Date startAt, Date endAt);
}
