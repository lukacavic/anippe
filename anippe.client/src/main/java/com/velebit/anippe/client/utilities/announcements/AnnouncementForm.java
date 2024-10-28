package com.velebit.anippe.client.utilities.announcements;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.utilities.announcements.AnnouncementForm.MainBox.CancelButton;
import com.velebit.anippe.client.utilities.announcements.AnnouncementForm.MainBox.GroupBox;
import com.velebit.anippe.client.utilities.announcements.AnnouncementForm.MainBox.MarkAsReadMenu;
import com.velebit.anippe.client.utilities.announcements.AnnouncementForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.utilities.announcements.AnnouncementFormData;
import com.velebit.anippe.shared.utilities.announcements.IAnnouncementService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = AnnouncementFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AnnouncementForm extends AbstractForm {

    private Integer announcementId;

    @FormData
    public Integer getAnnouncementId() {
        return announcementId;
    }

    @FormData
    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Announcement");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Bell;
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

    public GroupBox.ContentField getContentField() {
        return getFieldByClass(GroupBox.ContentField.class);
    }

    public GroupBox.SubjectField getSubjectField() {
        return getFieldByClass(GroupBox.SubjectField.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public void startPreview() {
        startInternal(new PreviewHandler());
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
                return 3;
            }

            @Order(1000)
            public class SubjectField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Subject");
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 3;
                }

                @Override
                public int getLabelWidthInPixel() {
                    return 130;
                }

                @Override
                protected String getConfiguredFieldStyle() {
                    return FIELD_STYLE_CLASSIC;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }
            }

            @Order(2000)
            public class ContentField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Content");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 5;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                public int getLabelWidthInPixel() {
                    return 130;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 3;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }
            }

        }

        @Order(1000)
        public class MarkAsReadMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("MarkAsRead");
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Check;
            }

            @Override
            protected void execAction() {
                BEANS.get(IAnnouncementService.class).markAsRead(getAnnouncementId());
                setFormStored(true);
                doClose();
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
            AnnouncementFormData formData = new AnnouncementFormData();
            exportFormData(formData);
            formData = BEANS.get(IAnnouncementService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            AnnouncementFormData formData = new AnnouncementFormData();
            exportFormData(formData);
            formData = BEANS.get(IAnnouncementService.class).create(formData);
            importFormData(formData);
        }
    }

    public class PreviewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            AnnouncementFormData formData = new AnnouncementFormData();
            exportFormData(formData);
            formData = BEANS.get(IAnnouncementService.class).preview(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getSubjectField().setEnabledGranted(false);
            getContentField().setEnabledGranted(false);
            MenuUtility.getMenuByClass(getMainBox(), MarkAsReadMenu.class).setVisible(true);

            getOkButton().setVisible(false);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            AnnouncementFormData formData = new AnnouncementFormData();
            exportFormData(formData);
            formData = BEANS.get(IAnnouncementService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            AnnouncementFormData formData = new AnnouncementFormData();
            exportFormData(formData);
            formData = BEANS.get(IAnnouncementService.class).store(formData);
            importFormData(formData);
        }
    }
}
