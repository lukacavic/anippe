package com.velebit.anippe.client.tickets;

import com.velebit.anippe.shared.tickets.FollowersFormData;
import com.velebit.anippe.shared.tickets.IFollowersService;
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
public class FollowersFormTest {
    @BeanMock
    private IFollowersService m_mockSvc;
// TODO [lukacavic] add test cases

    @Before
    public void setup() {
        FollowersFormData answer = new FollowersFormData();
    }
}
