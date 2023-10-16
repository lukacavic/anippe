package com.velebit.anippe.shared.attachments;

import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IAttachmentService extends IService {

	void deleteAttachments(List<Integer> attachmentIds);

	BinaryResource download(Integer attachmentId);

	Integer saveAttachment(Attachment attachment);

	void saveAttachments(List<Attachment> attachments);

	List<Attachment> fetchAttachments(AttachmentRequest request);
}
