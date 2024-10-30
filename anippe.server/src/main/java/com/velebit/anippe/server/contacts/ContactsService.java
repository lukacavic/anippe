package com.velebit.anippe.server.contacts;

import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.contacts.ContactRequest;
import com.velebit.anippe.shared.contacts.ContactsFormData;
import com.velebit.anippe.shared.contacts.IContactsService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class ContactsService implements IContactsService {

    @Override
    public List<ContactsFormData.ContactsTable.ContactsTableRowData> fetchContacts(Integer clientId) {
        ContactRequest request = new ContactRequest();
        request.setClientId(clientId);

        List<Contact> contacts = BEANS.get(ContactDao.class).get(request);
        List<ContactsFormData.ContactsTable.ContactsTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(contacts)) return rows;

        for (Contact contact : contacts) {
            ContactsFormData.ContactsTable.ContactsTableRowData row = new ContactsFormData.ContactsTable.ContactsTableRowData();
            row.setContact(contact);
            row.setEmail(contact.getEmail());
            row.setPrimary(contact.isPrimaryContact());
            row.setActive(contact.isActive());
            row.setPhone(contact.getPhone());
            row.setFullName(contact.getFullName());
            row.setPosition(contact.getPosition());

            rows.add(row);
        }

        return rows;
    }

    @Override
    public void delete(Integer contactId) {
        SQL.update("UPDATE contacts SET deleted_at = now() WHERE id = :contactId", new NVPair("contactId", contactId));
    }

}
