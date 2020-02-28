package com.impactupgrade.integration.hubspot;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class AbstractClient {

  protected final String apiKey;

  protected final WebTarget target;

  protected AbstractClient(String apiKey) {
    this.apiKey = apiKey;

//    Logger logger = Logger.getLogger(this.getClass().getName());
//    Feature loggingFeature = new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT, 2000 * 1024);
    Client client = ClientBuilder.newBuilder()
//        .register(loggingFeature)
        .build();

    target = client.target("https://api.hubapi.com");
  }
}
