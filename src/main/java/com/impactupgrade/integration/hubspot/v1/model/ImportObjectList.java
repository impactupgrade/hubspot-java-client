package com.impactupgrade.integration.hubspot.v1.model;

public class ImportObjectList implements AbstractModel {

    private String objectType;
    private String listId;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
