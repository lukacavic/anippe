package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.TasksForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.AbstractTasksTable;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ITasksService;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.TasksFormData;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData;
import com.velebit.anippe.shared.tasks.TasksTablePageData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

@FormData(value = TasksFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TasksForm extends AbstractForm {

    private Project project;

    @FormData
    public Project getProject() {
        return project;
    }

    @FormData
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Tasks");
    }

    public GroupBox.InformationBoxesBox.AwaitingFeedbackField getAwaitingFeedbackField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.AwaitingFeedbackField.class);
    }

    public GroupBox.InformationBoxesBox.CompletedField getCompletedField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.CompletedField.class);
    }

    public GroupBox.InformationBoxesBox.CreatedField getCreatedField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.CreatedField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.InformationBoxesBox.InProgressField getInProgressField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.InProgressField.class);
    }

    public GroupBox.InformationBoxesBox getInformationBoxesBox() {
        return getFieldByClass(GroupBox.InformationBoxesBox.class);
    }

    public GroupBox.TasksSummaryField getTasksSummaryField() {
        return getFieldByClass(GroupBox.TasksSummaryField.class);
    }

    public GroupBox.TasksTableField getTasksTableField() {
        return getFieldByClass(GroupBox.TasksTableField.class);
    }

    public GroupBox.InformationBoxesBox.TestingField getTestingField() {
        return getFieldByClass(GroupBox.InformationBoxesBox.TestingField.class);
    }

    public void fetchTasks() {
        List<TasksTablePageData.TasksTableRowData> rows = BEANS.get(ITasksService.class).fetchTasks();
        getTasksTableField().getTable().importFromTableRowBeanData(rows, AbstractTasksGroupBoxData.TasksTable.TasksTableRowData.class);

    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Override
            protected double getConfiguredGridWeightY() {
                return 1;
            }

            @Order(0)
            public class SwitchDisplayStyleMenu extends AbstractMenu {

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Menu;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(100)
            public class TasksOverview extends AbstractMenu {

                @Override
                protected int getConfiguredActionStyle() {
                    return ACTION_STYLE_BUTTON;
                }

                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("TasksOverview");
                }

                @Override
                protected String getConfiguredCssClass() {
                    return "greenbutton";
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(1000)
            public class FilterMenu extends AbstractMenu {

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

            @Order(-1000)
            @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
            public class TasksSummaryField extends AbstractLabelField {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredHtmlEnabled() {
                    return true;
                }

                @Override
                protected void execInitField() {
                    super.execInitField();
                    IHtmlContent content = HTML.fragment(HTML.bold("Tasks Summary").style("font-size:19px;font-weight:normal"));

                    setValue(content.toHtml());
                }
            }

            @Order(0)
            public class InformationBoxesBox extends org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 5;
                }

                @Override
                public boolean isBorderVisible() {
                    return false;
                }

                @Order(1000)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class CreatedField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Created";
                    }

                    @Override
                    public Integer getCount() {
                        return 13;
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 3";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#64748b";
                    }
                }

                @Order(2000)
                public class InProgressField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "In Progress";
                    }

                    @Override
                    public Integer getCount() {
                        return 13;
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 3";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#3b82f6";
                    }
                }

                @Order(3000)
                public class TestingField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Testing";
                    }

                    @Override
                    public Integer getCount() {
                        return 0;
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 1";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#0284c7";
                    }
                }

                @Order(4000)
                public class AwaitingFeedbackField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Awaiting Feedback";
                    }

                    @Override
                    public Integer getCount() {
                        return 6;
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 1";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#84cc16";
                    }
                }

                @Order(5000)
                public class CompletedField extends AbstractInformationField {
                    @Override
                    public String getLabel() {
                        return "Completed";
                    }

                    @Override
                    public Integer getCount() {
                        return 49;
                    }

                    @Override
                    public String getSubLabel() {
                        return "Tasks assigned to me: 33";
                    }

                    @Override
                    public String getLabelColor() {
                        return "#22c55e";
                    }
                }

            }

            @Order(1000)
            public class TasksTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<TasksTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected double getConfiguredGridWeightY() {
                    return -1;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredPreventInitialFocus() {
                    return true;
                }

                @ClassId("24b73347-a392-4f9d-94f0-4a204dab2ed9")
                public class Table extends AbstractTasksTable {
                    @Override
                    public int getMaxRowCount() {
                        return super.getMaxRowCount();
                    }

                    @Override
                    public void reloadData() {
                        fetchTasks();
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
