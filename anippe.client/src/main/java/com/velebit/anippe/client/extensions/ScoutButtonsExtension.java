package com.velebit.anippe.client.extensions;

import org.eclipse.scout.rt.client.extension.ui.form.fields.FormFieldChains;
import org.eclipse.scout.rt.client.extension.ui.form.fields.button.AbstractButtonExtension;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSaveButton;

public class ScoutButtonsExtension extends AbstractButtonExtension<AbstractButton> {
    public ScoutButtonsExtension(AbstractButton ownerField) {
        super(ownerField);
    }

    @Override
    public void execInitField(FormFieldChains.FormFieldInitFieldChain chain) {
        super.execInitField(chain);

        if (getOwner() instanceof AbstractOkButton || getOwner() instanceof AbstractCancelButton || getOwner() instanceof AbstractSaveButton) {
            getOwner().getGridData().withHorizontalAlignment(1);
        }
    }

}
