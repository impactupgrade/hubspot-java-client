package com.impactupgrade.integration.hubspot.v1.model;

import java.util.Date;

public class ImportResponse implements AbstractModel {

    private String id;
    private String state; // TODO: enum?
    private Boolean optOutImport;
    private ImportMetadata metadata;

    private Date updatedAt;
    private Date createdAt;

}
