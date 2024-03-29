package com.impactupgrade.integration.hubspot.crm.v3

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paranamer.ParanamerModule
import com.impactupgrade.integration.hubspot.ApiError
import com.impactupgrade.integration.hubspot.HubSpotException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.glassfish.jersey.client.HttpUrlConnectorProvider
import org.glassfish.jersey.media.multipart.MultiPartFeature
import java.net.http.HttpResponse
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider


abstract class AbstractCrmV3Client(
  protected val apiKey: String,
  path: String,
) {

  private val log: Logger = LogManager.getLogger(AbstractCrmV3Client::class.java)

  protected val target = ClientBuilder.newBuilder().build()
      .register(JacksonConfig::class.java)
      .register(MultiPartFeature::class.java)
      // forces Jersey to allow PATCH methods
      .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
      .target("https://api.hubapi.com").path(path)

  protected fun <T: Any?> handleError(response: Response, attemptCount: Int, retryFunction: (Int) -> T): T {
    val body = response.readEntity(String::class.java)
    val errorEntity = objectMapper.readValue(body, ApiError::class.java)
    return if (errorEntity.errorType == "RATE_LIMIT" || errorEntity.category == "RATE_LIMITS") {
      if (attemptCount == 5) {
        log.error("HubSpot API hit rate limit; exhausted retries")
        throw RuntimeException("HubSpot API hit rate limit; exhausted retries")
      } else {
        val newAttemptCount = attemptCount + 1
        log.info("HubSpot API hit rate limit; retry {}/5 in 5 sec", newAttemptCount)
        Thread.sleep(5000)
        retryFunction(newAttemptCount)
      }
    } else if (errorEntity.category == "VALIDATION_ERROR") {
      log.warn("HubSpot API failed due to invalid data: {}", errorEntity.message)
      throw HubSpotException("HubSpot API failed due to invalid data: " + errorEntity.message)
    } else {
      log.error("HubSpot API error {}: {}", response.status, body)
      throw RuntimeException("HubSpot API error: $body")
    }
  }

  protected fun <T: Any?> handleError(response: HttpResponse<String>, attemptCount: Int, retryFunction: (Int) -> T): T {
    val body = response.body()
    val errorEntity = objectMapper.readValue(body, ApiError::class.java)
    return if (errorEntity.category == "RATE_LIMITS") {
      if (attemptCount == 5) {
        log.error("HubSpot API hit rate limit; exhausted retries")
        throw RuntimeException("HubSpot API hit rate limit; exhausted retries")
      } else {
        val newAttemptCount = attemptCount + 1
        log.info("HubSpot API hit rate limit; retry {}/5 in 5 sec", newAttemptCount)
        Thread.sleep(5000)
        retryFunction(newAttemptCount)
      }
    } else if (errorEntity.category == "VALIDATION_ERROR") {
      log.warn("HubSpot API failed due to invalid data: {}", errorEntity.message)
      throw HubSpotException("HubSpot API failed due to invalid data: " + errorEntity.message)
    } else {
      log.error("HubSpot API error {}: {}", response.statusCode(), body)
      throw RuntimeException("HubSpot API error: $body")
    }
  }

  companion object {
    @JvmStatic
    protected val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(ParanamerModule())
        .registerModule(KotlinModule())
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
  }

  @Provider
  class JacksonConfig : ContextResolver<ObjectMapper> {
    override fun getContext(aClass: Class<*>?): ObjectMapper {
      return objectMapper
    }
  }
}