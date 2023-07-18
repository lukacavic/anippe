package com.velebit.anippe.client.contacts;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class ContactsNodePage extends AbstractPageWithNodes {
    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Contacts");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Phone;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Phone;
    }

    @Override
    protected Class<? extends IForm> getConfiguredDetailForm() {
        return ContactsForm.class;
    }

    @Override
    protected void execInitDetailForm() {
        ContactsForm form = (ContactsForm) getDetailForm();
    }

    @Override
    protected boolean getConfiguredTableVisible() {
        return false;
    }

}
