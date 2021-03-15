package com.impactupgrade.integration.hubspot.v3

import jakarta.ws.rs.client.ClientBuilder

abstract class AbstractV3Client(
  protected val apiKey: String,
  path: String,
) {
  protected val target = ClientBuilder.newBuilder().build().target("https://api.hubapi.com").path(path)
}