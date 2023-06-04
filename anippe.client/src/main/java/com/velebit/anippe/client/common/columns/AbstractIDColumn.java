package com.velebit.anippe.client.common.columns;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;

public class AbstractIDColumn extends AbstractIntegerColumn {

    @Override
    protected boolean getConfiguredDisplayable() {
        return false;
    }
}
