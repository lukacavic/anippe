package com.velebit.anippe.client.extensions;

import org.eclipse.scout.rt.client.extension.ui.form.AbstractFormExtension;
import org.eclipse.scout.rt.client.extension.ui.form.FormChains;
import org.eclipse.scout.rt.client.extension.ui.form.fields.AbstractFormFieldExtension;
import org.eclipse.scout.rt.client.extension.ui.form.fields.FormFieldChains;
import org.eclipse.scout.rt.client.extension.ui.form.fields.button.AbstractButtonExtension;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSaveButton;

public class ScoutFieldsExtension extends AbstractFormExtension<AbstractForm> {
    public ScoutFieldsExtension(AbstractForm ownerField) {
        super(ownerField);
    }

    @Override
    public void execInitForm(FormChains.FormInitFormChain chain) {
        super.execInitForm(chain);

        //getOwner().getAllFields().forEach(f -> f.setFieldStyle(IFormField.FIELD_STYLE_CLASSIC));
        //getOwner().getAllFields().forEach(f -> f.setLabelPosition(IFormField.LABEL_POSITION_TOP));
    }
}
