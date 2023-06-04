package com.velebit.anippe.client.common.menus;

import com.velebit.anippe.shared.Icons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.Set;

public class AbstractEditMenu extends AbstractMenu {
  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Edit");
  }
  @Override
  protected Set<? extends IMenuType> getConfiguredMenuTypes() {
    return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.SingleSelection);
  }
  @Override
  protected String getConfiguredIconId() {
    return Icons.Pencil;
  }
}
