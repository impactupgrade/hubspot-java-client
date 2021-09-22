package com.impactupgrade.integration.hubspot.crm.v3

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paranamer.ParanamerModule
import org.glassfish.jersey.client.HttpUrlConnectorProvider
import javax.ws.rs.client.ClientBuilder

abstract class AbstractCrmV3Client(
  protected val apiKey: String,
  path: String,
) {
  protected val target = ClientBuilder.newBuilder().build()
    // forces Jersey to allow PATCH methods
    .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
    .target("https://api.hubapi.com").path(path)

  protected val objectMapper: ObjectMapper = ObjectMapper().registerModule(ParanamerModule())
}