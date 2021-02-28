package com.impactupgrade.integration.hubspot.v1.model.internal;

import com.impactupgrade.integration.hubspot.v1.model.AbstractModel;

public class ErrorResponse implements AbstractModel {

  private String message;

  private IdentityProfile identityProfile;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public IdentityProfile getIdentityProfile() {
    return identityProfile;
  }

  public void setIdentityProfile(IdentityProfile identityProfile) {
    this.identityProfile = identityProfile;
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
        "message='" + message + '\'' +
        ", identityProfile=" + identityProfile +
        '}';
  }
}
