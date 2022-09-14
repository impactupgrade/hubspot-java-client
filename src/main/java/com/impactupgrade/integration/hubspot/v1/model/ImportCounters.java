package com.impactupgrade.integration.hubspot.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportCounters implements AbstractModel {

    @JsonProperty("PROPERTY_VALUES_EMITTED")
    private Integer propertyValuesEmitted;

    @JsonProperty("TOTAL_ROWS")
    private Integer totalRows;

    @JsonProperty("UNIQUE_OBJECTS_WRITTEN")
    private Integer uniqueObjectsWritten;

    @JsonProperty("CREATED_OBJECTS")
    private Integer createdObjects;

    public Integer getPropertyValuesEmitted() {
        return propertyValuesEmitted;
    }

    public void setPropertyValuesEmitted(Integer propertyValuesEmitted) {
        this.propertyValuesEmitted = propertyValuesEmitted;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getUniqueObjectsWritten() {
        return uniqueObjectsWritten;
    }

    public void setUniqueObjectsWritten(Integer uniqueObjectsWritten) {
        this.uniqueObjectsWritten = uniqueObjectsWritten;
    }

    public Integer getCreatedObjects() {
        return createdObjects;
    }

    public void setCreatedObjects(Integer createdObjects) {
        this.createdObjects = createdObjects;
    }
}
