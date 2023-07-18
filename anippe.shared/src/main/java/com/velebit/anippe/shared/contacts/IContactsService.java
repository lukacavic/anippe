package com.velebit.anippe.shared.contacts;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IContactsService extends IService {

    List<ContactsFormData.ContactsTable.ContactsTableRowData> fetchContacts(Integer clientId);

    void delete(Integer contactId);
}
