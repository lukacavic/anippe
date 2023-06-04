package com.velebit.anippe.client.clients;

import com.velebit.anippe.client.clients.ClientForm.MainBox.CancelButton;
import com.velebit.anippe.client.clients.ClientForm.MainBox.GroupBox;
import com.velebit.anippe.client.clients.ClientForm.MainBox.OkButton;
import com.velebit.anippe.shared.clients.ClientFormData;
import com.velebit.anippe.shared.clients.IClientService;
import com.velebit.anippe.shared.country.CountryLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ClientFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ClientForm extends AbstractForm {

    private Integer clientId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Client");
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

    public GroupBox.CountryField getCountryField() {
        return getFieldByClass(GroupBox.CountryField.class);
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.PhoneField getPhoneField() {
        return getFieldByClass(GroupBox.PhoneField.class);
    }

    public GroupBox.PostalCodeField getPostalCodeField() {
        return getFieldByClass(GroupBox.PostalCodeField.class);
    }

    public GroupBox.WebsiteField getWebsiteField() {
        return getFieldByClass(GroupBox.WebsiteField.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
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
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1125)
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

            @Order(1187)
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

            @Order(1250)
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

            @Order(1500)
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

            @Order(1750)
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

            @Order(2000)
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

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ClientFormData formData = new ClientFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientService.class).prepareCreate(formData);
            importFormData(formData);

        }

        @Override
        protected void execStore() {
            ClientFormData formData = new ClientFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ClientFormData formData = new ClientFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            setSubTitle(TEXTS.get("ViewEntry"));
        }

        @Override
        protected void execStore() {
            ClientFormData formData = new ClientFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientService.class).store(formData);
            importFormData(formData);
        }
    }
}
