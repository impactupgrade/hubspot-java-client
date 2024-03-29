package com.impactupgrade.integration.hubspot.v1.model;

public class Engagement implements AbstractModel  {

  private boolean active = true;
  private String ownerId;
  private String type;
  // TODO: timestamp

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getOwnerId() {
    return this.ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Engagement{" +
        "active=" + active +
        ", ownerId='" + ownerId + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
