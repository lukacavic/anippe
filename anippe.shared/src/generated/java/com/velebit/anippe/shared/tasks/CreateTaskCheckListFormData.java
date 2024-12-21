package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.tasks.CreateTaskCheckListForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class CreateTaskCheckListFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public Name getName() {
        return getFieldByClass(Name.class);
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

    public static class Name extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class TaskIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }
}
