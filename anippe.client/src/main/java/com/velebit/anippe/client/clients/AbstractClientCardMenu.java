package com.velebit.anippe.client.clients;

import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.Set;

public class AbstractClientCardMenu extends AbstractMenu {

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredText() {
        return TEXTS.get("ClientCard");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserCheck;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(TableMenuType.SingleSelection);
    }

    @Override
    protected void execAction() {
        ClientCardForm form = new ClientCardForm();
        form.setClientId(getClientId());
        form.startModify();
    }
}
