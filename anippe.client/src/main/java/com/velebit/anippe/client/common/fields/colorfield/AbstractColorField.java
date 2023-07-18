package com.velebit.anippe.client.common.fields.colorfield;

import org.eclipse.scout.rt.client.ModelContextProxy;
import org.eclipse.scout.rt.client.ModelContextProxy.ModelContext;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractBasicField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ColorUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractColorField extends AbstractBasicField<String> implements IColorField {
    protected static final Pattern RGB_COLOR_PATTERN = Pattern.compile("^([0-9]{1,3})[\\-\\,\\;\\/\\\\\\s]{1}([0-9]{1,3})[\\-\\,\\;\\/\\\\\\s]{1}([0-9]{1,3})$");
    private IColorFieldUIFacade m_uiFacade;

    public AbstractColorField() {
        this(true);
    }

    public AbstractColorField(boolean callInitializer) {
        super(callInitializer);
    }

    @Override
    protected void initConfig() {
        super.initConfig();
        m_uiFacade = BEANS.get(ModelContextProxy.class).newProxy(new P_ColorUIFacade(), ModelContext.copyCurrent());
    }

    @Override
    public IColorFieldUIFacade getUIFacade() {
        return m_uiFacade;
    }

    @Override
    protected String parseValueInternal(String text) {
        // hex
        if (StringUtility.isNullOrEmpty(text)) {
            return null;
        }
        try {
            // try to parse hex
            Matcher matcher = ColorUtility.HEX_COLOR_PATTERN.matcher(text);
            if (matcher.matches()) {
                return "#" + matcher.group(2);
            }
            // try to parse any kind of RGB
            matcher = RGB_COLOR_PATTERN.matcher(text);
            if (matcher.matches()) {
                int r = Integer.parseInt(matcher.group(1));
                int g = Integer.parseInt(matcher.group(2));
                int b = Integer.parseInt(matcher.group(3));
                if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                    throw new ProcessingException(TEXTS.get("InvalidValueMessageX", text));
                }
                String hexValue = ColorUtility.rgbToText(r, g, b).toUpperCase();
                return hexValue;
            }
        } catch (Exception e) {
            throw new ProcessingException(TEXTS.get("InvalidValueMessageX", text), e);
        }
        throw new ProcessingException(TEXTS.get("InvalidValueMessageX", text));
    }

    @Override
    public String getColor() {
        return propertySupport.getPropertyString("color");
    }

    @Override
    public void setColor(String color) {
        propertySupport.setProperty("color", color);
    }

    protected class P_ColorUIFacade extends AbstractBasicField<String>.P_UIFacade implements IColorFieldUIFacade {

        @Override
        public void changeColor(String color) {
            if (color != null && color.contains("#")) {
                color = color.replace("#", "");
            }

            setValue(color);
        }

    }

}
