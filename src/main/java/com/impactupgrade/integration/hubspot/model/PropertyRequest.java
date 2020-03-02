package com.impactupgrade.integration.hubspot.model;

public class PropertyRequest implements AbstractModel {

  private String property;
  private String value;

  public PropertyRequest() {

  }

  public PropertyRequest(String property, String value) {
    this.property = property;
    this.value = value;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PropertyRequest{" +
        "property='" + property + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
