package com.velebit.anippe.client.tasks;

import com.velebit.anippe.shared.constants.Constants.TaskStatus;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class TaskStatusLookupCall extends LocalLookupCall<Integer> {

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {
        List<ILookupRow<Integer>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<Integer>(TaskStatus.CREATED, TEXTS.get("Created")).withIconId(FontIcons.Pencil));
        rows.add(new LookupRow<Integer>(TaskStatus.IN_PROGRESS, TEXTS.get("InProgress")).withIconId(FontIcons.Spinner1));
        rows.add(new LookupRow<Integer>(TaskStatus.TESTING, TEXTS.get("Testing")).withIconId(FontIcons.Users1));
        rows.add(new LookupRow<Integer>(TaskStatus.AWAITING_FEEDBACK, TEXTS.get("AwaitingFeedback")).withIconId(FontIcons.Note));
        rows.add(new LookupRow<Integer>(TaskStatus.COMPLETED, TEXTS.get("Completed")).withIconId(FontIcons.Check));

        return rows;
    }

}
