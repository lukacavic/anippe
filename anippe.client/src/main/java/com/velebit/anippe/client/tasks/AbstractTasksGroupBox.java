package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = AbstractTasksGroupBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractTasksGroupBox extends AbstractGroupBox {

    private Integer relatedId;
    private Integer relatedType;

    @FormData
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    protected abstract void reloadTasks();

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }
    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }
    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    public TasksTableField getTasksTableField() {
        return getFieldByClass(TasksTableField.class);
    }

    @Override
    protected String getConfiguredLabel() {
        return TEXTS.get("Tasks");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewTask");
        }

        @Override
        protected void execAction() {
            TaskForm form = new TaskForm();
            form.setRelatedId(getRelatedId().longValue());
            form.setRelatedType(getRelatedType());
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadTasks();
            }
        }
    }

    @Order(1000)
    public class TasksTableField extends AbstractTableField<TasksTableField.Table> {
        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }
        @Override
        public boolean isLabelVisible() {
            return false;
        }

        @Override
        protected void execInitField() {
            super.execInitField();

            setLabel();
        }

        private void setLabel() {
            int tasksCount = getTable().getRowCount();
            AbstractTasksGroupBox.this.setLabel(AbstractTasksGroupBox.this.getConfiguredLabel() + " (" + tasksCount + ")");
        }

        @Override
        protected int getConfiguredGridH() {
            return 6;
        }

        @ClassId("d27e61a9-140e-49d3-b024-64b9f7f0c494")
        public class Table extends AbstractTasksTable {
            @Override
            protected void execContentChanged() {
                super.execContentChanged();

                setLabel();
            }


        }
    }

}
