package com.impactupgrade.integration.hubspot.crm.v3

import com.impactupgrade.integration.hubspot.*
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.reflect.full.declaredMemberProperties

class DealCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/objects/deals",
) {

  private val log: Logger = LogManager.getLogger(DealCrmV3Client::class.java)

  // for Java callers
  fun read(id: String, customProperties: Collection<String> = listOf()) = read(id, customProperties, 0)

  fun read(id: String, customProperties: Collection<String> = listOf(), attemptCount: Int = 0): Deal? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(DealProperties::class.declaredMemberProperties.map { p -> p.name })
    log.info("fetching deal {}: {}", id, properties)

    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .queryParam("properties", properties.joinToString(","))
      .request(MediaType.APPLICATION_JSON)
      .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Deal::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> read(id, customProperties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun batchRead(ids: List<String>, customProperties: Collection<String> = listOf())  = batchRead(ids, customProperties, 0)

  fun batchRead(ids: List<String>, customProperties: Collection<String> = listOf(), attemptCount: Int = 0): DealBatchResults {
    val hasIds = ids.map { HasId(it) }
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(DealProperties::class.declaredMemberProperties.map { p -> p.name })
    val batchRead = BatchRead(hasIds, properties)
    log.info("batch reading deals: {}", batchRead)

    val response = target
      .path("batch/read")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(batchRead, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(DealBatchResults::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> batchRead(ids, customProperties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf()) = search(filterGroups, customProperties, "0", 0)
  fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf(), after: String) = search(filterGroups, customProperties, after, 0)
  fun searchAutoPaging(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf()): List<Deal> {
    val deals = mutableListOf<Deal>()

    var after: String? = "0"
    while (!after.isNullOrBlank()) {
      val dealResults = search(filterGroups, customProperties, after)
      deals.addAll(dealResults.results)
      after = if (dealResults.paging != null && dealResults.paging?.next != null) {
        dealResults.paging?.next?.after;
      } else {
        null
      }
    }

    return deals
  }

  private fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf(), after: String, attemptCount: Int): DealResults {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(DealProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(filterGroups, properties, after.toInt())
    log.info("searching deals: {}", search)

    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(DealResults::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> search(filterGroups, customProperties, after, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun insert(properties: DealProperties) = insert(properties, 0)

  fun insert(properties: DealProperties, attemptCount: Int = 0): Deal? {
    val deal = Deal(null, properties)
    log.info("inserting deal: {}", deal)
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(deal, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> {
        val responseEntity = response.readEntity(Deal::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> insert(properties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun update(id: String, properties: DealProperties) = update(id, properties, 0)

  // TODO: Switched to using JDK's HttpClient -- having issues with Jersey, PATCH fixes, and Java 16 now preventing reflection on private modules.
  //  Update this lib-wide, but isolating here for the moment.
  fun update(id: String, properties: DealProperties, attemptCount: Int = 0): Deal? {
    val deal = Deal(null, properties)
    log.info("updating deal: {}", deal)

    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://api.hubapi.com/crm/v3/objects/deals/$id?hapikey=$apiKey"))
        .header("Content-Type", "application/json")
        .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(deal)))
        .build()
    val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())

    return when (response.statusCode()) {
      200 -> {
        val responseEntity = objectMapper.readValue(response.body(), Deal::class.java)
        log.info("HubSpot API response {}: {}", response.statusCode(), responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> update(id, properties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  fun delete(id: String) {
    log.info("deleting deal: {}", id)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .delete()
    log.info("HubSpot API response: {}", response.status)
  }
}