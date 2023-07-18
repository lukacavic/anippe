package com.velebit.anippe.client.email;

import com.velebit.anippe.client.email.EmailQueueSearchForm.MainBox.CancelButton;
import com.velebit.anippe.client.email.EmailQueueSearchForm.MainBox.OkButton;
import com.velebit.anippe.shared.email.EmailQueueSearchFormData;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.utilities.DateUtility;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.HorizontalGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = EmailQueueSearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EmailQueueSearchForm extends AbstractSearchForm {
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Search");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.MainInformationsBox getMainInformationsBox() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.PeriodSequenceBox getPeriodSequenceBox() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PeriodSequenceBox.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.PeriodSequenceBox.PeriodFromField getPeriodFromField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PeriodSequenceBox.PeriodFromField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.PeriodSequenceBox.PeriodToField getPeriodToField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.PeriodSequenceBox.PeriodToField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.UserField getUserField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.UserField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.HasAttachmentsField getHasAttachmentsField() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.HasAttachmentsField.class);
    }

    public MainBox.MainTabBox.MainInformationsBox.StatusBox getStatusBox() {
        return getFieldByClass(MainBox.MainTabBox.MainInformationsBox.StatusBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(1000)
        public class MainTabBox extends AbstractTabBox {

            @Order(1000)
            public class MainInformationsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SearchParameters");
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 4;
                }

                @Override
                protected Class<? extends IGroupBoxBodyGrid> getConfiguredBodyGrid() {
                    return HorizontalGroupBoxBodyGrid.class;
                }

                @Order(1000)
                public class PeriodSequenceBox extends AbstractSequenceBox {
                    @Override
                    protected boolean getConfiguredLabelVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected boolean getConfiguredAutoCheckFromTo() {
                        return false;
                    }

                    @Order(1000)
                    public class PeriodFromField extends AbstractDateTimeField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("PeriodFrom");
                        }

                        @Override
                        protected int getConfiguredLabelWidthInPixel() {
                            return 70;
                        }

                        @Override
                        protected void execInitField() {
                            setValue(DateUtility.startOfDay());
                        }
                    }

                    @Order(2000)
                    public class PeriodToField extends AbstractDateTimeField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("to");
                        }

                        @Override
                        protected void execInitField() {
                            setValue(DateUtility.endOfDay());
                        }
                    }

                }

                @Order(2000)
                public class UserField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("User");
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return UserLookupCall.class;
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                }

                @Order(3000)
                public class HasAttachmentsField extends AbstractBooleanField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("HasAttachments");
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected void execInitField() {
                        setValue(false);
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 100;
                    }
                }

                @Order(4000)
                public class StatusBox extends AbstractListBox<Integer> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Status");
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected void execInitField() {
                        checkAllKeys();
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 4;
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                        return EmailQueueStatusLookupCall.class;
                    }
                }

            }

        }

        @Order(2000)
        public class OkButton extends AbstractSearchButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractResetButton {

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
        }

        @Override
        protected void execStore() {
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
        }

        @Override
        protected void execStore() {
        }
    }
}
