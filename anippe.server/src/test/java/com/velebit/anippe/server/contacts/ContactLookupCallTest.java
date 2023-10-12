package com.velebit.anippe.server.contacts;

import com.velebit.anippe.shared.contacts.ContactLookupCall;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWithSubject("anonymous")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(IServerSession.class)
public class ContactLookupCallTest {
// TODO [lukacavic] add test cases

    protected ContactLookupCall createLookupCall() {
        return new ContactLookupCall();
    }

    @Test
    public void testGetDataByAll() {
        ContactLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [lukacavic] verify data
    }

    @Test
    public void testGetDataByKey() {
        ContactLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [lukacavic] verify data
    }

    @Test
    public void testGetDataByText() {
        ContactLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [lukacavic] verify data
    }
}
