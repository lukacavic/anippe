package com.velebit.anippe.client.kanban;

import com.velebit.anippe.client.gantt.GanttItem;
import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.event.FastListenerList;
import org.eclipse.scout.rt.platform.util.event.IFastListenerList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractKanbanField extends AbstractFormField implements IKanbanField {

    private IKanbanFieldUIFacade m_uiFacade;

    private final FastListenerList<IKanbanListener> m_listenerList = new FastListenerList<>();

    @Override
    protected void initConfig() {
        m_uiFacade = BEANS.get(ModelContextProxy.class).newProxy(new AbstractKanbanField.P_UIFacade(), ModelContext.copyCurrent());
        super.initConfig();
        setItems(Collections.<GanttItem>emptyList());
    }

    @Override
    public IKanbanFieldUIFacade getUIFacade() {
        return m_uiFacade;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GanttItem> getItems() {
        return Collections.unmodifiableList((List<GanttItem>) getProperty(PROP_ITEMS));
    }

    @Override
    public void setItems(Collection<GanttItem> items) {
        setProperty(PROP_ITEMS, new ArrayList<>(items));
    }

    protected class P_UIFacade implements IKanbanFieldUIFacade {

    }

    @Override
    public IFastListenerList<IKanbanListener> kanbanListeners() {
        return m_listenerList;
    }

}
