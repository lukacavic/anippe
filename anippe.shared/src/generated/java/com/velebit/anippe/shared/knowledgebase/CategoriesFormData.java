package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.knowledgebase.CategoriesForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class CategoriesFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public CategoriesTable getCategoriesTable() {
        return getFieldByClass(CategoriesTable.class);
    }

    /**
     * access method for property ProjectId.
     */
    public Integer getProjectId() {
        return getProjectIdProperty().getValue();
    }

    /**
     * access method for property ProjectId.
     */
    public void setProjectId(Integer projectId) {
        getProjectIdProperty().setValue(projectId);
    }

    public ProjectIdProperty getProjectIdProperty() {
        return getPropertyByClass(ProjectIdProperty.class);
    }

    public static class CategoriesTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public CategoriesTableRowData addRow() {
            return (CategoriesTableRowData) super.addRow();
        }

        @Override
        public CategoriesTableRowData addRow(int rowState) {
            return (CategoriesTableRowData) super.addRow(rowState);
        }

        @Override
        public CategoriesTableRowData createRow() {
            return new CategoriesTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return CategoriesTableRowData.class;
        }

        @Override
        public CategoriesTableRowData[] getRows() {
            return (CategoriesTableRowData[]) super.getRows();
        }

        @Override
        public CategoriesTableRowData rowAt(int index) {
            return (CategoriesTableRowData) super.rowAt(index);
        }

        public void setRows(CategoriesTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class CategoriesTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String categoryId = "categoryId";
            public static final String name = "name";
            private Integer m_categoryId;
            private String m_name;

            public Integer getCategoryId() {
                return m_categoryId;
            }

            public void setCategoryId(Integer newCategoryId) {
                m_categoryId = newCategoryId;
            }

            public String getName() {
                return m_name;
            }

            public void setName(String newName) {
                m_name = newName;
            }
        }
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }
}
