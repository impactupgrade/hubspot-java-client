package com.impactupgrade.integration.hubspot.v1.model;

public class ImportFile implements AbstractModel {

    private String fileName;
    private String fileFormat;
    private String dateFormat;
    FileImportPage fileImportPage;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public FileImportPage getFileImportPage() {
        return fileImportPage;
    }

    public void setFileImportPage(FileImportPage fileImportPage) {
        this.fileImportPage = fileImportPage;
    }
}
