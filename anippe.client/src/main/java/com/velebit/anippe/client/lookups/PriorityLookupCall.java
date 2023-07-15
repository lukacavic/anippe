package com.velebit.anippe.client.lookups;

import com.velebit.anippe.shared.constants.ColorConstants;
import com.velebit.anippe.shared.constants.ColorConstants.Red;
import com.velebit.anippe.shared.constants.Constants;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class PriorityLookupCall extends LocalLookupCall<Integer> {

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
        List<ILookupRow<Integer>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<Integer>(Constants.Priority.LOW, TEXTS.get("Low")));
        rows.add(new LookupRow<Integer>(Constants.Priority.NORMAL, TEXTS.get("Normal")));
        rows.add(new LookupRow<Integer>(Constants.Priority.HIGH, TEXTS.get("High")));
        rows.add(new LookupRow<Integer>(Constants.Priority.URGENT, TEXTS.get("Urgent")).withForegroundColor("red"));

        return rows;
    }
}
