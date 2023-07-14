package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.TasksForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.AbstractTasksTable;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.TasksFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = TasksFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TasksForm extends AbstractForm {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Tasks");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.TasksTableField getTasksTableField() {
        return getFieldByClass(GroupBox.TasksTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Order(0)
            public class SwitchViewMenu extends AbstractMenu {
                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.List;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(1000)
            public class FilterMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("Filter");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Filter;
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(1000)
            public class TasksTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<TasksTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("24b73347-a392-4f9d-94f0-4a204dab2ed9")
                public class Table extends AbstractTasksTable {

                    @Override
                    public void reloadData() {

                    }
                }
            }
        }
    }


    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TasksFormData formData = new TasksFormData();
            exportFormData(formData);
            formData = BEANS.get(ITasksService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TasksFormData formData = new TasksFormData();
            exportFormData(formData);
            formData = BEANS.get(ITasksService.class).create(formData);
            importFormData(formData);
        }
    }

}
