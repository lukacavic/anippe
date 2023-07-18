package com.velebit.anippe.client.common.fields.texteditor;

import org.eclipse.scout.rt.client.ui.form.fields.IBasicField;

public interface ITextEditorField extends IBasicField<String> {

    ITextEditorFieldUIFacade getUIFacade();

}
