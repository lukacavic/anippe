package com.velebit.anippe.shared.beans;

import java.util.Date;

public class Document implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String fileName;
    private String fileNameOnDisk;
    private String fileExtension;
    private Date uploadedAt;
    private String uploader;
    private Integer uploaderId;
    private String fullPath;
    private Integer organisationId;

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public String getFileNameOnDisk() {
        return fileNameOnDisk;
    }

    public void setFileNameOnDisk(String fileNameOnDisk) {
        this.fileNameOnDisk = fileNameOnDisk;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

}
