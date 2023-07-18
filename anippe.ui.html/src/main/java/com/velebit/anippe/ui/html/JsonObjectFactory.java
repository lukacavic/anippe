package com.velebit.anippe.ui.html;

import com.velebit.anippe.client.common.fields.colorfield.IColorField;
import com.velebit.anippe.client.common.fields.texteditor.ITextEditorField;
import com.velebit.anippe.ui.html.fields.colorfield.JsonColorField;
import com.velebit.anippe.ui.html.fields.texteditor.JsonTextEditorField;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.AbstractJsonObjectFactory;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;

@Bean
@Order(100)
public class JsonObjectFactory extends AbstractJsonObjectFactory {

    @Override
    public IJsonAdapter<?> createJsonAdapter(Object model, IUiSession session, String id, IJsonAdapter<?> parent) {

        if (model instanceof IColorField) {
            return new JsonColorField<>((IColorField) model, session, id, parent);
        }

        if (model instanceof ITextEditorField) {
            return new JsonTextEditorField<>((ITextEditorField) model, session, id, parent);
        }

        return null;
    }

}
