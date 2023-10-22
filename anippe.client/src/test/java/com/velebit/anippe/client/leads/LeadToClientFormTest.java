package com.velebit.anippe.client.leads;

import com.velebit.anippe.shared.leads.ILeadToClientService;
import com.velebit.anippe.shared.leads.LeadToClientFormData;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class LeadToClientFormTest {
    @BeanMock
    private ILeadToClientService m_mockSvc;
// TODO [lukacavic] add test cases

    @Before
    public void setup() {
        LeadToClientFormData answer = new LeadToClientFormData();
    }
}
