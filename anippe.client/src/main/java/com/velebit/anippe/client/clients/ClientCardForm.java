package com.velebit.anippe.client.clients;

import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.common.menus.AbstractSendEmailMenu;
import com.velebit.anippe.client.contacts.ContactsForm;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.DocumentsForm;
import com.velebit.anippe.client.projects.SupportForm;
import com.velebit.anippe.client.projects.TasksForm;
import com.velebit.anippe.client.reminders.RemindersForm;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.vaults.VaultsForm;
import com.velebit.anippe.shared.clients.IClientService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.shareds.ClientCardFormData;
import com.velebit.anippe.shared.shareds.IClientCardService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.MouseButton;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.tree.AbstractTree;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.AbstractTreeBox;
import org.eclipse.scout.rt.client.ui.form.fields.wrappedform.AbstractWrappedFormField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.TypeCastUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ClientCardFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ClientCardForm extends AbstractForm {

    private Integer clientId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @Override
    public Object computeExclusiveKey() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("ViewEntry");
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ClientCard");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public MainBox.MainGroupBox.FormContainerField getFormContainerField() {
        return getFieldByClass(MainBox.MainGroupBox.FormContainerField.class);
    }

    public MainBox.MainGroupBox getMainGroupBox() {
        return getFieldByClass(MainBox.MainGroupBox.class);
    }

    public MainBox.MainGroupBox.NavigationTreeField getNavigationTreeField() {
        return getFieldByClass(MainBox.MainGroupBox.NavigationTreeField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(-1000)
        public class MainGroupBox extends org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox {
            @Override
            public boolean isLabelVisible() {
                return false;
            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 4;
            }

            @Order(0)
            public class NavigationTreeField extends AbstractTreeBox<String> {

                @ClassId("3cb5d63d-f6ad-4523-8dd6-b0f15c346321")
                public class Tree extends AbstractTree {
                    @Override
                    protected void execNodeClick(ITreeNode node, MouseButton mouseButton) {
                        super.execNodeClick(node, mouseButton);

                        String primaryKey = TypeCastUtility.castValue(node.getPrimaryKey(), String.class);
                        if (primaryKey.equals("TASKS")) {
                            TasksForm form = new TasksForm();
                            form.setProject(new Project());
                            form.setShowOnStart(false);
                            form.setModal(false);
                            form.startNew();
                            getFormContainerField().setInnerForm(form);
                        } else if (primaryKey.equals("CONTACTS")) {
                            ContactsForm form = new ContactsForm();
                            form.setClientId(getClientId());
                            form.setShowOnStart(false);
                            form.setModal(false);
                            form.start();
                            form.fetchContacts();
                            getFormContainerField().setInnerForm(form);
                        } else if (primaryKey.equals("DOCUMENTS")) {
                            DocumentsForm form = new DocumentsForm();
                            form.setClientId(getClientId());
                            form.setShowOnStart(false);
                            form.setModal(false);
                            form.startNew();
                            getFormContainerField().setInnerForm(form);
                        } else if (primaryKey.equals("TICKETS")) {
                            SupportForm form = new SupportForm();
                            form.setClientId(getClientId());
                            form.setShowOnStart(false);
                            form.setModal(false);
                            form.startClient();
                            getFormContainerField().setInnerForm(form);
                        } else if (primaryKey.equals("VAULT")) {
                            VaultsForm form = new VaultsForm();
                            form.setRelatedId(getClientId());
                            form.setRelatedType(Constants.Related.CLIENT);
                            form.setShowOnStart(false);
                            form.setModal(false);
                            form.start();
                            form.fetchVaults();
                            getFormContainerField().setInnerForm(form);
                        } else if (primaryKey.equals("REMINDERS")) {
                            RemindersForm form = new RemindersForm();
                            form.setRelatedId(getClientId());
                            form.setRelatedType(Constants.Related.CLIENT);
                            form.setShowOnStart(false);
                            form.setModal(false);
                            form.startNew();
                            getFormContainerField().setInnerForm(form);
                        }
                    }
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                    return NavigationLookupCall.class;
                }

                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }
            }

            @Order(100)
            public class FormContainerField extends AbstractWrappedFormField<IForm> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredFillVertical() {
                    return true;
                }

                @Override
                protected boolean getConfiguredGridUseUiHeight() {
                    return true;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 3;
                }
            }
        }


        @Order(0)
        public class ActionsMenu extends AbstractActionsMenu {

            @Override
            protected boolean getConfiguredVisible() {
                return true;
            }

            @Order(0)
            public class EditMenu extends AbstractEditMenu {

                @Override
                protected void execAction() {
                    ClientForm form = new ClientForm();
                    form.setClientId(getClientId());
                    form.startModify();
                    form.waitFor();
                    if (form.isFormStored()) {
                        NotificationHelper.showSaveSuccessNotification();
                    }
                }
            }

            @Order(1000)
            public class DeleteMenu extends AbstractDeleteMenu {


                @Override
                protected void execAction() {
                    if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                        BEANS.get(IClientService.class).delete(getClientId());
                        NotificationHelper.showDeleteSuccessNotification();

                        doClose();

                    }
                }
            }
        }

        @Order(1000)
        public class SendEmailMenu extends AbstractSendEmailMenu {

            @Override
            protected void execAction() {
                EmailForm form = new EmailForm();
                form.startNew();
                form.waitFor();
            }
        }

        @Order(2000)
        public class NewTaskMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewTask");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Tasks;
            }

            @Override
            protected void execAction() {
                TaskForm form = new TaskForm();
                form.setRelatedType(Constants.Related.CLIENT);
                form.setRelatedId(getClientId());
                form.startNew();
                form.waitFor();
                if (form.isFormStored()) {

                }
            }
        }

        @Order(3000)
        public class PinMenu extends AbstractMenu {
            @Override
            protected String getConfiguredIconId() {
                return FontIcons.MapPin;
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected void execAction() {

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

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ClientCardFormData formData = new ClientCardFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientCardService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected boolean getConfiguredOpenExclusive() {
            return true;
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getNavigationTreeField().getTree().selectFirstNode();
            getFormContainerField().setInnerForm(new VaultsForm());
        }

        @Override
        protected void execStore() {
            ClientCardFormData formData = new ClientCardFormData();
            exportFormData(formData);
            formData = BEANS.get(IClientCardService.class).store(formData);
            importFormData(formData);
        }
    }
}
