package com.impactupgrade.integration.hubspot.v1.model;

import java.util.List;

public class ImportMetadata implements AbstractModel {

    private ImportCounters counters;
    private List<String> fileIds;
    private List<ImportObjectList> objectLists;

    public ImportCounters getCounters() {
        return counters;
    }

    public void setCounters(ImportCounters counters) {
        this.counters = counters;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public List<ImportObjectList> getObjectLists() {
        return objectLists;
    }

    public void setObjectLists(List<ImportObjectList> objectLists) {
        this.objectLists = objectLists;
    }
}
