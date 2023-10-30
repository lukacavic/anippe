package com.velebit.anippe.client.gantt;

import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.event.FastListenerList;
import org.eclipse.scout.rt.platform.util.event.IFastListenerList;

import java.util.*;

public abstract class AbstractGanttField extends AbstractFormField implements IGanttField {

	private IGanttFieldUIFacade m_uiFacade;

	private final FastListenerList<IGanttListener> m_listenerList = new FastListenerList<>();

	@Override
	protected void initConfig() {
		m_uiFacade = BEANS.get(ModelContextProxy.class).newProxy(new P_UIFacade(), ModelContext.copyCurrent());
		super.initConfig();
		setItems(Collections.<GanttItem>emptyList());
		setViewMode("Month");
	}

	@Override
	public IGanttFieldUIFacade getUIFacade() {
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

	@Override
	public String getViewMode() {
		return (String) getProperty(PROP_VIEW_MODE);
	}

	@Override
	public void setViewMode(String viewMode) {
		setProperty(PROP_VIEW_MODE, viewMode);
	}

	protected class P_UIFacade implements IGanttFieldUIFacade {

		@Override
		public void handleItemClick(Integer itemId) {
			ganttListeners().list().forEach(l -> l.onItemClick(itemId));
		}

		@Override
		public void handleItemDragged(Integer itemId, Date startAt, Date endAt) {
			ganttListeners().list().forEach(l -> l.onItemDragged(itemId, startAt, endAt));
		}
	}

	@Override
	public IFastListenerList<IGanttListener> ganttListeners() {
		return m_listenerList;
	}

}
