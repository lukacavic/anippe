package com.velebit.anippe.client.settings.settings;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class EmailNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Email");
    }

    @Override
    protected void execInitDetailForm() {
        EmailSettingsForm form = (EmailSettingsForm) getDetailForm();

        if (form == null)
            return;
        form.startModify();
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return EmailSettingsForm.class;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Email;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Email;
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }
}
