package com.velebit.anippe.shared.contacts;

import com.velebit.anippe.shared.contacts.ContactsFormData.ContactsTable.ContactsTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@TunnelToServer
public interface IContactsService extends IService {
    List<ContactsTableRowData> fetchContacts(Integer clientId);

    void delete(Integer contactId);

    ContactsTablePageData getContactsTableData(SearchFilter filter);
}
