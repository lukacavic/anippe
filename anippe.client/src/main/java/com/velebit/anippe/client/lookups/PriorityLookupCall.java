package com.velebit.anippe.client.lookups;

import com.velebit.anippe.shared.Icons;
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

        rows.add(new LookupRow<Integer>(Constants.Priority.LOW, TEXTS.get("Low")).withIconId(Icons.GrayCircle));
        rows.add(new LookupRow<Integer>(Constants.Priority.NORMAL, TEXTS.get("Normal")).withIconId(Icons.YellowCircle));
        rows.add(new LookupRow<Integer>(Constants.Priority.HIGH, TEXTS.get("High")).withIconId(Icons.OrangeCircle));
        rows.add(new LookupRow<Integer>(Constants.Priority.URGENT, TEXTS.get("Urgent")).withIconId(Icons.RedCircle));

        return rows;
    }
}
