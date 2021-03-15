package com.impactupgrade.integration.hubspot.v3

import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ContactV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/contacts",
) {

  private val log: Logger = LogManager.getLogger(ContactV3Client::class.java)

  fun getById(id: String): Contact? {
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .get()
    return when (response.status) {
      200 -> response.readEntity(Contact::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun getByEmail(email: String): Contact? {
    val search = Search(listOf(FilterGroup(listOf(Filter("email", "EQ", email)))))
    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        val results = response.readEntity(ContactResults::class.java)
        return when (results.total) {
          0 -> null
          1 -> results.results[0]
          else -> {
            log.warn("{} resulted in {} contacts; returning the first", email, results.total)
            results.results[0]
          }
        }
      }
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: ContactProperties): Contact? {
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(properties, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> response.readEntity(Contact::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: ContactProperties): Contact? {
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(properties, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> response.readEntity(Contact::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }
}