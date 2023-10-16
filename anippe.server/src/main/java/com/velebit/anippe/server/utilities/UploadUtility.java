package com.velebit.anippe.server.utilities;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.config.AppFileRootDirectoryOrganisationsConfigProperty;
import com.velebit.anippe.shared.attachments.UploadedFile;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Bean
public class UploadUtility {

	Logger LOG = LoggerFactory.getLogger(getClass());

	public final String FILE_SEPARATOR = System.getProperty("file.separator");

	public UploadedFile uploadFile(byte[] content, String fileName) throws IOException {
		return uploadFile(new BinaryResource(fileName, content));
	}

	public UploadedFile uploadFile(BinaryResource binaryResource) throws IOException {
		return uploadFile(binaryResource, ServerSession.get().getCurrentOrganisation().getSubdomain());
	}

	public UploadedFile uploadFile(BinaryResource binaryResource, String subdomain) throws IOException {
		UploadedFile uploadedFile = new UploadedFile();

		if (binaryResource == null) {
			LOG.error("Binary resource is null.");
			return null;
		}

		if (binaryResource.getContent() == null) {
			LOG.error("Binary resource content is null.");
			return null;
		}

		File tempFile = null;
		String fileName = UUID.randomUUID().toString() + "." + IOUtility.getFileExtension(binaryResource.getFilename());

		// Create root directory if does not exist
		if (!IOUtility.fileExists(getAbsoluteUploadPath(subdomain))) {
			IOUtility.createDirectory(getAbsoluteUploadPath(subdomain));
		}

		tempFile = new File(getAbsoluteUploadPath(subdomain) + FILE_SEPARATOR + fileName);

		tempFile.createNewFile();

		IOUtility.writeContent(tempFile.getPath(), binaryResource.getContent());

		uploadedFile.setBinaryResource(binaryResource);
		uploadedFile.setFullPath(tempFile.getPath());
		uploadedFile.setRelativePath(subdomain);
		uploadedFile.setAbsolutePath(getAbsoluteUploadPath(subdomain));
		uploadedFile.setFileNameOnDisk(fileName);
		uploadedFile.setFileName(binaryResource.getFilename());

		return uploadedFile;
	}

	public String getRelativeUploadPath() {
		return ServerSession.get().getCurrentOrganisation().getSubdomain();
	}

	public String getAbsoluteUploadPath() {
		return getAbsoluteUploadPath(ServerSession.get().getCurrentOrganisation().getSubdomain());
	}

	public String getAbsoluteUploadPath(String subdomain) {
		return CONFIG.getPropertyValue(AppFileRootDirectoryOrganisationsConfigProperty.class) + FILE_SEPARATOR + subdomain;
	}

}
