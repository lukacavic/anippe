package com.velebit.anippe.client.kanban;

import com.velebit.anippe.client.gantt.GanttItem;
import com.velebit.anippe.client.gantt.IGanttFieldUIFacade;
import com.velebit.anippe.client.gantt.IGanttListener;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.platform.util.event.IFastListenerList;

import java.util.Collection;

public interface IKanbanField extends IFormField {

    String PROP_ITEMS = "items";

    IKanbanFieldUIFacade getUIFacade();

    IFastListenerList<IKanbanListener> kanbanListeners();

    default void addKanbanListener(IKanbanListener m_listener) {
        kanbanListeners().add(m_listener);
    }

    default void removeKanbanListener(IKanbanListener listener) {
        kanbanListeners().remove(listener);
    }

    public Collection<GanttItem> getItems();

    public void setItems(Collection<GanttItem> items);

}
