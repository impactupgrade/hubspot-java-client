package com.impactupgrade.integration.hubspot.v1;

public class HubSpotV1Client {

  private final String apiKey;

  public HubSpotV1Client(String apiKey) {
    this.apiKey = apiKey;
  }

  public ContactV1Client contacts() {
    return new ContactV1Client(apiKey);
  }

  public ContactListV1Client lists() {
    return new ContactListV1Client(apiKey);
  }
}
