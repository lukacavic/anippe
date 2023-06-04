package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractFormFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.tasks.AbstractTasksGroupBox", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public abstract class AbstractTasksGroupBoxData extends AbstractFormFieldData {
    private static final long serialVersionUID = 1L;

    /**
     * access method for property RelatedId.
     */
    public Integer getRelatedId() {
        return getRelatedIdProperty().getValue();
    }

    /**
     * access method for property RelatedId.
     */
    public void setRelatedId(Integer relatedId) {
        getRelatedIdProperty().setValue(relatedId);
    }

    public RelatedIdProperty getRelatedIdProperty() {
        return getPropertyByClass(RelatedIdProperty.class);
    }

    /**
     * access method for property RelatedType.
     */
    public Integer getRelatedType() {
        return getRelatedTypeProperty().getValue();
    }

    /**
     * access method for property RelatedType.
     */
    public void setRelatedType(Integer relatedType) {
        getRelatedTypeProperty().setValue(relatedType);
    }

    public RelatedTypeProperty getRelatedTypeProperty() {
        return getPropertyByClass(RelatedTypeProperty.class);
    }

    public TasksTable getTasksTable() {
        return getFieldByClass(TasksTable.class);
    }

    public static class RelatedIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class RelatedTypeProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class TasksTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public TasksTableRowData addRow() {
            return (TasksTableRowData) super.addRow();
        }

        @Override
        public TasksTableRowData addRow(int rowState) {
            return (TasksTableRowData) super.addRow(rowState);
        }

        @Override
        public TasksTableRowData createRow() {
            return new TasksTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return TasksTableRowData.class;
        }

        @Override
        public TasksTableRowData[] getRows() {
            return (TasksTableRowData[]) super.getRows();
        }

        @Override
        public TasksTableRowData rowAt(int index) {
            return (TasksTableRowData) super.rowAt(index);
        }

        public void setRows(TasksTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class TasksTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String task = "task";
            public static final String color = "color";
            public static final String name = "name";
            public static final String status = "status";
            public static final String startAt = "startAt";
            public static final String deadlineAt = "deadlineAt";
            public static final String assignedTo = "assignedTo";
            public static final String priority = "priority";
            private Task m_task;
            private String m_color;
            private String m_name;
            private Integer m_status;
            private Date m_startAt;
            private Date m_deadlineAt;
            private String m_assignedTo;
            private Integer m_priority;

            public Task getTask() {
                return m_task;
            }

            public void setTask(Task newTask) {
                m_task = newTask;
            }

            public String getColor() {
                return m_color;
            }

            public void setColor(String newColor) {
                m_color = newColor;
            }

            public String getName() {
                return m_name;
            }

            public void setName(String newName) {
                m_name = newName;
            }

            public Integer getStatus() {
                return m_status;
            }

            public void setStatus(Integer newStatus) {
                m_status = newStatus;
            }

            public Date getStartAt() {
                return m_startAt;
            }

            public void setStartAt(Date newStartAt) {
                m_startAt = newStartAt;
            }

            public Date getDeadlineAt() {
                return m_deadlineAt;
            }

            public void setDeadlineAt(Date newDeadlineAt) {
                m_deadlineAt = newDeadlineAt;
            }

            public String getAssignedTo() {
                return m_assignedTo;
            }

            public void setAssignedTo(String newAssignedTo) {
                m_assignedTo = newAssignedTo;
            }

            public Integer getPriority() {
                return m_priority;
            }

            public void setPriority(Integer newPriority) {
                m_priority = newPriority;
            }
        }
    }
}
