package com.impactupgrade.integration.hubspot.v3

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.full.declaredMemberProperties

class DealV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/deals",
) {

  private val log: Logger = LogManager.getLogger(DealV3Client::class.java)

  fun read(id: String, customProperties: List<String> = listOf()): Deal? {
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
        log.warn("HubSpot API error {}: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun search(filters: Array<Filter>, customProperties: List<String> = listOf()): DealResults? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(DealProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(listOf(FilterGroup(filters.asList())), properties)
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
        log.warn("HubSpot API error {}: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: DealProperties): Deal? {
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
        log.warn("HubSpot API error {}: {}", response.status, response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: DealProperties): Deal? {
    val deal = Deal(null, properties)
    log.info("updating deal: {}", deal)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(deal, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Deal::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.status, response.readEntity(String::class.java))
        null
      }
    }
  }
}