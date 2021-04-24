package com.impactupgrade.integration.hubspot.v3

import jakarta.ws.rs.client.ClientBuilder
import org.glassfish.jersey.client.HttpUrlConnectorProvider

abstract class AbstractV3Client(
  protected val apiKey: String,
  path: String,
) {
  protected val target = ClientBuilder.newBuilder().build()
    // forces Jersey to allow PATCH methods
    .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
    .target("https://api.hubapi.com").path(path)
}