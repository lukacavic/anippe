package com.velebit.anippe.client.email;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.email.EmailQueueTablePage.Table;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.utilities.ColorUtility;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.email.EmailQueueTablePageData;
import com.velebit.anippe.shared.email.IEmailQueueService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data(EmailQueueTablePageData.class)
public class EmailQueueTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IEmailQueueService.class).getEmailQueueTableData(filter));
    }

    @Override
    protected void execDataChanged(Object... dataTypes) {
        reloadPage();
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Email;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Email;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("EmailLog");
    }

    @Override
    protected Class<? extends ISearchForm> getConfiguredSearchForm() {
        return EmailQueueSearchForm.class;
    }

    public class Table extends AbstractTable {

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public EmailQueueIdColumn getEmailQueueIdColumn() {
            return getColumnSet().getColumnByClass(EmailQueueIdColumn.class);
        }

        public CreatedAtColumn getCreatedAtColumn() {
            return getColumnSet().getColumnByClass(CreatedAtColumn.class);
        }

        public SubjectColumn getSubjectColumn() {
            return getColumnSet().getColumnByClass(SubjectColumn.class);
        }

        public ContentColumn getContentColumn() {
            return getColumnSet().getColumnByClass(ContentColumn.class);
        }

        public AttachmentsCountColumn getAttachmentsCountColumn() {
            return getColumnSet().getColumnByClass(AttachmentsCountColumn.class);
        }

        public CCReceiversColumn getCCReceiversColumn() {
            return getColumnSet().getColumnByClass(CCReceiversColumn.class);
        }

        public BCCReceiversColumn getBCCReceiversColumn() {
            return getColumnSet().getColumnByClass(BCCReceiversColumn.class);
        }

        public UserColumn getUserColumn() {
            return getColumnSet().getColumnByClass(UserColumn.class);
        }

        public ErrorColumn getErrorColumn() {
            return getColumnSet().getColumnByClass(ErrorColumn.class);
        }

        public ReceiversColumn getReceiversColumn() {
            return getColumnSet().getColumnByClass(ReceiversColumn.class);
        }

        public SenderColumn getSenderColumn() {
            return getColumnSet().getColumnByClass(SenderColumn.class);
        }

        public StatusColumn getStatusColumn() {
            return getColumnSet().getColumnByClass(StatusColumn.class);
        }

        @Order(1000)
        public class DeleteMenu extends AbstractDeleteMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
            }

            @Override
            protected void execAction() {
                List<ITableRow> rows = getSelectedRows().stream().filter(r -> getStatusColumn().getValue(r).equals(Constants.EmailStatus.PREPARE_SEND) || getStatusColumn().getValue(r).equals(Constants.EmailStatus.ERROR)).collect(Collectors.toList());

                if (CollectionUtility.isEmpty(rows))
                    return;

                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(IEmailQueueService.class).deleteQueuedEmails(getEmailQueueIdColumn().getValues(rows));

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }

            }
        }

        @Order(0)
        public class EmailQueueIdColumn extends AbstractIDColumn {

        }

        @Order(250)
        public class AttachmentsCountColumn extends AbstractIntegerColumn {

            @Override
            protected int getConfiguredWidth() {
                return 60;
            }

            @Override
            public boolean isFixedPosition() {
                return true;
            }

            @Override
            public boolean isFixedWidth() {
                return true;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
                Integer attachmentsCount = getValue(row);
                cell.setText("");

                if (attachmentsCount > 0) {
                    cell.setText(getValue(row).toString());
                    cell.setIconId(FontIcons.Paperclip);

                    String orange = BEANS.get(ColorUtility.class).getOrange();
                    cell.setBackgroundColor(orange);
                }
            }
        }

        @Order(500)
        public class CreatedAtColumn extends AbstractDateTimeColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("CreatedAt");
            }

            @Override
            protected int getConfiguredWidth() {
                return 130;
            }
        }

        @Order(562)
        public class UserColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("User");
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
                if (getValue(row) == null) {
                    cell.setText(TEXTS.get("System"));
                }
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(625)
        public class SenderColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Sender");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(687)
        public class ReceiversColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Receivers");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(718)
        public class CCReceiversColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("CCReceivers");
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(734)
        public class BCCReceiversColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("BCCReceivers");
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(750)
        public class SubjectColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Subject");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(875)
        public class ContentColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Content");
            }

            @Override
            protected int getConfiguredWidth() {
                return 250;
            }
        }

        @Order(1000)
        public class StatusColumn extends AbstractIntegerColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Status");
            }

            @Override
            protected int getConfiguredWidth() {
                return 150;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
                Integer statusId = getValue(row);
                cell.setText("");

                if (statusId.equals(Constants.EmailStatus.PREPARE_SEND)) {
                    cell.setText(TEXTS.get("PreparingToSend"));
                    cell.setIconId(FontIcons.Email);
                } else if (statusId.equals(Constants.EmailStatus.SENT)) {
                    cell.setText(TEXTS.get("Sent"));
                    String green = BEANS.get(ColorUtility.class).getGreen();
                    cell.setBackgroundColor(green);
                    cell.setIconId(FontIcons.Check);
                } else if (statusId.equals(Constants.EmailStatus.ERROR)) {
                    cell.setText(TEXTS.get("ErrorSending"));
                    String red = BEANS.get(ColorUtility.class).getRed();
                    cell.setBackgroundColor(red);
                    cell.setIconId(FontIcons.ExclamationMarkBold);
                }
            }
        }

        @Order(2000)
        public class ErrorColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Error");
            }

            @Override
            protected int getConfiguredWidth() {
                return 200;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
                Integer statusId = getStatusColumn().getValue(row);
                cell.setText("");

                if (!StringUtility.isNullOrEmpty(getValue(row)) && statusId.equals(Constants.EmailStatus.ERROR)) {
                    cell.setTooltipText(getValue(row));
                    String red = BEANS.get(ColorUtility.class).getRed();
                    cell.setBackgroundColor(red);
                    cell.setText(getValue(row));
                }
            }
        }

    }
}
