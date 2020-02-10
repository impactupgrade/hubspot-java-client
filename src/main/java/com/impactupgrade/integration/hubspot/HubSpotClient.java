package com.impactupgrade.integration.hubspot;

public class HubSpotClient {

  private final String apiKey;

  public HubSpotClient(String apiKey) {
    this.apiKey = apiKey;
  }

  public ContactsClient contacts() {
    return new ContactsClient(apiKey);
  }
}
