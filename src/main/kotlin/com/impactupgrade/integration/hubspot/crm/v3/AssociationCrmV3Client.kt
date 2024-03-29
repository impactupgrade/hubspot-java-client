package com.impactupgrade.integration.hubspot.crm.v3

import com.impactupgrade.integration.hubspot.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class AssociationCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/associations",
) {

  private val log: Logger = LogManager.getLogger(AssociationCrmV3Client::class.java)

  fun search(fromType: String, fromId: String, toType: String) = search(fromType, fromId, toType, 0)

  fun search(fromType: String, fromId: String, toType: String, attemptCount: Int = 0): AssociationSearchResults {
    val search = AssociationSearchBatch(listOf(HasId(fromId)))
    log.info("searching associations: {} {} {}", fromType, search, toType)

    val response = target
        .path(fromType).path(toType).path("batch").path("read")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return if (response.status < 300) {
      val responseEntity = response.readEntity(AssociationSearchResults::class.java)
      log.info("HubSpot API response {}: {}", response.status, responseEntity)
      responseEntity
    } else {
      val retryFunction = { newAttemptCount: Int -> search(fromType, fromId, toType, newAttemptCount) }
      handleError(response, attemptCount, retryFunction)
    }
  }

  // for Java callers
  fun insert(fromType: String, fromId: String, toType: String, toId: String) = insert(fromType, fromId, toType, toId, 0)

  /**
   * This one's a little odd. The API requires the from and to object types to be defined on the path,
   * but also requires the type to be given within each batch input. For now, assume one at a time, just to be safe.
   */
  fun insert(fromType: String, fromId: String, toType: String, toId: String, attemptCount: Int = 0) {
    val association = AssociationInsertInput(HasId(fromId), HasId(toId), fromType + "_to_" + toType)
    val associationBatch = AssociationInsertBatch(listOf(association))
    log.info("inserting association: {}", associationBatch)
    val response = target
        .path(fromType).path(toType).path("batch").path("create")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(Entity.entity(associationBatch, MediaType.APPLICATION_JSON_TYPE))
    if (response.status >= 300) {
      val retryFunction = { newAttemptCount: Int -> insert(fromType, fromId, toType, toId, newAttemptCount) }
      handleError(response, attemptCount, retryFunction)
    } else {
      log.info("HubSpot API response {}: {}", response.status, response.readEntity(String::class.java))
    }
  }

  // for Java callers
  fun delete(fromType: String, fromId: String, toType: String, toId: String) = delete(fromType, fromId, toType, toId, 0)

  /**
   * This one's a little odd. The API requires the from and to object types to be defined on the path,
   * but also requires the type to be given within each batch input. For now, assume one at a time, just to be safe.
   */
  fun delete(fromType: String, fromId: String, toType: String, toId: String, attemptCount: Int = 0) {
    val association = AssociationInsertInput(HasId(fromId), HasId(toId), fromType + "_to_" + toType)
    val associationBatch = AssociationInsertBatch(listOf(association))
    log.info("deleting association: {}", associationBatch)
    val response = target
        .path(fromType).path(toType).path("batch").path("archive")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(Entity.entity(associationBatch, MediaType.APPLICATION_JSON_TYPE))
    if (response.status >= 300) {
      val retryFunction = { newAttemptCount: Int -> delete(fromType, fromId, toType, toId, newAttemptCount) }
      handleError(response, attemptCount, retryFunction)
    } else {
      log.info("HubSpot API response {}: {}", response.status, response.readEntity(String::class.java))
    }
  }
}