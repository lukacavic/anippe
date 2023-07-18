package com.velebit.anippe.client.email;

import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.ArrayList;
import java.util.List;

public class EmailQueueStatusLookupCall extends LocalLookupCall<Integer> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<Integer>> execCreateLookupRows() {

        List<ILookupRow<Integer>> rows = new ArrayList<ILookupRow<Integer>>();

        rows.add(new LookupRow<Integer>(Constants.EmailStatus.PREPARE_SEND, TEXTS.get("PreparingToSend")).withIconId(FontIcons.Email));
        rows.add(new LookupRow<Integer>(Constants.EmailStatus.SENT, TEXTS.get("Sent")).withIconId(FontIcons.Check));
        rows.add(new LookupRow<Integer>(Constants.EmailStatus.ERROR, TEXTS.get("ErrorSending")).withIconId(FontIcons.FolderOpen));

        return rows;
    }
}
