package com.impactupgrade.integration.hubspot.crm.v3

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paranamer.ParanamerModule
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.glassfish.jersey.client.HttpUrlConnectorProvider
import java.net.http.HttpResponse
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response

abstract class AbstractCrmV3Client(
  protected val apiKey: String,
  path: String,
) {

  private val log: Logger = LogManager.getLogger(AbstractCrmV3Client::class.java)

  protected val target = ClientBuilder.newBuilder().build()
    // forces Jersey to allow PATCH methods
    .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
    .target("https://api.hubapi.com").path(path)

  protected val objectMapper: ObjectMapper = ObjectMapper().registerModule(ParanamerModule())

  protected fun <T: Any?> handleError(response: Response, attemptCount: Int, retryFunction: (Int) -> T, failureResult: T): T {
    val errorEntity = response.readEntity(ApiError::class.java)
    return if (errorEntity.category == "RATE_LIMITS") {
      if (attemptCount == 5) {
        log.error("HubSpot API hit rate limit; exhausted retries")
        failureResult
      } else {
        val newAttemptCount = attemptCount + 1
        log.info("HubSpot API hit rate limit; retry {}/5 in 3 sec", newAttemptCount)
        Thread.sleep(3000)
        retryFunction(newAttemptCount)
      }
    } else {
      log.error("HubSpot API error {}: {}", errorEntity)
      failureResult
    }
  }

  protected fun <T: Any?> handleError(response: HttpResponse<String>, attemptCount: Int, retryFunction: (Int) -> T, failureResult: T): T {
    val body = response.body()
    val errorEntity = objectMapper.readValue(body, ApiError::class.java)
    return if (errorEntity.category == "RATE_LIMITS") {
      if (attemptCount == 5) {
        log.error("HubSpot API hit rate limit; exhausted retries")
        failureResult
      } else {
        val newAttemptCount = attemptCount + 1
        log.info("HubSpot API hit rate limit; retry {}/5 in 3 sec", newAttemptCount)
        Thread.sleep(3000)
        retryFunction(newAttemptCount)
      }
    } else {
      log.error("HubSpot API error {}: {}", body)
      failureResult
    }
  }
}