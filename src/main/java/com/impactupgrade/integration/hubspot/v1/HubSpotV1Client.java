package com.impactupgrade.integration.hubspot.v1;

@Deprecated
public class HubSpotV1Client {

  private final String apiKey;

  @Deprecated
  public HubSpotV1Client(String apiKey) {
    this.apiKey = apiKey;
  }

  @Deprecated
  public ContactV1Client contact() {
    return new ContactV1Client(apiKey);
  }

  @Deprecated
  public ContactListV1Client contactList() {
    return new ContactListV1Client(apiKey);
  }

  @Deprecated
  public EngagementV1Client engagement() {
    return new EngagementV1Client(apiKey);
  }
}
