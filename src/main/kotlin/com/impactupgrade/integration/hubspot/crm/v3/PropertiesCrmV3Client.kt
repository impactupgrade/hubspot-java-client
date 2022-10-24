package com.impactupgrade.integration.hubspot.crm.v3

import com.impactupgrade.integration.hubspot.PropertiesResponse
import com.impactupgrade.integration.hubspot.Property
import com.impactupgrade.integration.hubspot.PropertyBatchCreateRequest
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class PropertiesCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/properties", // /crm/v3/properties/{objectType}
) {

  private val log: Logger = LogManager.getLogger(PropertiesCrmV3Client::class.java)

  // for Java callers
  fun insert(objectType: String, property: Property) = insert(objectType, property, 0)

  fun insert(objectType: String, property: Property, attemptCount: Int = 0): Property? {
    log.info("inserting property: {}", property)
    val response = target
      .path(objectType)
      .request(MediaType.APPLICATION_JSON)
      .header("Authorization", "Bearer $apiKey")
      .post(Entity.entity(property, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> {
        val responseEntity = response.readEntity(Property::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> insert(objectType, property, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun read(objectType: String, propertyName: String) = read(objectType, propertyName, 0)

  fun read(objectType: String, propertyName: String, attemptCount: Int = 0): Property? {
    log.info("fetching {}'s property {}:", objectType, propertyName)

    val response = target
      .path(objectType)
      .path(propertyName)
      .request(MediaType.APPLICATION_JSON)
      .header("Authorization", "Bearer $apiKey")
      .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Property::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> read(objectType, propertyName, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun update(objectType: String, propertyName: String, property: Property) = update(objectType, propertyName, property, 0)

  // TODO: Switched to using JDK's HttpClient -- having issues with Jersey, PATCH fixes, and Java 16 now preventing reflection on private modules.
  //  Update this lib-wide, but isolating here for the moment.
  fun update(objectType: String, propertyName: String, property: Property, attemptCount: Int = 0): Property? {
    log.info("updating {}'s property {}", objectType, property)

    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://api.hubapi.com/crm/v3/properties/$objectType/$propertyName?hapikey=$apiKey"))
        .header("Content-Type", "application/json")
        .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(property)))
        .build()
    val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())

    return when (response.statusCode()) {
      200 -> {
        val responseEntity = objectMapper.readValue(response.body(), Property::class.java)
        log.info("HubSpot API response {}: {}", response.statusCode(), responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> update(objectType, propertyName, property, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  fun delete(objectType: String, propertyName: String) {
    log.info("deleting {}'s property {}", objectType, propertyName)
    val response = target
      .path(objectType)
      .path(propertyName)
      .request(MediaType.APPLICATION_JSON)
      .header("Authorization", "Bearer $apiKey")
      .delete()
    log.info("HubSpot API response: {}", response.status)
  }

  // for Java callers
  fun readAll(objectType: String) = readAll(objectType, 0)

  fun readAll(objectType: String, attemptCount: Int = 0): PropertiesResponse? {
    log.info("fetching {} properties:", objectType)

    val response = target
      .path(objectType)
      .request(MediaType.APPLICATION_JSON)
      .header("Authorization", "Bearer $apiKey")
      .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(PropertiesResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> readAll(objectType, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun batchInsert(objectType: String, properties: List<Property>) = batchInsert(objectType, properties, 0)

  fun batchInsert(objectType: String, properties: List<Property>? = null, attemptCount: Int = 0): PropertiesResponse? {
    log.info("inserting properties: {}", properties)
    val batchRequest = PropertyBatchCreateRequest(properties)
    val response = target
      .path(objectType + "/batch/create")
      .request(MediaType.APPLICATION_JSON)
      .header("Authorization", "Bearer $apiKey")
      .post(Entity.entity(batchRequest, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> {
        val responseEntity = response.readEntity(PropertiesResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> batchInsert(objectType, properties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }
}