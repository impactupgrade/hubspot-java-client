package com.impactupgrade.integration.hubspot.crm.v3

import com.impactupgrade.integration.hubspot.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
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
        .queryParam("properties", properties.joinToString(","))
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(Contact::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> read(id, customProperties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf()) = search(filterGroups, customProperties, "0", 0)
  fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf(), after: String) = search(filterGroups, customProperties, after, 0)
  fun searchAutoPaging(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf()): List<Contact> {
    val contacts = mutableListOf<Contact>()

    var after: String? = "0"
    while (!after.isNullOrBlank()) {
      val contactResults = search(filterGroups, customProperties, after)
      contacts.addAll(contactResults.results)
      after = if (contactResults.paging != null && contactResults.paging?.next != null) {
        contactResults.paging?.next?.after;
      } else {
        null
      }
    }

    return contacts
  }

  // ex: Filter("email", "EQ", email)
  private fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf(), after: String, attemptCount: Int): ContactResults {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(ContactProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(filterGroups, properties, after.toInt())
    log.info("searching contacts: {}", search)

    val response = target
        .path("search")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ContactResults::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> search(filterGroups, customProperties, after, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // provide commonly-used searches
  fun searchByEmail(email: String, customProperties: Collection<String> = listOf()) =
    // https://community.hubspot.com/t5/APIs-Integrations/Search-Contacts-for-Secondary-Mail/td-p/358756
    search(
      listOf(
        // email is case-sensitive and HS auto lower cases is
        FilterGroup(listOf(Filter("email", "EQ", email.lowercase()))),
        // IMPORTANT: hs_additional_emails does not appear to support wildcards with CONTAINS_TOKEN. Pass in only the email.
        FilterGroup(listOf(Filter("hs_additional_emails", "CONTAINS_TOKEN", email.lowercase())))
      ),
      customProperties
    )
  fun searchByPhone(phone: String, customProperties: Collection<String> = listOf()): ContactResults {
    var normalizedPhone = phone.replace("+1", "").filter { it.isDigit() }
    if (phone.length == 11) {
      normalizedPhone = normalizedPhone.substring(1)
    }
    normalizedPhone = "*" + normalizedPhone.substring(0, 3) + "*" + normalizedPhone.substring(3, 6) + "*" + normalizedPhone.substring(6) + "*"
    println("normalizedPhone: $normalizedPhone")
    return search(
        listOf(
            FilterGroup(listOf(Filter("phone", "CONTAINS_TOKEN", normalizedPhone))),
            FilterGroup(listOf(Filter("mobilephone", "CONTAINS_TOKEN", normalizedPhone)))
        ),
        customProperties
    )
  }

  // for Java callers
  fun insert(properties: ContactProperties) = insert(properties, 0)

  fun insert(properties: ContactProperties, attemptCount: Int = 0): Contact? {
    val contact = Contact(null, properties)
    log.info("inserting contact: {}", contact)
    val response = target
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE))
    return when (response.status) {
      201 -> {
        val responseEntity = response.readEntity(Contact::class.java)
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
  fun update(id: String, properties: ContactProperties) = update(id, properties, 0)

  // TODO: Switched to using JDK's HttpClient -- having issues with Jersey, PATCH fixes, and Java 16 now preventing reflection on private modules.
  //  Update this lib-wide, but isolating here for the moment.
  fun update(id: String, properties: ContactProperties, attemptCount: Int = 0): Contact? {
    val contact = Contact(null, properties)
    log.info("updating contact: {}", contact)

    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://api.hubapi.com/crm/v3/objects/contacts/$id"))
        .header("Content-Type", "application/json")
        .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(contact)))
        .header("Authorization", "Bearer $apiKey")
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
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  fun delete(id: String) {
    log.info("deleting contact: {}", id)
    val response = target
        .path(id)
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .delete()
    log.info("HubSpot API response: {}", response.status)
  }
}