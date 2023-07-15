package com.velebit.anippe.client.clients;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class NavigationLookupCall extends LocalLookupCall<Long> {
    @Override
    protected List<? extends ILookupRow<Long>> execCreateLookupRows() {
        List<ILookupRow<Long>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<Long>(1L, TEXTS.get("Summary")).withIconId(FontIcons.Chart));

        return rows;
    }
}
