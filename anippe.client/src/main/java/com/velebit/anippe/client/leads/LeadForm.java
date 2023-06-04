package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.common.fields.AbstractEmailField;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.leads.LeadForm.MainBox.ActionsMenu;
import com.velebit.anippe.client.leads.LeadForm.MainBox.CancelButton;
import com.velebit.anippe.client.leads.LeadForm.MainBox.EditMenu;
import com.velebit.anippe.client.leads.LeadForm.MainBox.OkButton;
import com.velebit.anippe.client.tasks.AbstractTasksGroupBox;
import com.velebit.anippe.shared.country.CountryLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.LeadFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSaveButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = LeadFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadForm extends AbstractForm {
    private Integer leadId;

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Lead");
    }

    public MainBox.MainTabBox.MainInformationsBox.AddressField getAddressField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.AddressField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
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

    public MainBox.MainTabBox.MainInformationsBox.CityField getCityField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.CityField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.CompanyField getCompanyField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.CompanyField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.CountryField getCountryField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.CountryField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.DescriptionField getDescriptionField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.DescriptionField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.EmailField getEmailField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.EmailField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.LastContactAtField getLastContactAtField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.LastContactAtField.class);
    }


    public MainBox.MainTabBox.MainInformationsBox getMainInformationsBox() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.NameField getNameField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.NameField.class);
    }


    public MainBox.MainTabBox.MainInformationsBox.PhoneField getPhoneField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PhoneField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.PositionField getPositionField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PositionField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.PostalCodeField getPostalCodeField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PostalCodeField.class);
    }

    public MainBox.MainTabBox.TasksBox getTasksBox() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.WebsiteField getWebsiteField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.WebsiteField.class);
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

    public void startNew() {
        startInternal(new NewHandler());
    }

    public void setLabels() {
        //Integer tasksCount = getTasksBox().getTasksTableField().getTable().getRowCount();
        //getTasksBox().setLabel(getTasksBox().getConfiguredLabel() + " (" + tasksCount + ")");
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {


        @Order(1000)
        public class ActionsMenu extends AbstractActionsMenu {
            @Order(1000)
            public class MarkAsLostMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("MarkAsLost");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.UserMinus;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(2000)
            public class MarkAsJunkMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("MarkAsJunk");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Remove;
                }

                @Override
                protected void execAction() {

                }
            }

            @Order(3000)
            public class DeleteMenu extends AbstractDeleteMenu {

                @Override
                protected void execAction() {

                }
            }
        }

        @Order(1500)
        public class MainTabBox extends AbstractTabBox {

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class MainInformationsBox extends AbstractGroupBox {

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("MainInformations");
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
                        return 2;
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


            }

            @Order(2000)
            public class TasksBox extends AbstractTasksGroupBox {

                @Override
                protected void reloadTasks() {

                }
            }


        }


        @Order(2000)
        public class ConvertToCustomerButton extends AbstractButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("ConvertToCustomer");
            }

            @Override
            protected String getConfiguredCssClass() {
                return "greenbutton";
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.UserPlus;
            }

            @Override
            protected boolean getConfiguredVisible() {
                return true;
            }

            @Override
            protected void execClickAction() {

            }
        }

        @Order(3000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected void execAction() {

            }
        }


        @Order(1750)
        public class SaveButton extends AbstractSaveButton {

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("SaveAndClose");
            }
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

            MenuUtility.getMenuByClass(getMainBox(), ActionsMenu.class).setVisible(true);
            getTasksBox().setVisible(true);

            setLabels();
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getTasksBox().setVisible(true);

            MenuUtility.getMenuByClass(getMainBox(), ActionsMenu.class).setVisible(true);
            MenuUtility.getMenuByClass(getMainBox(), EditMenu.class).setVisible(true);
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
