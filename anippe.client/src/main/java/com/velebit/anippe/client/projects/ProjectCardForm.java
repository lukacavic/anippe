package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.projects.ProjectCardForm.MainBox.CancelButton;
import com.velebit.anippe.client.tasks.AbstractTasksGroupBox;
import com.velebit.anippe.client.tickets.AbstractTicketsGroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IProjectCardService;
import com.velebit.anippe.shared.projects.ProjectCardFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.AbstractAction;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = ProjectCardFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ProjectCardForm extends AbstractForm {

    private Integer projectId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ProjectCard");
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("ViewEntry");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Draver;
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.OverviewBox getOverviewBox() {
        return getFieldByClass(MainBox.MainTabBox.OverviewBox.class);
    }

    public MainBox.MainTabBox.TimesheetsBox getTimesheetsBox() {
        return getFieldByClass(MainBox.MainTabBox.TimesheetsBox.class);
    }

    public MainBox.MainTabBox.TimesheetsBox.TimesheetsTableField getTimesheetsTableField() {
        return getFieldByClass(MainBox.MainTabBox.TimesheetsBox.TimesheetsTableField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(2000)
    public class MainBox extends AbstractGroupBox {
        @Order(1100)
        public class ActionsMenu extends AbstractActionsMenu {
            @Override
            protected boolean getConfiguredVisible() {
                return true;
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Order(1000)
            public class DeleteMenu extends AbstractDeleteMenu {

                @Override
                protected void execAction() {

                }
            }
        }

        @Order(1000)
        public class NewTaskMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewTask");
            }
            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }
            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Plus;
            }

            @Override
            protected void execAction() {

            }
        }
        @Order(1000)
        public class MainTabBox extends AbstractTabBox {

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class OverviewBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Overview");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }
            }

            @Order(2000)
            public class TasksBox extends AbstractTasksGroupBox {

                @Override
                protected void reloadTasks() {

                }
            }

            @Order(3000)
            public class TimesheetsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Timesheets");
                }
                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }
                @Order(1000)
                public class TimesheetsTableField extends AbstractTableField<TimesheetsTableField.Table> {
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

                    @ClassId("cbea9e26-5861-43ae-9bdc-4622302f2131")
                    public class Table extends AbstractTable {

                    }
                }
            }

            @Order(4000)
            public class TicketsBox extends AbstractTicketsGroupBox {

            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {
            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }
        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ProjectCardFormData formData = new ProjectCardFormData();
            exportFormData(formData);
            formData = BEANS.get(IProjectCardService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ProjectCardFormData formData = new ProjectCardFormData();
            exportFormData(formData);
            formData = BEANS.get(IProjectCardService.class).create(formData);
            importFormData(formData);
        }
    }

}
