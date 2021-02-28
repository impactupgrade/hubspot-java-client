package com.impactupgrade.integration.hubspot.v1.model;

import java.util.List;

public class ContactRequest implements AbstractModel {

  private List<PropertyRequest> properties;

  public List<PropertyRequest> getProperties() {
    return properties;
  }

  public void setProperties(List<PropertyRequest> properties) {
    this.properties = properties;
  }

  @Override
  public String toString() {
    return "ContactRequest{" +
        "properties=" + properties +
        '}';
  }
}
