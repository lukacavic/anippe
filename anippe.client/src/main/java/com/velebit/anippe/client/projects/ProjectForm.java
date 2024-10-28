package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.projects.ProjectForm.MainBox.CancelButton;
import com.velebit.anippe.client.projects.ProjectForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.ProjectForm.MainBox.OkButton;
import com.velebit.anippe.shared.clients.ClientLookupCall;
import com.velebit.anippe.shared.constants.Constants.ProjectType;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IProjectService;
import com.velebit.anippe.shared.projects.ProjectFormData;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ProjectFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ProjectForm extends AbstractForm {

    private Integer projectId;

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Project");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.BoxRemove;
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public GroupBox.ClientField getClientField() {
        return getFieldByClass(GroupBox.ClientField.class);
    }

    public GroupBox.DeadlineField getDeadlineField() {
        return getFieldByClass(GroupBox.DeadlineField.class);
    }

    public GroupBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DescriptionField.class);
    }

    public GroupBox.MembersListBox getMembersListBox() {
        return getFieldByClass(GroupBox.MembersListBox.class);
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.StartDateField getStartDateField() {
        return getFieldByClass(GroupBox.StartDateField.class);
    }

    public GroupBox.StatusField getStatusField() {
        return getFieldByClass(GroupBox.StatusField.class);
    }

    public GroupBox.TypeField getTypeField() {
        return getFieldByClass(GroupBox.TypeField.class);
    }

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 700;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1500)
            public class TypeField extends AbstractSmartField<Integer> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Type");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execInitField() {
                    setValue(ProjectType.FOR_CLIENT);
                }

                @Override
                protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                    return ProjectTypeLookupCall.class;
                }

                @Override
                protected void execChangedValue() {
                    super.execChangedValue();

                    if (getValue() == null) {
                        getClientField().setValue(null);
                        return;
                    }

                    if (getValue().equals(ProjectType.INTERNAL)) {
                        getClientField().setValue(null);
                    }

                    getClientField().setMandatory(getValue().equals(ProjectType.FOR_CLIENT));
                }
            }

            @Order(2000)
            public class ClientField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Client");
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return ClientLookupCall.class;
                }
            }

            @Order(2500)
            public class StatusField extends AbstractSmartField<Integer> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Status");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                    return ProjectStatusLookupCall.class;
                }
            }

            @Order(3000)
            public class MembersListBox extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Users");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 4;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }
            }

            @Order(3500)
            public class StartDateField extends AbstractDateField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("StartDate");
                }
            }

            @Order(3750)
            public class DeadlineField extends AbstractDateField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("DeadlineAt");
                }
            }

            @Order(4000)
            public class DescriptionField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 2;
                }

                @Override
                protected double getConfiguredGridWeightY() {
                    return 0;
                }
            }


        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ProjectFormData formData = new ProjectFormData();
            exportFormData(formData);
            formData = BEANS.get(IProjectService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ProjectFormData formData = new ProjectFormData();
            exportFormData(formData);
            formData = BEANS.get(IProjectService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ProjectFormData formData = new ProjectFormData();
            exportFormData(formData);
            formData = BEANS.get(IProjectService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getClientField().setMandatory(getTypeField().getValue().equals(ProjectType.FOR_CLIENT));
        }

        @Override
        protected void execStore() {
            ProjectFormData formData = new ProjectFormData();
            exportFormData(formData);
            formData = BEANS.get(IProjectService.class).store(formData);
            importFormData(formData);
        }
    }
}
