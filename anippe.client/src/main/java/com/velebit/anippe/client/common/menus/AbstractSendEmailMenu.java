package com.velebit.anippe.client.common.menus;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class AbstractSendEmailMenu extends AbstractMenu {
	@Override
	protected String getConfiguredIconId() {
		return FontIcons.Email;
	}

	@Override
	protected String getConfiguredText() {
		return TEXTS.get("SendEmail");
	}
}
