package com.velebit.anippe.client.gantt;

import java.util.EventListener;

public interface IGanttListener extends EventListener {

    void onItemClick(Integer itemId);
}
