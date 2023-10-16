package com.velebit.anippe.shared.attachments;

import org.eclipse.scout.rt.platform.resource.BinaryResource;

import java.util.Date;

public class Attachment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer relatedId;
	private Integer relatedTypeId;
	private byte[] attachment;
	private String fileExtension;
	private String fileName;
	private String fileNameOnDisk;
	private Integer fileSize;

	private Integer userId;
	private String name;
	private String description;
	private Date createdAt;

	public String getFileNameOnDisk() {
		return fileNameOnDisk;
	}

	public void setFileNameOnDisk(String fileNameOnDisk) {
		this.fileNameOnDisk = fileNameOnDisk;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getRelatedTypeId() {
		return relatedTypeId;
	}

	public void setRelatedTypeId(Integer relatedTypeId) {
		this.relatedTypeId = relatedTypeId;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public BinaryResource getBinaryResource() {
		return new BinaryResource(fileName, attachment);
	}

}
