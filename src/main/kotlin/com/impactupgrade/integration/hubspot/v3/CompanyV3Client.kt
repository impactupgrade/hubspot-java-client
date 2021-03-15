package com.impactupgrade.integration.hubspot.v3

import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class CompanyV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/companies",
) {

  private val log: Logger = LogManager.getLogger(CompanyV3Client::class.java)

  fun getById(id: String): Company? {
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .get()
    return when (response.status) {
      200 -> response.readEntity(Company::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: CompanyProperties): Company? {
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(properties, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> response.readEntity(Company::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: CompanyProperties): Company? {
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(properties, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> response.readEntity(Company::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }
}