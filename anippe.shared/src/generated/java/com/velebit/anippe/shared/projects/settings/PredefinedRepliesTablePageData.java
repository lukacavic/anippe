package com.velebit.anippe.shared.projects.settings;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import javax.annotation.Generated;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.projects.settings.PredefinedRepliesTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class PredefinedRepliesTablePageData extends AbstractTablePageData {
    private static final long serialVersionUID = 1L;

    @Override
    public PredefinedRepliesTableRowData addRow() {
        return (PredefinedRepliesTableRowData) super.addRow();
    }

    @Override
    public PredefinedRepliesTableRowData addRow(int rowState) {
        return (PredefinedRepliesTableRowData) super.addRow(rowState);
    }

    @Override
    public PredefinedRepliesTableRowData createRow() {
        return new PredefinedRepliesTableRowData();
    }

    @Override
    public Class<? extends AbstractTableRowData> getRowType() {
        return PredefinedRepliesTableRowData.class;
    }

    @Override
    public PredefinedRepliesTableRowData[] getRows() {
        return (PredefinedRepliesTableRowData[]) super.getRows();
    }

    @Override
    public PredefinedRepliesTableRowData rowAt(int index) {
        return (PredefinedRepliesTableRowData) super.rowAt(index);
    }

    public void setRows(PredefinedRepliesTableRowData[] rows) {
        super.setRows(rows);
    }

    public static class PredefinedRepliesTableRowData extends AbstractTableRowData {
        private static final long serialVersionUID = 1L;
        public static final String predefinedReplyId = "predefinedReplyId";
        public static final String title = "title";
        public static final String content = "content";
        private Integer m_predefinedReplyId;
        private String m_title;
        private String m_content;

        public Integer getPredefinedReplyId() {
            return m_predefinedReplyId;
        }

        public void setPredefinedReplyId(Integer newPredefinedReplyId) {
            m_predefinedReplyId = newPredefinedReplyId;
        }

        public String getTitle() {
            return m_title;
        }

        public void setTitle(String newTitle) {
            m_title = newTitle;
        }

        public String getContent() {
            return m_content;
        }

        public void setContent(String newContent) {
            m_content = newContent;
        }
    }
}
