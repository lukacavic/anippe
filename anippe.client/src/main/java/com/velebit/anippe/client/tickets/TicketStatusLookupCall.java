package com.velebit.anippe.client.tickets;

import com.velebit.anippe.shared.constants.Constants;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class TicketStatusLookupCall extends LocalLookupCall<Integer> {

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
        List<ILookupRow<Integer>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<Integer>(Constants.TicketStatus.CREATED, TEXTS.get("Created")));

        return rows;
    }
}
