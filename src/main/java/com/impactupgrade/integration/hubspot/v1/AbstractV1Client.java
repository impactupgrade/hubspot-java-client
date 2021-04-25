package com.impactupgrade.integration.hubspot.v1;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class AbstractV1Client {

  protected final String apiKey;

  protected final WebTarget target;

  protected AbstractV1Client(String apiKey) {
    this.apiKey = apiKey;

    Client client = ClientBuilder.newBuilder()
        .build();

    target = client.target("https://api.hubapi.com");
  }
}
