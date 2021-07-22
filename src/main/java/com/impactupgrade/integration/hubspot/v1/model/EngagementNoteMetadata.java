package com.impactupgrade.integration.hubspot.v1.model;

public class EngagementNoteMetadata implements AbstractModel  {

  private String body;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "EngagementNoteMetadata{" +
        "body='" + body + '\'' +
        '}';
  }
}
