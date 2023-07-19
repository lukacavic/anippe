package com.velebit.anippe.client.lookups;

import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class RelatedLookupCall extends LocalLookupCall<Integer> {

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
        List<ILookupRow<Integer>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<Integer>(Constants.Related.TICKET, TEXTS.get("Ticket")).withIconId(FontIcons.Info));
        rows.add(new LookupRow<Integer>(Constants.Related.CLIENT, TEXTS.get("Client")).withIconId(FontIcons.Users1));
        rows.add(new LookupRow<Integer>(Constants.Related.PROJECT, TEXTS.get("Project")).withIconId(FontIcons.Folder));

        return rows;
    }
}
