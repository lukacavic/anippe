package com.velebit.anippe.client.common.fields;

import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;

public class AbstractTextAreaField extends AbstractStringField {
    public AbstractTextAreaField() {
        this(true);
    }

    public AbstractTextAreaField(boolean callInitializer) {
        super(callInitializer);
    }

    @Override
    protected int getConfiguredGridH() {
        return 2;
    }

    @Override
    public boolean isMultilineText() {
        return true;
    }

    @Override
    public boolean isWrapText() {
        return true;
    }
}
