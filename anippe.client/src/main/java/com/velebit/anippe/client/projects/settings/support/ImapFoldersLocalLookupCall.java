package com.velebit.anippe.client.projects.settings.support;

import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.ArrayList;
import java.util.List;

public class ImapFoldersLocalLookupCall extends LocalLookupCall<String> {

    private List<String> folders = CollectionUtility.emptyArrayList();

    public List<String> getFolders() {
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
        List<LookupRow<String>> list = new ArrayList<LookupRow<String>>();

        if(CollectionUtility.isEmpty(folders)) return list;

        for(String folder : folders) {
            list.add(new LookupRow<String>(folder, folder));
        }

        return list;
    }
}
