package com.impactupgrade.integration.hubspot.v3

import com.fasterxml.jackson.databind.ObjectMapper
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.full.declaredMemberProperties

class CompanyV3Client(apiKey: String) : AbstractV3Client(
  apiKey,
  "crm/v3/objects/companies",
) {

  private val log: Logger = LogManager.getLogger(CompanyV3Client::class.java)

  fun read(id: String, customProperties: List<String> = listOf()): Company? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(CompanyProperties::class.declaredMemberProperties.map { p -> p.name })

    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .queryParam("properties", properties.joinToString(","))
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

  fun search(filters: Array<Filter>, customProperties: List<String> = listOf()): CompanyResults? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(CompanyProperties::class.declaredMemberProperties.map { p -> p.name })

    val search = com.impactupgrade.integration.hubspot.v3.Search(listOf(
      com.impactupgrade.integration.hubspot.v3.FilterGroup(
        filters.asList()
      ),
    ), properties)
    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        return response.readEntity(CompanyResults::class.java)
      }
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: CompanyProperties): Company? {
    val company = com.impactupgrade.integration.hubspot.v3.Company(null, properties)
    log.info(ObjectMapper().writeValueAsString(company))
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(company, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> response.readEntity(Company::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: CompanyProperties): Company? {
    val company = com.impactupgrade.integration.hubspot.v3.Company(null, properties)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(company, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> response.readEntity(Company::class.java)
      else -> {
        log.warn("HubSpot API error: {}", response.readEntity(String::class.java))
        null
      }
    }
  }
}