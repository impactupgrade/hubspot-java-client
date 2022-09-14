package com.impactupgrade.integration.hubspot.v1.model;

import java.util.List;

public class FileImportPage implements AbstractModel {

    private Boolean hasHeader;
    private List<ColumnMapping> columnMappings;

    public Boolean getHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(Boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public List<ColumnMapping> getColumnMappings() {
        return columnMappings;
    }

    public void setColumnMappings(List<ColumnMapping> columnMappings) {
        this.columnMappings = columnMappings;
    }
}
