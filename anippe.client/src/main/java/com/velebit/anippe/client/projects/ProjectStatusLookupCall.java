package com.velebit.anippe.client.projects;

import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.ProjectStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class ProjectStatusLookupCall extends LocalLookupCall<Integer> {

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
        List<ILookupRow<Integer>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<Integer>(ProjectStatus.OPEN, TEXTS.get("Open")));
        rows.add(new LookupRow<Integer>(ProjectStatus.CANCELED, TEXTS.get("Canceled")));
        rows.add(new LookupRow<Integer>(ProjectStatus.HOLD, TEXTS.get("Hold")));
        rows.add(new LookupRow<Integer>(ProjectStatus.COMPLETED, TEXTS.get("Completed")));

        return rows;
    }
}