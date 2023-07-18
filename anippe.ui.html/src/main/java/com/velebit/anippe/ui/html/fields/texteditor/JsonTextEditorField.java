package com.velebit.anippe.ui.html.fields.texteditor;

import com.velebit.anippe.client.common.fields.texteditor.ITextEditorField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonEvent;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonBasicField;

public class JsonTextEditorField<T extends ITextEditorField> extends JsonBasicField<T> {

	public JsonTextEditorField(T model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
		super(model, uiSession, id, parent);
	}

	@Override
	public String getObjectType() {
		return "anippe.TextEditorField";
	}

	@Override
	protected void initJsonProperties(T model) {
		super.initJsonProperties(model);

	}

	@Override
	public void handleUiEvent(JsonEvent event) {
		super.handleUiEvent(event);
	}

	@Override
	protected void handleUiAcceptInputWhileTyping(String displayText) {
		getModel().getUIFacade().parseAndSetValueFromUI(displayText);
	}

	@Override
	protected void handleUiAcceptInputAfterTyping(String displayText) {
		getModel().getUIFacade().parseAndSetValueFromUI(displayText);
	}

	protected void handleUiAcceptInput(JsonEvent event) {
		String displayText = event.getData().optString(IValueField.PROP_DISPLAY_TEXT);
		addPropertyEventFilterCondition(IValueField.PROP_DISPLAY_TEXT, displayText);
		boolean whileTyping = event.getData().optBoolean("whileTyping", false);
		if (whileTyping) {
			handleUiAcceptInputWhileTyping(displayText);
		} else {
			handleUiAcceptInputAfterTyping(displayText);
		}
	}

}
