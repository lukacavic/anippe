package com.velebit.anippe.ui.html;

import com.velebit.anippe.client.gantt.GanttItem;
import com.velebit.anippe.client.kanban.IKanbanField;
import com.velebit.anippe.client.kanban.IKanbanListener;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class JsonKanbanField extends JsonFormField<IKanbanField> {

	public JsonKanbanField(IKanbanField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
		super(model, uiSession, id, parent);
	}

	@Override
	public String getObjectType() {
		return "anippe.KanbanField";
	}

	@Override
	protected void initJsonProperties(IKanbanField model) {
		super.initJsonProperties(model);

		putJsonProperty(new JsonProperty<IKanbanField>(IKanbanField.PROP_ITEMS, model) {
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

		return json;
	}

	@Override
	protected void attachModel() {
		super.attachModel();
		getModel().addKanbanListener(m_listener);
	}

	@Override
	protected void detachModel() {
		super.detachModel();
		getModel().removeKanbanListener(m_listener);
	}

	private final IKanbanListener m_listener = new IKanbanListener() {

	};

}
