package com.velebit.anippe.client.contacts;

import com.velebit.anippe.shared.contacts.ContactsFormData;
import com.velebit.anippe.shared.contacts.IContactsService;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class ContactsFormTest {
    @BeanMock
    private IContactsService m_mockSvc;
// TODO [lukacavic] add test cases

    @Before
    public void setup() {
        ContactsFormData answer = new ContactsFormData();
    }
}
