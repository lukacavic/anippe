package com.velebit.anippe.client.common.columns;

import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;

public abstract class AbstractColorColumn extends AbstractStringColumn {

    public AbstractColorColumn() {
        this(true);
    }

    public AbstractColorColumn(boolean callInitializer) {
        super(callInitializer);
    }

    @Override
    protected void execDecorateCell(Cell cell, ITableRow row) {
        cell.setText("");
        String color = getValue(row) != null ? getValue(row).replace("#", "").trim() : null;
        cell.setBackgroundColor(color);
    }

    @Override
    protected boolean getConfiguredSummary() {
        return false;
    }

    @Override
    public boolean isFixedPosition() {
        return true;
    }

    @Override
    public boolean isFixedWidth() {
        return true;
    }

    @Override
    protected int getConfiguredWidth() {
        return 50;
    }
}
