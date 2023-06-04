package com.velebit.anippe.server.servers;

import com.velebit.anippe.server.contacts.ContactDao;
import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.contacts.ContactRequest;
import com.velebit.anippe.shared.shareds.ClientCardFormData;
import com.velebit.anippe.shared.shareds.ClientCardFormData.ContactsTable.ContactsTableRowData;
import com.velebit.anippe.shared.shareds.ClientCardFormData.ProjectsTable.ProjectsTableRowData;
import com.velebit.anippe.shared.shareds.IClientCardService;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class ClientCardService implements IClientCardService {

    @Override
    public ClientCardFormData load(ClientCardFormData formData) {

        //Fetch contacts
        List<ContactsTableRowData> contacts = fetchContacts(formData.getClientId());
        formData.getContactsTable().setRows(contacts.toArray(new ContactsTableRowData[contacts.size()]));

        //Fetch projects
        List<ProjectsTableRowData> projects = fetchProjects(formData.getClientId());
        formData.getProjectsTable().setRows(projects.toArray(new ProjectsTableRowData[projects.size()]));


        return formData;
    }
    
    private List<ProjectsTableRowData> fetchProjects(Integer clientId) {
        return CollectionUtility.emptyArrayList();
    }

    @Override
    public ClientCardFormData store(ClientCardFormData formData) {
        return formData;
    }


    @Override
    public List<ContactsTableRowData> fetchContacts(Integer clientId) {
        ContactRequest request = new ContactRequest();

        List<Contact> contacts = BEANS.get(ContactDao.class).get(request);

        List<ContactsTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(contacts)) return CollectionUtility.emptyArrayList();

        for (Contact contact : contacts) {
            ContactsTableRowData row = new ContactsTableRowData();
            row.setContact(contact);
            row.setName(contact.getFullName());
            row.setEmail(contact.getEmail());
            row.setPhone(contact.getPhone());
            row.setPosition(contact.getPosition());
            row.setActive(contact.isActive());
            row.setLastLoginAt(contact.getLastLoginAt());
            rows.add(row);
        }

        return rows;
    }
}
