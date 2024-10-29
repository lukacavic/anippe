package com.velebit.anippe.client.components;

import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.components.AbstractDocumentsGroupBoxData;
import com.velebit.anippe.shared.documents.DocumentsFormData.DocumentsTable.DocumentsTableRowData;
import com.velebit.anippe.shared.documents.IDocumentsService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractObjectColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Arrays;
import java.util.List;

@FormData(value = AbstractDocumentsGroupBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractDocumentsGroupBox extends AbstractGroupBox {

	protected Integer relatedId;
	protected Integer relatedType;

	private List<Integer> temporaryDocumentIds = CollectionUtility.emptyArrayList();

	@FormData
	public List<Integer> getTemporaryDocumentIds() {
		return temporaryDocumentIds;
	}

	@FormData
	public void setTemporaryDocumentIds(List<Integer> temporaryDocumentIds) {
		this.temporaryDocumentIds = temporaryDocumentIds;
	}

	@FormData
	public Integer getRelatedId() {
		return relatedId;
	}

	@FormData
	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	@FormData
	public Integer getRelatedType() {
		return relatedType;
	}

	@FormData
	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	@Override
	protected int getConfiguredGridColumnCount() {
		return 1;
	}

	public DocumentsTableField getDocumentsTableField() {
		return getFieldByClass(DocumentsTableField.class);
	}

	@Override
	protected boolean getConfiguredStatusVisible() {
		return false;
	}

	@Override
	protected String getConfiguredLabel() {
		return TEXTS.get("Documents");
	}

	@Override
	protected String getConfiguredSubLabel() {
		return TEXTS.get("UploadedDocuments");
	}

	@Override
	protected void execDataChanged(Object... dataTypes) {
		fetchDocuments();
	}

	public void fetchDocuments() {
		if (getRelatedId() == null && getRelatedType() == null)
			return;

		List<DocumentsTableRowData> rowData = BEANS.get(IDocumentsService.class).fetchDocuments(relatedId, relatedType);
		getDocumentsTableField().getTable().importFromTableRowBeanData(rowData, DocumentsTableRowData.class);

		int documentsCount = getDocumentsTableField().getTable().getRowCount();
		AbstractDocumentsGroupBox.this.setLabel(AbstractDocumentsGroupBox.this.getConfiguredLabel() + " (" + documentsCount + ")");
	}


	@Order(-1500)
	public class UploadMenu extends AbstractAddMenu {
		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Upload");
		}

		@Override
		protected String getConfiguredIconId() {
			return FontIcons.Paperclip;
		}

		@Override
		protected void execAction() {
			if (getRelatedId() == null) {
				NotificationHelper.showErrorNotification(TEXTS.get("SaveChangesBeforeUploadingDocument"));
				return;
			}

			List<BinaryResource> uploadedFiles = new FileChooser(true).startChooser();
			if (CollectionUtility.isEmpty(uploadedFiles)) return;

			List<Attachment> attachments = CollectionUtility.emptyArrayList();

			for (BinaryResource binaryResource : uploadedFiles) {
				Attachment attachment = new Attachment();
				attachment.setRelatedId(getRelatedId());
				attachment.setRelatedTypeId(getRelatedType());
				attachment.setAttachment(binaryResource.getContent());
				attachment.setName(binaryResource.getFilename());
				attachment.setFileExtension(IOUtility.getFileExtension(binaryResource.getFilename()));
				attachment.setFileName(binaryResource.getFilename());

				attachments.add(attachment);
			}

			BEANS.get(IDocumentsService.class).upload(attachments);

			fetchDocuments();

			NotificationHelper.showSaveSuccessNotification();
		}
	}


	@Order(1000)
	public class DocumentsTableField extends AbstractTableField<DocumentsTableField.Table> {

		@Override
		protected int getConfiguredGridH() {
			return 6;
		}

		@Override
		protected int getConfiguredGridW() {
			return 6;
		}

		@Override
		protected boolean getConfiguredStatusVisible() {
			return false;
		}

		public class Table extends AbstractTable {

			@Override
			protected void execDecorateRow(ITableRow row) {
				super.execDecorateRow(row);

				row.setCssClass("clients-row");
			}

			@Override
			protected boolean getConfiguredAutoResizeColumns() {
				return true;
			}

			public CreatedAtColumn getCreatedAtColumn() {
				return getColumnSet().getColumnByClass(CreatedAtColumn.class);
			}

			public DocumentIdColumn getDocumentIdColumn() {
				return getColumnSet().getColumnByClass(DocumentIdColumn.class);
			}

			public DocumentColumn getDocumentColumn() {
				return getColumnSet().getColumnByClass(DocumentColumn.class);
			}

			public NameColumn getNameColumn() {
				return getColumnSet().getColumnByClass(NameColumn.class);
			}

			public SizeColumn getSizeColumn() {
				return getColumnSet().getColumnByClass(SizeColumn.class);
			}

			public TypeColumn getTypeColumn() {
				return getColumnSet().getColumnByClass(TypeColumn.class);
			}

			public UpdatedAtColumn getUpdatedAtColumn() {
				return getColumnSet().getColumnByClass(UpdatedAtColumn.class);
			}

			public UserColumn getUserColumn() {
				return getColumnSet().getColumnByClass(UserColumn.class);
			}

			@Order(1000)
			public class DocumentIdColumn extends AbstractIDColumn {

			}

			@Order(1500)
			public class DocumentColumn extends AbstractObjectColumn {
				@Override
				protected boolean getConfiguredDisplayable() {
					return false;
				}
			}

			@Order(2000)
			public class NameColumn extends AbstractStringColumn {

				@Override
				protected String getConfiguredHeaderText() {
					return TEXTS.get("Name");
				}

				@Override
				protected int getConfiguredWidth() {
					return 100;
				}

				@Override
				protected void execDecorateCell(Cell cell, ITableRow row) {
					super.execDecorateCell(cell, row);

					String createdAt = new PrettyTime().format(getCreatedAtColumn().getValue(row));
					String creator = getUserColumn().getValue(row);

					IHtmlContent sublabel = HTML.span(StringUtility.join(", ", creator, createdAt));

					String content = HTML.fragment(
							HTML.span(getValue(row)).cssClass(ICustomCssClasses.TABLE_HTML_CELL_HEADING),
							HTML.br(),
							HTML.span(sublabel).cssClass(ICustomCssClasses.TABLE_HTML_CELL_SUB_HEADING)).toHtml();

					cell.setText(content);
				}

				@Override
				protected boolean getConfiguredHtmlEnabled() {
					return true;
				}
			}

			@Order(3000)
			public class UserColumn extends AbstractStringColumn {
				@Override
				protected boolean getConfiguredDisplayable() {
					return false;
				}

				@Override
				protected String getConfiguredHeaderText() {
					return TEXTS.get("UploadedBy");
				}

				@Override
				protected int getConfiguredWidth() {
					return 100;
				}
			}

			@Order(4000)
			public class CreatedAtColumn extends AbstractDateTimeColumn {
				@Override
				protected boolean getConfiguredDisplayable() {
					return false;
				}

				@Override
				protected String getConfiguredHeaderText() {
					return TEXTS.get("CreatedAt");
				}

				@Override
				protected int getConfiguredWidth() {
					return 100;
				}
			}

			@Order(4500)
			public class UpdatedAtColumn extends AbstractDateTimeColumn {
                @Override
                protected boolean getConfiguredDisplayable() {
                    return false;
                }

                @Override
				protected String getConfiguredHeaderText() {
					return TEXTS.get("UpdatedAt");
				}

				@Override
				protected int getConfiguredWidth() {
					return 100;
				}
			}

			@Order(5000)
			public class TypeColumn extends AbstractStringColumn {
				@Override
				public boolean isFixedWidth() {
					return true;
				}

				@Override
				public boolean isFixedPosition() {
					return true;
				}

				@Override
				protected String getConfiguredHeaderText() {
					return TEXTS.get("Type");
				}

				@Override
				protected int getConfiguredWidth() {
					return 100;
				}
			}

			@Order(6000)
			public class SizeColumn extends AbstractIntegerColumn {
				@Override
				public boolean isFixedWidth() {
					return true;
				}

				@Override
				public boolean isFixedPosition() {
					return true;
				}

				@Override
				protected String getConfiguredHeaderText() {
					return TEXTS.get("Size");
				}

				@Override
				protected int getConfiguredWidth() {
					return 120;
				}

				@Override
				protected void execDecorateCell(Cell cell, ITableRow row) {
					cell.setText(FileUtils.byteCountToDisplaySize(getValue(row)));
				}
			}

			@Order(3000)
			public class ViewMenu extends AbstractOpenMenu {

				@Override
				protected void execAction() {
					IDesktop desktop = IDesktop.CURRENT.get();
					BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

					if (desktop != null && binaryResource != null) {
						desktop.openUri(binaryResource, OpenUriAction.OPEN);
					}
				}
			}

			@Order(3050)
			public class ActionsMenu extends AbstractActionsMenu {

				@Order(2100)
				public class DownloadMenu extends AbstractDownloadMenu {

					@Override
					protected void execAction() {
						IDesktop desktop = IDesktop.CURRENT.get();
						BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

						if (desktop != null && binaryResource != null) {
							desktop.openUri(binaryResource, OpenUriAction.DOWNLOAD);
						}
					}
				}

				@Order(2150)
				public class SendEmailMenu extends AbstractMenu {

					@Override
					protected String getConfiguredIconId() {
						return FontIcons.Email;
					}

					@Override
					protected String getConfiguredText() {
						return TEXTS.get("SendEmail");
					}

					@Override
					protected void execAction() {
						BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

						if (binaryResource == null)
							return;

						EmailForm form = new EmailForm();
						form.setAttachments(Arrays.asList(binaryResource));
						form.startNew();
						form.waitFor();
					}
				}

				@Order(4000)
				public class DeleteMenu extends AbstractDeleteMenu {

					@Override
					protected void execAction() {
						if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
							BEANS.get(IDocumentsService.class).delete(getDocumentIdColumn().getSelectedValues());
							NotificationHelper.showSaveSuccessNotification();

							fetchDocuments();
						}
					}
				}
			}
		}

		@Override
		protected boolean getConfiguredLabelVisible() {
			return false;
		}
	}

}
