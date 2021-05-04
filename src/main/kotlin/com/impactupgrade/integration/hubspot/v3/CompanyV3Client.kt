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
    log.info("fetching company {}: {}", id, properties)

    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .queryParam("properties", properties.joinToString(","))
      .request(MediaType.APPLICATION_JSON)
      .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Company::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun search(filters: Array<Filter>, customProperties: List<String> = listOf()): CompanyResults? {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(CompanyProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(listOf(
      FilterGroup(
        filters.asList()
      ),
    ), properties)
    log.info("searching companies: {}", search)

    val response = target
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(CompanyResults::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.readEntity(String::class.java))
        null
      }
    }
  }

  fun insert(properties: CompanyProperties): Company? {
    val company = Company(null, properties)
    log.info("inserting company: {}", company)
    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(company, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> {
        val responseEntity = response.readEntity(Company::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.status, response.readEntity(String::class.java))
        null
      }
    }
  }

  fun update(id: String, properties: CompanyProperties): Company? {
    val company = Company(null, properties)
    log.info("updating company: {}", company)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .method("PATCH", Entity.entity(company, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Company::class.java)
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