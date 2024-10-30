package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.common.fields.AbstractEmailField;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.leads.LeadForm.MainBox.CancelButton;
import com.velebit.anippe.client.leads.LeadForm.MainBox.GroupBox;
import com.velebit.anippe.client.leads.LeadForm.MainBox.GroupBox.*;
import com.velebit.anippe.client.leads.LeadForm.MainBox.OkButton;
import com.velebit.anippe.client.leads.LeadViewForm.MainBox.MainTabBox.OverviewBox.GeneralInformationBox.AssignedUserField;
import com.velebit.anippe.shared.country.CountryLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.LeadFormData;
import com.velebit.anippe.shared.leads.LeadSourceLookupCall;
import com.velebit.anippe.shared.leads.LeadStatusLookupCall;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = LeadFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadForm extends AbstractForm {
    private Integer leadId;

    private Integer clientId;
    private boolean lost = false; // is lead lost?
    private Integer projectId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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
    public Object computeExclusiveKey() {
        return getLeadId();
    }

    @FormData
    public boolean isLost() {
        return lost;
    }

    @FormData
    public void setLost(boolean lost) {
        this.lost = lost;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Lead");
    }

    public AddressField getAddressField() {
        return getFieldByClass(AddressField.class);
    }

    public AssignedUserField getAssignedUserField() {
        return getFieldByClass(AssignedUserField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserPlus;
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public CityField getCityField() {
        return getFieldByClass(CityField.class);
    }

    public CompanyField getCompanyField() {
        return getFieldByClass(CompanyField.class);
    }

    public CountryField getCountryField() {
        return getFieldByClass(CountryField.class);
    }

    public DescriptionField getDescriptionField() {
        return getFieldByClass(DescriptionField.class);
    }

    public EmailField getEmailField() {
        return getFieldByClass(EmailField.class);
    }

    public LastContactAtField getLastContactAtField() {
        return getFieldByClass(LastContactAtField.class);
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public PhoneField getPhoneField() {
        return getFieldByClass(PhoneField.class);
    }

    public PositionField getPositionField() {
        return getFieldByClass(PositionField.class);
    }

    public PostalCodeField getPostalCodeField() {
        return getFieldByClass(PostalCodeField.class);
    }

    public WebsiteField getWebsiteField() {
        return getFieldByClass(WebsiteField.class);
    }

    @FormData
    public Integer getLeadId() {
        return leadId;
    }

    @FormData
    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public SourceField getSourceField() {
        return getFieldByClass(SourceField.class);
    }

    public StatusField getStatusField() {
        return getFieldByClass(StatusField.class);
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(0)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected void execInitField() {
                super.execInitField();

                getFields().forEach(f -> f.setDisabledStyle(DISABLED_STYLE_READ_ONLY));
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 2;
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected String getConfiguredFont() {
                    return "BOLD";
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

            @Order(2000)
            public class PositionField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Position");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(3000)
            public class EmailField extends AbstractEmailField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Email");
                }

                @Override
                protected String execValidateValue(String rawValue) {
                    if (rawValue != null) {
                        if (BEANS.get(ILeadService.class).isEmailUnique(rawValue, getLeadId() != null ? getLeadId() : null)) {
                            throw new VetoException(TEXTS.get("EmailIsInUse0"));
                        }
                    }

                    return rawValue;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(4000)
            public class WebsiteField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Website");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(5000)
            public class PhoneField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Phone");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }

                @Override
                protected String execValidateValue(String rawValue) {
                    if (rawValue != null) {
                        if (BEANS.get(ILeadService.class).isPhoneUnique(rawValue, getLeadId() != null ? getLeadId() : null)) {
                            throw new VetoException(TEXTS.get("PhoneIsInUse"));
                        }
                    }

                    return rawValue;
                }
            }

            @Order(6000)
            public class AddressField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Address");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(7000)
            public class CityField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("City");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(8000)
            public class PostalCodeField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("PostalCode");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(9000)
            public class CountryField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Country");
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return CountryLookupCall.class;
                }
            }

            @Order(10000)
            public class CompanyField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Company");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(10500)
            public class LastContactAtField extends AbstractDateTimeField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("LastContact");
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }
            }

            @Order(11000)
            public class DescriptionField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                protected double getConfiguredGridWeightY() {
                    return 0;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

            }

            @Order(11500)
            public class SourceField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Source0");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    LeadSourceLookupCall c = (LeadSourceLookupCall) call;
                    c.setProjectId(getProjectId());
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return LeadSourceLookupCall.class;
                }
            }

            @Order(11750)
            public class StatusField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Status");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    LeadStatusLookupCall c = (LeadStatusLookupCall) call;
                    c.setProjectId(getProjectId());
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return LeadStatusLookupCall.class;
                }
            }

            @Order(12000)
            public class AssignedUserField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Assigned");
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    UserLookupCall c = (UserLookupCall) call;
                    if (getProjectId() != null) {
                        c.setProjectId(getProjectId());
                    }
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
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
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {

        @Override
        protected boolean getConfiguredOpenExclusive() {
            return true;
        }

        @Override
        protected void execLoad() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).store(formData);
            importFormData(formData);
        }
    }

}
