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


class CompanyCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/objects/companies",
) {

  private val log: Logger = LogManager.getLogger(CompanyCrmV3Client::class.java)

  // for Java callers
  fun read(id: String, customProperties: Collection<String> = listOf()) = read(id, customProperties, 0)

  fun read(id: String, customProperties: Collection<String> = listOf(), attemptCount: Int = 0): Company? {
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
        val retryFunction = { newAttemptCount: Int -> read(id, customProperties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf()) = search(filterGroups, customProperties, "0", 0)
  fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf(), after: String) = search(filterGroups, customProperties, after, 0)
  fun searchAutoPaging(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf()): List<Company> {
    val companies = mutableListOf<Company>()

    var after: String? = "0"
    while (!after.isNullOrBlank()) {
      val companyResults = search(filterGroups, customProperties, after)
      companies.addAll(companyResults.results)
      after = if (companyResults.paging != null && companyResults.paging?.next != null) {
        companyResults.paging?.next?.after;
      } else {
        null
      }
    }

    return companies
  }

  private fun search(filterGroups: List<FilterGroup>, customProperties: Collection<String> = listOf(), after: String, attemptCount: Int): CompanyResults {
    val properties = mutableListOf<String>()
    properties.addAll(customProperties)
    properties.addAll(CompanyProperties::class.declaredMemberProperties.map { p -> p.name })
    val search = Search(filterGroups, properties, after.toInt())
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
        val retryFunction = { newAttemptCount: Int -> search(filterGroups, customProperties, after, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // provide commonly-used searches
  fun searchByName(name: String, customProperties: Collection<String> = listOf()) =
    search(
      listOf(
        FilterGroup(listOf(Filter("name", "CONTAINS_TOKEN", name)))
      ),
      customProperties
    )

  // for Java callers
  fun insert(properties: CompanyProperties) = insert(properties, 0)

  fun insert(properties: CompanyProperties, attemptCount: Int = 0): Company? {
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
        val retryFunction = { newAttemptCount: Int -> insert(properties, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun update(id: String, properties: CompanyProperties) = update(id, properties, 0)

  // TODO: Switched to using JDK's HttpClient -- having issues with Jersey, PATCH fixes, and Java 16 now preventing reflection on private modules.
  //  Update this lib-wide, but isolating here for the moment.
  fun update(id: String, properties: CompanyProperties, attemptCount: Int = 0): Company? {
    val company = Company(null, properties)
    log.info("updating company: {}", company)

    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://api.hubapi.com/crm/v3/objects/companies/$id?hapikey=$apiKey"))
        .header("Content-Type", "application/json")
        .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(company)))
        .build()
    val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString())

    return when (response.statusCode()) {
      200 -> {
        val responseEntity = objectMapper.readValue(response.body(), Company::class.java)
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
    log.info("deleting company: {}", id)
    val response = target
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .delete()
    log.info("HubSpot API response: {}", response.status)
  }
}