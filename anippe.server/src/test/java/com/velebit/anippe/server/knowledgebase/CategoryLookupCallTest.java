package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.CategoryLookupCall;
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
public class CategoryLookupCallTest {
// TODO [lukacavic] add test cases

    protected CategoryLookupCall createLookupCall() {
        return new CategoryLookupCall();
    }

    @Test
    public void testGetDataByAll() {
        CategoryLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [lukacavic] verify data
    }

    @Test
    public void testGetDataByKey() {
        CategoryLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [lukacavic] verify data
    }

    @Test
    public void testGetDataByText() {
        CategoryLookupCall call = createLookupCall();
// TODO [lukacavic] fill call
        List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [lukacavic] verify data
    }
}
