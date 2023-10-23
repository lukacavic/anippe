package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.TicketDepartmentLookupCall;
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
public class TicketDepartmentLookupCallTest {
// TODO [lukacavic] add test cases

    protected TicketDepartmentLookupCall createLookupCall() {
        return new TicketDepartmentLookupCall();
    }

    @Test
    public void testGetDataByAll() {
        TicketDepartmentLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [lukacavic] verify data
    }

    @Test
    public void testGetDataByKey() {
        TicketDepartmentLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [lukacavic] verify data
    }

    @Test
    public void testGetDataByText() {
        TicketDepartmentLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [lukacavic] verify data
    }
}
