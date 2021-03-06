package com.impactupgrade.integration.hubspot.v3

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class AssociationV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/associations",
) {

  private val log: Logger = LogManager.getLogger(AssociationV3Client::class.java)

  /**
   * This one's a little odd. The API requires the from and to object types to be defined on the path,
   * but also requires the type to be given within each batch input. For now, assume one at a time, just to be safe.
   */
  fun insert(fromType: String, fromId: String, toType: String, toId: String) {
    val association = AssociationInput(HasId(fromId), HasId(toId), fromType + "_to_" + toType)
    val associationBatch = AssociationBatch(listOf(association))
    log.info("inserting association: {}", associationBatch)
    val response = target
      .path(fromType).path(toType).path("batch").path("create")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(associationBatch, MediaType.APPLICATION_JSON_TYPE))
    if (response.status >= 300) {
      log.warn("HubSpot API error {}: {}", response.status, response.readEntity(String::class.java))
    } else {
      log.info("HubSpot API response {}: {}", response.status, response.readEntity(String::class.java))
    }
  }
}