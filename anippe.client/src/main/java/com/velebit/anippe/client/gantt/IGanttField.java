package com.velebit.anippe.client.gantt;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.platform.util.event.IFastListenerList;

import java.util.Collection;

public interface IGanttField extends IFormField {

	String PROP_ITEMS = "items";
	String PROP_VIEW_MODE = "viewMode";

	IGanttFieldUIFacade getUIFacade();

	IFastListenerList<IGanttListener> ganttListeners();

	default void addGanttListener(IGanttListener m_listener) {
		ganttListeners().add(m_listener);
	}

	default void removeGanttListener(IGanttListener listener) {
		ganttListeners().remove(listener);
	}

	public Collection<GanttItem> getItems();

	public void setItems(Collection<GanttItem> items);

	public String getViewMode();

	public void setViewMode(String viewMode);

}
