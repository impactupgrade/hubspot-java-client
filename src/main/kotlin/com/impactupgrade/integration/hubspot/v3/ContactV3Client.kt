package com.impactupgrade.integration.hubspot.v3

import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.full.declaredMemberProperties

class ContactV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/contacts",
) {

  private val log: Logger = LogManager.getLogger(ContactV3Client::class.java)

  fun read(id: String, customProperties: List<String> = listOf()): Contact? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(ContactProperties::class.declaredMemberProperties.map { p -> p.name })
    log.info("fetching contact {}: {}", id, properties)

    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .queryParam("properties", properties.joinToString(","))
      .request(MediaType.APPLICATION_JSON)
      .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Contact::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  // ex: Filter("email", "EQ", email)
  fun search(filters: Array<Filter>, customProperties: List<String> = listOf()): ContactResults? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(ContactProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(listOf(
      FilterGroup(
        filters.asList()
      )
    ), properties)
    log.info("searching contacts: {}", search)

    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ContactResults::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: ContactProperties): Contact? {
    val contact = Contact(null, properties)
    log.info("inserting contact: {}", contact)
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> {
        val responseEntity = response.readEntity(Contact::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.status, response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: ContactProperties): Contact? {
    val contact = Contact(null, properties)
    log.info("updating contact: {}", contact)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Contact::class.java)
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