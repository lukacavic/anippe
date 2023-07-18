package com.velebit.anippe.client.clients;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class NavigationLookupCall extends LocalLookupCall<String> {
    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
        List<ILookupRow<String>> rows = CollectionUtility.emptyArrayList();

        rows.add(new LookupRow<String>("SUMMARY", TEXTS.get("Summary")).withIconId(FontIcons.Chart));
        rows.add(new LookupRow<String>("CONTACTS", TEXTS.get("Contacts")).withIconId(FontIcons.Users1));
        rows.add(new LookupRow<String>("TASKS", TEXTS.get("Tasks")).withIconId(FontIcons.Tasks));
        rows.add(new LookupRow<String>("TICKETS", TEXTS.get("Tickets")).withIconId(FontIcons.Info));
        rows.add(new LookupRow<String>("DOCUMENTS", TEXTS.get("Documents")).withIconId(FontIcons.Paperclip));
        rows.add(new LookupRow<String>("REMINDERS", TEXTS.get("Reminders")).withIconId(FontIcons.Clock));
        rows.add(new LookupRow<String>("VAULT", TEXTS.get("Vault")).withIconId(FontIcons.Key));

        return rows;
    }
}
