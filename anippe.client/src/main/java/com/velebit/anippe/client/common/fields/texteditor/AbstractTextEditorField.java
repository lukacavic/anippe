package com.velebit.anippe.client.common.fields.texteditor;

import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractBasicField;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.regex.Pattern;

public class AbstractTextEditorField extends AbstractBasicField<String> implements ITextEditorField {
    protected static final Pattern RGB_COLOR_PATTERN = Pattern.compile("^([0-9]{1,3})[\\-\\,\\;\\/\\\\\\s]{1}([0-9]{1,3})[\\-\\,\\;\\/\\\\\\s]{1}([0-9]{1,3})$");
    private ITextEditorFieldUIFacade m_uiFacade;

    public AbstractTextEditorField() {
        this(true);
    }

    public AbstractTextEditorField(boolean callInitializer) {
        super(callInitializer);
    }

    @Override
    protected void initConfig() {
        super.initConfig();
        m_uiFacade = BEANS.get(ModelContextProxy.class).newProxy(new P_TextEditorFieldUIFacade(), ModelContext.copyCurrent());
        super.setUpdateDisplayTextOnModify(true);
    }

    @Override
    public ITextEditorFieldUIFacade getUIFacade() {
        return m_uiFacade;
    }

    @Override
    protected String parseValueInternal(String text) {
        return text;
    }

    protected class P_TextEditorFieldUIFacade extends AbstractBasicField<String>.P_UIFacade implements ITextEditorFieldUIFacade {

    }

}
