package com.impactupgrade.integration.hubspot.v1.model;

public class ColumnMapping implements AbstractModel {

    private String columnObjectTypeId;
    private String columnName;
    private String propertyName;
    private String idColumnType;

    public String getColumnObjectTypeId() {
        return columnObjectTypeId;
    }

    public void setColumnObjectTypeId(String columnObjectTypeId) {
        this.columnObjectTypeId = columnObjectTypeId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getIdColumnType() {
        return idColumnType;
    }

    public void setIdColumnType(String idColumnType) {
        this.idColumnType = idColumnType;
    }
}
