package com.velebit.anippe.client.common.fields.colorfield;

import org.eclipse.scout.rt.client.ui.form.fields.IBasicField;

public interface IColorField extends IBasicField<String> {

    String PROP_COLOR = "color";

    IColorFieldUIFacade getUIFacade();

    String getColor();

    void setColor(String color);

}
