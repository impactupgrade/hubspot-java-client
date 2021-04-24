package com.impactupgrade.integration.hubspot.v3

import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
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

    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .queryParam("properties", properties.joinToString(","))
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

  fun search(filters: Array<Filter>, customProperties: List<String> = listOf()): DealResults? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(DealProperties::class.declaredMemberProperties.map { p -> p.name })

    val search = Search(listOf(FilterGroup(filters.asList())), properties)
    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        return response.readEntity(DealResults::class.java)
      }
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: DealProperties): Deal? {
    val deal = Deal(null, properties)
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(deal, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> response.readEntity(Deal::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: DealProperties): Deal? {
    val deal = Deal(null, properties)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(deal, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> response.readEntity(Deal::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }
}