package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.leads.LeadToClientForm.MainBox.CancelButton;
import com.velebit.anippe.client.leads.LeadToClientForm.MainBox.GroupBox;
import com.velebit.anippe.client.leads.LeadToClientForm.MainBox.GroupBox.NameField;
import com.velebit.anippe.client.leads.LeadToClientForm.MainBox.OkButton;
import com.velebit.anippe.shared.country.CountryLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.ILeadToClientService;
import com.velebit.anippe.shared.leads.LeadToClientFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = LeadToClientFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadToClientForm extends AbstractForm {

    private Integer leadId;

    private Integer clientId; //ID of created client;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @FormData
    public Integer getLeadId() {
        return leadId;
    }

    @FormData
    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("NewClient");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserPlus;
    }

    public GroupBox.AddressField getAddressField() {
        return getFieldByClass(GroupBox.AddressField.class);
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

    public GroupBox.CityField getCityField() {
        return getFieldByClass(GroupBox.CityField.class);
    }

    public GroupBox.CompanyField getCompanyField() {
        return getFieldByClass(GroupBox.CompanyField.class);
    }

    public GroupBox.CountryField getCountryField() {
        return getFieldByClass(GroupBox.CountryField.class);
    }

    public GroupBox.EmailField getEmailField() {
        return getFieldByClass(GroupBox.EmailField.class);
    }

    public NameField getNameField() {
        return getFieldByClass(NameField.class);
    }

    public GroupBox.PhoneField getPhoneField() {
        return getFieldByClass(GroupBox.PhoneField.class);
    }

    public GroupBox.PositionField getPositionField() {
        return getFieldByClass(GroupBox.PositionField.class);
    }

    public GroupBox.PostalCodeField getPostalCodeField() {
        return getFieldByClass(GroupBox.PostalCodeField.class);
    }

    public GroupBox.TransferAttachmentsToClientField getTransferAttachmentsToClientField() {
        return getFieldByClass(GroupBox.TransferAttachmentsToClientField.class);
    }

    public GroupBox.TransferNotesToClientField getTransferNotesToClientField() {
        return getFieldByClass(GroupBox.TransferNotesToClientField.class);
    }

    public GroupBox.WebsiteField getWebsiteField() {
        return getFieldByClass(GroupBox.WebsiteField.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("ConvertLeadToClient");
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        public int getLabelWidthInPixel() {
            return 600;
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

            @Order(3000)
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

            @Order(4000)
            public class EmailField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Email");
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

            @Order(5000)
            public class CompanyField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Company");
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

            @Order(6000)
            public class PhoneField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Phone");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(7000)
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

            @Order(8000)
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

            @Order(9000)
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

            @Order(10000)
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

            @Order(11000)
            public class CountryField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Country");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return CountryLookupCall.class;
                }
            }

            @Order(12000)
            public class TransferNotesToClientField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("TransferNotesToClient");
                }
            }

            @Order(13000)
            public class TransferAttachmentsToClientField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("TransferAttachmentsToClient");
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
            LeadToClientFormData formData = new LeadToClientFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadToClientService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadToClientFormData formData = new LeadToClientFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadToClientService.class).create(formData);
            importFormData(formData);
        }
    }

}
