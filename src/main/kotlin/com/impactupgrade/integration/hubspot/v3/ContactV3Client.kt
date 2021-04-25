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

    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .queryParam("properties", properties.joinToString(","))
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

  // ex: Filter("email", "EQ", email)
  fun search(filters: Array<Filter>, customProperties: List<String> = listOf()): ContactResults? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(ContactProperties::class.declaredMemberProperties.map { p -> p.name })

    val search = com.impactupgrade.integration.hubspot.v3.Search(listOf(
      com.impactupgrade.integration.hubspot.v3.FilterGroup(
        filters.asList()
      )
    ), properties)
    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        return response.readEntity(ContactResults::class.java)
      }
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: ContactProperties): Contact? {
    val contact = com.impactupgrade.integration.hubspot.v3.Contact(null, properties)
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> response.readEntity(Contact::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: ContactProperties): Contact? {
    val contact = com.impactupgrade.integration.hubspot.v3.Contact(null, properties)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> response.readEntity(Contact::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }
}