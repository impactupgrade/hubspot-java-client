package com.impactupgrade.integration.hubspot.v1.model;

import java.util.List;

public class ImportRequest implements AbstractModel {

    private String name;
    private List<ImportFile> files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImportFile> getFiles() {
        return files;
    }

    public void setFiles(List<ImportFile> files) {
        this.files = files;
    }
}
