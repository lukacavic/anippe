package com.velebit.anippe.server.attachments;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.config.AppFileRootDirectoryOrganisationsConfigProperty;
import com.velebit.anippe.server.utilities.UploadUtility;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.AttachmentRequest;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.attachments.UploadedFile;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AttachmentService extends AbstractService implements IAttachmentService {

	@Override
	public BinaryResource download(Integer attachmentId) {
		UploadedFile uploadedFile = new UploadedFile();

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT file_name, file_name_on_disk ");
		varname1.append("FROM   attachments ");
		varname1.append("WHERE  id = :attachmentId ");
		varname1.append("INTO   :{uploadedFile.fileName}, :{uploadedFile.fileNameOnDisk} ");
		SQL.selectInto(varname1.toString(), new NVPair("uploadedFile", uploadedFile), new NVPair("attachmentId", attachmentId));

		String filePath = getRootPath() + uploadedFile.getFileNameOnDisk();

		File file = new File(filePath);

		BinaryResource binaryResource = null;
		if (file.exists() && file.isFile()) {
			binaryResource = new BinaryResource(uploadedFile.getFileName(), IOUtility.getContent(file));
		}

		return binaryResource;
	}

	private String getRootPath() {
		String subdomain = ServerSession.get().getCurrentOrganisation().getSubdomain();
		return CONFIG.getPropertyValue(AppFileRootDirectoryOrganisationsConfigProperty.class) + FILE_SEPARATOR + subdomain + FILE_SEPARATOR;
	}

	@Override
	public List<Attachment> fetchAttachments(AttachmentRequest request) {
		BeanArrayHolder<Attachment> rows = new BeanArrayHolder<Attachment>(Attachment.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT id, ");
		varname1.append("       file_name, ");
		varname1.append("       file_name, ");
		varname1.append("       file_size, ");
		varname1.append("       description, ");
		varname1.append("       file_extension ");
		varname1.append("FROM   attachments ");
		varname1.append("WHERE  deleted_at IS NULL ");

		if (request.getRelatedId() != null) {
			varname1.append(" AND related_id = :{request.relatedId} ");
		}

		if (request.getRelatedType() != null) {
			varname1.append(" AND related_type = :{request.relatedType} ");
		}

		varname1.append("INTO   :{attachments.id}, ");
		varname1.append("       :{attachments.fileName}, ");
		varname1.append("       :{attachments.Name}, ");
		varname1.append("       :{attachments.fileSize}, ");
		varname1.append("       :{attachments.description}, ");
		varname1.append("       :{attachments.fileExtension}");
		SQL.selectInto(varname1.toString(), new NVPair("attachments", rows), new NVPair("request", request));

		return Arrays.asList(rows.getBeans());
	}

	@Override
	public Integer saveAttachment(Attachment attachment, Integer userId, String subdomain) {
		UploadedFile uploadedFile;
		IntegerHolder attachmentId = new IntegerHolder();

		try {
			uploadedFile = BEANS.get(UploadUtility.class).uploadFile(attachment.getBinaryResource(), subdomain);

			StringBuffer varname1 = new StringBuffer();
			varname1.append("INSERT INTO attachments ");
			varname1.append("            (created_at, ");
			varname1.append("             file_name, ");
			varname1.append("             file_name_on_disk, ");
			varname1.append("             file_size, ");
			varname1.append("             file_extension, ");
			varname1.append("             absolute_path, ");
			varname1.append("             relative_path, ");
			varname1.append("             full_path, ");
			varname1.append("             user_id, ");
			varname1.append("             related_id, ");
			varname1.append("             related_type) ");
			varname1.append("VALUES      (now(), ");
			varname1.append("             :{attachment.fileName}, ");
			varname1.append("             :{uploadedFile.fileNameOnDisk}, ");
			varname1.append("             :{attachment.fileSize}, ");
			varname1.append("             :{attachment.fileExtension}, ");
			varname1.append("             :{uploadedFile.absolutePath}, ");
			varname1.append("             :{uploadedFile.relativePath}, ");
			varname1.append("             :{uploadedFile.fullPath}, ");
			varname1.append("             :userId, ");
			varname1.append("             :{attachment.relatedId}, ");
			varname1.append("             :{attachment.relatedTypeId} ) ");
			varname1.append("RETURNING id INTO :attachmentId ");
			SQL.selectInto(varname1.toString(), new NVPair("attachment", attachment), new NVPair("attachmentId", attachmentId), new NVPair("uploadedFile", uploadedFile), new NVPair("userId", userId));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return attachmentId.getValue();
	}

	@Override
	public Integer saveAttachment(Attachment attachment) {
		return saveAttachment(attachment, ServerSession.get().getCurrentUser().getId(), ServerSession.get().getCurrentOrganisation().getSubdomain());
	}

	@Override
	public void saveAttachments(List<Attachment> attachments) {
		for (Attachment attachment : attachments) {
			saveAttachment(attachment);
		}
	}

	@Override
	public void deleteAttachments(List<Integer> attachmentIds) {
		String stmt = "UPDATE attachments SET deleted_at = now() WHERE id = :attachmentIds";
		SQL.update(stmt, new NVPair("attachmentIds", attachmentIds));
	}

}
