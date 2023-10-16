package com.velebit.anippe.shared.attachments;

import org.eclipse.scout.rt.platform.resource.BinaryResource;

public class UploadedFile implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BinaryResource binaryResource;
	private String fullPath;
	private String relativePath;
	private String absolutePath;
	private String fileName;
	private String fileNameOnDisk;

	public String getFileNameOnDisk() {
		return fileNameOnDisk;
	}

	public void setFileNameOnDisk(String fileNameOnDisk) {
		this.fileNameOnDisk = fileNameOnDisk;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public BinaryResource getBinaryResource() {
		return binaryResource;
	}

	public void setBinaryResource(BinaryResource binaryResource) {
		this.binaryResource = binaryResource;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

}
