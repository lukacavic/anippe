package com.velebit.anippe.client.projects;

import com.velebit.anippe.shared.projects.ILeadsService;
import com.velebit.anippe.shared.projects.LeadsFormData;
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
public class LeadsFormTest {
    @BeanMock
    private ILeadsService m_mockSvc;
// TODO [lukacavic] add test cases

    @Before
    public void setup() {
        LeadsFormData answer = new LeadsFormData();
    }
}
