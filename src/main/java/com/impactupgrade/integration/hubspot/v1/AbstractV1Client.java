package com.impactupgrade.integration.hubspot.v1;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

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
