package com.impactupgrade.integration.hubspot;

import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractClient {

  protected final String apiKey;

  protected final WebTarget target;

  protected AbstractClient(String apiKey) {
    this.apiKey = apiKey;

    Logger logger = Logger.getLogger(this.getClass().getName());
    Feature loggingFeature = new LoggingFeature(
        logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE);

    Client client = ClientBuilder.newBuilder()
        .register(loggingFeature)
        .build();

    target = client.target("https://api.hubapi.com");
  }
}
