package com.velebit.anippe.client.calendar;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CalendarNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Calendar");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Calendar;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Calendar;
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return CalendarForm.class;
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }
}
