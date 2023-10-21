package com.velebit.anippe.client.common.fields;

import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class AbstractEmailField extends AbstractStringField {
    public AbstractEmailField() {
        this(true);
    }

    public AbstractEmailField(boolean callInitializer) {
        super(callInitializer);
    }

    @Override
    protected String execValidateValue(String rawValue) {
        if (rawValue == null)
            return rawValue;

        if (!rawValue.equalsIgnoreCase("")) {
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if (!rawValue.matches(regex)) {
                throw new VetoException(TEXTS.get("EmailIsInvalid"));
            }
        }
        return rawValue;
    }

}
