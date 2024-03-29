package com.velebit.anippe.ui.html;

import com.velebit.anippe.client.gantt.GanttItem;
import com.velebit.anippe.client.gantt.IGanttField;
import com.velebit.anippe.client.gantt.IGanttListener;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonDate;
import org.eclipse.scout.rt.ui.html.json.JsonEvent;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;

public class JsonGanttField extends JsonFormField<IGanttField> {

	private static final String EVENT_ITEM_CLICKED = "itemClicked";
	private static final String EVENT_ITEM_DRAG = "itemDragged";

	public JsonGanttField(IGanttField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
		super(model, uiSession, id, parent);
	}

	@Override
	public String getObjectType() {
		return "anippe.GanttField";
	}

	@Override
	protected void initJsonProperties(IGanttField model) {
		super.initJsonProperties(model);

		putJsonProperty(new JsonProperty<IGanttField>(IGanttField.PROP_ITEMS, model) {
			@Override
			protected Collection<GanttItem> modelValue() {
				return getModel().getItems();
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object prepareValueForToJson(Object value) {
				return itemsToJson((Collection<GanttItem>) value);
			}

		});

		putJsonProperty(new JsonProperty<IGanttField>(IGanttField.PROP_VIEW_MODE, model) {
			@Override
			protected String modelValue() {
				return getModel().getViewMode();
			}
		});
	}

	private JSONArray itemsToJson(Collection<GanttItem> items) {
		if (items == null) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();
		for (GanttItem item : items) {
			jsonArray.put(ganttItemToJson(item));
		}

		return jsonArray;
	}

	@Override
	public void handleUiEvent(JsonEvent event) {
		if (EVENT_ITEM_CLICKED.equals(event.getType())) {
			handleEventClick(event);
		} else if (EVENT_ITEM_DRAG.equals(event.getType())) {
			handleEventDrag(event);
		} else {
			super.handleUiEvent(event);
		}
	}

	protected Date toJavaDate(JSONObject data, String propertyName) {
		if (data == null || propertyName == null) {
			return null;
		}
		return new JsonDate(data.optString(propertyName, null)).asJavaDate();
	}

	private void handleEventClick(JsonEvent event) {
		JSONObject data = event.getData();
		getModel().getUIFacade().handleItemClick(data.optInt("itemId"));
	}

	private void handleEventDrag(JsonEvent event) {
		JSONObject data = event.getData();

		Integer itemId = data.optInt("itemId");
		Date startAt = toJavaDate(data, "startAt");
		Date endAt = toJavaDate(data, "endAt");

		getModel().getUIFacade().handleItemDragged(itemId, startAt, endAt);
	}

	private JSONObject ganttItemToJson(GanttItem item) {
		if (item == null) {
			return null;
		}
		JSONObject json = new JSONObject();
		json.put("id", item.getId());
		json.put("name", item.getTitle());
		json.put("progress", item.getProgress());
		json.put("start", DateUtility.format(item.getStartDate(), "yyyy-MM-dd"));
		json.put("end", DateUtility.format(item.getEndDate(), "yyyy-MM-dd"));
		json.put("dependencies", item.getDependencies());

		return json;
	}

	@Override
	protected void attachModel() {
		super.attachModel();
		getModel().addGanttListener(m_listener);
	}

	@Override
	protected void detachModel() {
		super.detachModel();
		getModel().removeGanttListener(m_listener);
	}

	private final IGanttListener m_listener = new IGanttListener() {

		@Override
		public void onItemClick(Integer itemId) {

		}

		@Override
		public void onItemDragged(Integer itemId, Date startAt, Date endAt) {

		}
	};

}
