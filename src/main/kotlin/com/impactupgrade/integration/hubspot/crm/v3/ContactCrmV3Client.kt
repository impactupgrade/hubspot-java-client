package com.impactupgrade.integration.hubspot.crm.v3

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paranamer.ParanamerModule
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.reflect.full.declaredMemberProperties

class ContactCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/objects/contacts",
) {

  private val log: Logger = LogManager.getLogger(ContactCrmV3Client::class.java)

  // for Java callers
  fun read(id: String, customProperties: Collection<String> = listOf()) = read(id, customProperties, 0)

  fun read(id: String, customProperties: Collection<String> = listOf(), attemptCount: Int = 0): Contact? {
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
        val retryFunction = { newAttemptCount: Int -> read(id, customProperties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction, null)
      }
    }
  }

  // for Java callers
  fun search(filters: List<Filter>, customProperties: Collection<String> = listOf()) = search(filters, customProperties, 0)

  // ex: Filter("email", "EQ", email)
  fun search(filters: List<Filter>, customProperties: Collection<String> = listOf(), attemptCount: Int = 0): ContactResults {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(ContactProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(listOf(
      FilterGroup(
        filters
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
        val retryFunction = { newAttemptCount: Int -> search(filters, customProperties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction, ContactResults(0, listOf()))
      }
    }
  }

  // provide commonly-used searches
  fun searchByEmail(email: String, customProperties: Collection<String> = listOf()) =
    search(listOf(Filter("email", "EQ", email)), customProperties, 0)
  fun searchByPhone(phone: String, customProperties: Collection<String> = listOf()) =
    // TODO: Also need to include mobilephone?
    search(listOf(Filter("phone", "EQ", normalizePhoneNumber(phone))), customProperties, 0)

  // for Java callers
  fun insert(properties: ContactProperties) = insert(properties, 0)

  fun insert(properties: ContactProperties, attemptCount: Int = 0): Contact? {
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
        val retryFunction = { newAttemptCount: Int -> insert(properties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction, null)
      }
    }
  }

  // for Java callers
  fun update(id: String, properties: ContactProperties) = update(id, properties, 0)

  // TODO: Switched to using JDK's HttpClient -- having issues with Jersey, PATCH fixes, and Java 16 now preventing reflection on private modules.
  //  Update this lib-wide, but isolating here for the moment.
  fun update(id: String, properties: ContactProperties, attemptCount: Int = 0): Contact? {
    val contact = Contact(null, properties)
    log.info("updating contact: {}", contact)

    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://api.hubapi.com/crm/v3/objects/contacts/$id?hapikey=$apiKey"))
        .header("Content-Type", "application/json")
        .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(contact)))
        .build()
    val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())

    return when (response.statusCode()) {
      200 -> {
        val responseEntity = objectMapper.readValue(response.body(), Contact::class.java)
        log.info("HubSpot API response {}: {}", response.statusCode(), responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> update(id, properties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction, null)
      }
    }
  }

  fun delete(id: String) {
    log.info("deleting contact: {}", id)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .delete()
    log.info("HubSpot API response: {}", response.status)
  }
}