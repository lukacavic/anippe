package com.velebit.anippe.client.administration.organisations;

import com.velebit.anippe.client.administration.organisations.OrganisationForm.MainBox.CancelButton;
import com.velebit.anippe.client.administration.organisations.OrganisationForm.MainBox.GroupBox;
import com.velebit.anippe.client.administration.organisations.OrganisationForm.MainBox.OkButton;
import com.velebit.anippe.shared.administration.organisations.IOrganisationService;
import com.velebit.anippe.shared.administration.organisations.OrganisationFormData;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = OrganisationFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OrganisationForm extends AbstractForm {

    private Integer organisationId;

    @FormData
    public Integer getOrganisationId() {
        return organisationId;
    }

    @FormData
    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Organisation");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Building;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
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

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.SubdomainField getSubdomainField() {
        return getFieldByClass(GroupBox.SubdomainField.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }
            }

            @Order(2000)
            public class SubdomainField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Subdomain");
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
            OrganisationFormData formData = new OrganisationFormData();
            exportFormData(formData);
            formData = BEANS.get(IOrganisationService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            OrganisationFormData formData = new OrganisationFormData();
            exportFormData(formData);
            formData = BEANS.get(IOrganisationService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            OrganisationFormData formData = new OrganisationFormData();
            exportFormData(formData);
            formData = BEANS.get(IOrganisationService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            OrganisationFormData formData = new OrganisationFormData();
            exportFormData(formData);
            formData = BEANS.get(IOrganisationService.class).store(formData);
            importFormData(formData);
        }
    }
}
