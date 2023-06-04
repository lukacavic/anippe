package com.velebit.anippe.client.utilities.announcements;

import com.velebit.anippe.shared.utilities.announcements.AnnouncementFormData;
import com.velebit.anippe.shared.utilities.announcements.IAnnouncementService;
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
public class AnnouncementFormTest {
    @BeanMock
    private IAnnouncementService m_mockSvc;
// TODO [lukacavic] add test cases

    @Before
    public void setup() {
        AnnouncementFormData answer = new AnnouncementFormData();
        Mockito.when(m_mockSvc.prepareCreate(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(m_mockSvc.create(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(m_mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(m_mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
    }
}
