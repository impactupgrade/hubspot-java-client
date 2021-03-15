package com.impactupgrade.integration.hubspot.v3

import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AssociationV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/associations",
) {

  private val log: Logger = LogManager.getLogger(AssociationV3Client::class.java)

  fun insert(association: Association) {
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(association, MediaType.APPLICATION_JSON_TYPE))
    if (response.status >= 300) {
      log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
    }
  }
}