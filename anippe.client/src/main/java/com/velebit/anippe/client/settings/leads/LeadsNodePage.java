package com.velebit.anippe.client.settings.leads;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class LeadsNodePage extends AbstractPageWithNodes {

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);

		pageList.add(new LeadStatusesTablePage());
		pageList.add(new LeadSourcesTablePage());
	}

	@Override
	protected String getConfiguredIconId() {
		return FontIcons.UserPlus;
	}

	@Override
	protected String getConfiguredOverviewIconId() {
		return FontIcons.UserPlus;
	}

	@Override
	protected boolean getConfiguredShowTileOverview() {
		return true;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Leads");
	}
}
