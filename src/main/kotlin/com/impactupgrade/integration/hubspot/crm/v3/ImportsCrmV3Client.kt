package com.impactupgrade.integration.hubspot.crm.v3

import com.impactupgrade.integration.hubspot.ImportCancelResponse
import com.impactupgrade.integration.hubspot.ImportRequest
import com.impactupgrade.integration.hubspot.ImportResponse
import com.impactupgrade.integration.hubspot.ImportsResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.glassfish.jersey.media.multipart.FormDataMultiPart
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart
import java.io.File
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class ImportsCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/imports",
) {

  private val log: Logger = LogManager.getLogger(ImportsCrmV3Client::class.java)

  // for Java callers
  fun readActive() = readActive(0)

  fun readActive(attemptCount: Int = 0): ImportsResponse? {
    log.info("fetching active imports")

    val response = target
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ImportsResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> readActive(newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun importFiles(importRequest: ImportRequest, file: File) = importFiles(importRequest, file, 0)

  fun importFiles(importRequest: ImportRequest, file: File, attemptCount: Int = 0): ImportResponse? {
    log.info("importing: {}", importRequest)
    var filePart = FileDataBodyPart("files", file)
    var formDataMultiPart = FormDataMultiPart()
      formDataMultiPart
        .field("importRequest", objectMapper.writeValueAsString(importRequest))
        .bodyPart(filePart);

    val response = target
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ImportResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> importFiles(importRequest, file, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun read(importId: String) = read(importId, 0)

  fun read(importId: String, attemptCount: Int = 0): ImportResponse? {
    log.info("fetching import {}", importId)

    val response = target
        .path(importId)
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .get()
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ImportResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> read(importId, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

  // for Java callers
  fun cancel(importId: String) = cancel(importId, 0)

  fun cancel(importId: String, attemptCount: Int = 0): ImportCancelResponse? {
    log.info("canceling import {}", importId)

    val response = target
        .path(importId)
        .path("cancel")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer $apiKey")
        .post(null)
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ImportCancelResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> cancel(importId, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

}