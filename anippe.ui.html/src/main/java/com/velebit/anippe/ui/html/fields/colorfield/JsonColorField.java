package com.velebit.anippe.ui.html.fields.colorfield;

import com.velebit.anippe.client.common.fields.colorfield.IColorField;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonEvent;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonBasicField;
import org.json.JSONObject;

public class JsonColorField<T extends IColorField> extends JsonBasicField<T> {

	public JsonColorField(T model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
		super(model, uiSession, id, parent);
	}

	@Override
	public String getObjectType() {
		return "vetlio.ColorField";
	}

	@Override
	protected void initJsonProperties(T model) {
		super.initJsonProperties(model);

		putJsonProperty(new JsonProperty<IColorField>(IColorField.PROP_COLOR, model) {
			@Override
			protected String modelValue() {
				return getModel().getValue();
			}
		});

	}

	@Override
	public void handleUiEvent(JsonEvent event) {
		if (event.getType().equals("changeColor")) {
			handleUiChangeColor(event);
		} else {
			super.handleUiEvent(event);
		}
	}

	private void handleUiChangeColor(JsonEvent event) {
		JSONObject json = event.getData();
		getModel().getUIFacade().changeColor(json.optString("color"));
		getModel().getUIFacade().parseAndSetValueFromUI(json.optString("color"));
	}

}
