package com.impactupgrade.integration.hubspot.v3

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class DealV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/deals",
) {

  private val log: Logger = LogManager.getLogger(DealV3Client::class.java)

  fun getById(id: String): Deal? {
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .get()
    return when (response.status) {
      200 -> response.readEntity(Deal::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: DealProperties): Deal? {
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(properties, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> response.readEntity(Deal::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }
}