package com.impactupgrade.integration.hubspot;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class AbstractClient {

  private Client client = ClientBuilder.newClient();
  protected WebTarget target = client.target("https://api.hubapi.com");

  protected final String apiKey;

  protected AbstractClient(String apiKey) {
    this.apiKey = apiKey;
  }
}
