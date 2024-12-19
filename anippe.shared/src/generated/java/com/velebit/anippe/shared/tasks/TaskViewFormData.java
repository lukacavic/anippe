package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.tasks.TaskViewForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class TaskViewFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    /**
     * access method for property ActiveTimerId.
     */
    public Integer getActiveTimerId() {
        return getActiveTimerIdProperty().getValue();
    }

    /**
     * access method for property ActiveTimerId.
     */
    public void setActiveTimerId(Integer activeTimerId) {
        getActiveTimerIdProperty().setValue(activeTimerId);
    }

    public ActiveTimerIdProperty getActiveTimerIdProperty() {
        return getPropertyByClass(ActiveTimerIdProperty.class);
    }

    public ActivityLogTable getActivityLogTable() {
        return getFieldByClass(ActivityLogTable.class);
    }

    public ChildTasksProgress getChildTasksProgress() {
        return getFieldByClass(ChildTasksProgress.class);
    }

    public Comment getComment() {
        return getFieldByClass(Comment.class);
    }

    public Description getDescription() {
        return getFieldByClass(Description.class);
    }

    public SubTasksTable getSubTasksTable() {
        return getFieldByClass(SubTasksTable.class);
    }

    /**
     * access method for property Task.
     */
    public Task getTask() {
        return getTaskProperty().getValue();
    }

    /**
     * access method for property Task.
     */
    public void setTask(Task task) {
        getTaskProperty().setValue(task);
    }

    public TaskProperty getTaskProperty() {
        return getPropertyByClass(TaskProperty.class);
    }

    /**
     * access method for property TaskId.
     */
    public Integer getTaskId() {
        return getTaskIdProperty().getValue();
    }

    /**
     * access method for property TaskId.
     */
    public void setTaskId(Integer taskId) {
        getTaskIdProperty().setValue(taskId);
    }

    public TaskIdProperty getTaskIdProperty() {
        return getPropertyByClass(TaskIdProperty.class);
    }

    public static class ActiveTimerIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class ActivityLogTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public ActivityLogTableRowData addRow() {
            return (ActivityLogTableRowData) super.addRow();
        }

        @Override
        public ActivityLogTableRowData addRow(int rowState) {
            return (ActivityLogTableRowData) super.addRow(rowState);
        }

        @Override
        public ActivityLogTableRowData createRow() {
            return new ActivityLogTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return ActivityLogTableRowData.class;
        }

        @Override
        public ActivityLogTableRowData[] getRows() {
            return (ActivityLogTableRowData[]) super.getRows();
        }

        @Override
        public ActivityLogTableRowData rowAt(int index) {
            return (ActivityLogTableRowData) super.rowAt(index);
        }

        public void setRows(ActivityLogTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class ActivityLogTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String activityLogId = "activityLogId";
            public static final String createdAt = "createdAt";
            public static final String createdBy = "createdBy";
            public static final String createdById = "createdById";
            public static final String activityLog = "activityLog";
            private Integer m_activityLogId;
            private Date m_createdAt;
            private String m_createdBy;
            private Integer m_createdById;
            private String m_activityLog;

            public Integer getActivityLogId() {
                return m_activityLogId;
            }

            public void setActivityLogId(Integer newActivityLogId) {
                m_activityLogId = newActivityLogId;
            }

            public Date getCreatedAt() {
                return m_createdAt;
            }

            public void setCreatedAt(Date newCreatedAt) {
                m_createdAt = newCreatedAt;
            }

            public String getCreatedBy() {
                return m_createdBy;
            }

            public void setCreatedBy(String newCreatedBy) {
                m_createdBy = newCreatedBy;
            }

            public Integer getCreatedById() {
                return m_createdById;
            }

            public void setCreatedById(Integer newCreatedById) {
                m_createdById = newCreatedById;
            }

            public String getActivityLog() {
                return m_activityLog;
            }

            public void setActivityLog(String newActivityLog) {
                m_activityLog = newActivityLog;
            }
        }
    }

    public static class ChildTasksProgress extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Comment extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Description extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class SubTasksTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public SubTasksTableRowData addRow() {
            return (SubTasksTableRowData) super.addRow();
        }

        @Override
        public SubTasksTableRowData addRow(int rowState) {
            return (SubTasksTableRowData) super.addRow(rowState);
        }

        @Override
        public SubTasksTableRowData createRow() {
            return new SubTasksTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return SubTasksTableRowData.class;
        }

        @Override
        public SubTasksTableRowData[] getRows() {
            return (SubTasksTableRowData[]) super.getRows();
        }

        @Override
        public SubTasksTableRowData rowAt(int index) {
            return (SubTasksTableRowData) super.rowAt(index);
        }

        public void setRows(SubTasksTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class SubTasksTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String childTaskId = "childTaskId";
            public static final String completedAt = "completedAt";
            public static final String completed = "completed";
            public static final String createdAt = "createdAt";
            public static final String createdBy = "createdBy";
            public static final String task = "task";
            public static final String actions = "actions";
            private Integer m_childTaskId;
            private Date m_completedAt;
            private Boolean m_completed;
            private Date m_createdAt;
            private String m_createdBy;
            private String m_task;
            private String m_actions;

            public Integer getChildTaskId() {
                return m_childTaskId;
            }

            public void setChildTaskId(Integer newChildTaskId) {
                m_childTaskId = newChildTaskId;
            }

            public Date getCompletedAt() {
                return m_completedAt;
            }

            public void setCompletedAt(Date newCompletedAt) {
                m_completedAt = newCompletedAt;
            }

            public Boolean getCompleted() {
                return m_completed;
            }

            public void setCompleted(Boolean newCompleted) {
                m_completed = newCompleted;
            }

            public Date getCreatedAt() {
                return m_createdAt;
            }

            public void setCreatedAt(Date newCreatedAt) {
                m_createdAt = newCreatedAt;
            }

            public String getCreatedBy() {
                return m_createdBy;
            }

            public void setCreatedBy(String newCreatedBy) {
                m_createdBy = newCreatedBy;
            }

            public String getTask() {
                return m_task;
            }

            public void setTask(String newTask) {
                m_task = newTask;
            }

            public String getActions() {
                return m_actions;
            }

            public void setActions(String newActions) {
                m_actions = newActions;
            }
        }
    }

    public static class TaskIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class TaskProperty extends AbstractPropertyData<Task> {
        private static final long serialVersionUID = 1L;
    }
}
