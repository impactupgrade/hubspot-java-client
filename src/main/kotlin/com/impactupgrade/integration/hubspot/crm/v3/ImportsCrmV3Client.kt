package com.impactupgrade.integration.hubspot.crm.v3

import com.impactupgrade.integration.hubspot.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.glassfish.jersey.media.multipart.FormDataMultiPart
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import java.io.File
import kotlin.reflect.full.declaredMemberProperties

class ImportsCrmV3Client(apiKey: String) : AbstractCrmV3Client(
  apiKey,
  "crm/v3/imports",
) {

  private val log: Logger = LogManager.getLogger(ImportsCrmV3Client::class.java)

  // for Java callers
  fun importFiles(importRequest: ImportRequest) = importFiles(importRequest, 0)

  fun importFiles(importRequest: ImportRequest, attemptCount: Int = 0): ImportResponse? {
    log.info("importing: {}", importRequest)
    var filePart = FileDataBodyPart(
        "files",
        File("/Users/vsydor/Desktop/123.csv"))
    var formDataMultiPart = FormDataMultiPart()
        formDataMultiPart
                .field("importRequest", objectMapper.writeValueAsString(importRequest))
                .bodyPart(filePart);

    val response = target
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA))
    return when (response.status) {
      200 -> {
        val responseEntity = response.readEntity(ImportResponse::class.java)
        log.info("HubSpot API response {}: {}", response.status, responseEntity)
        responseEntity
      }
      else -> {
        val retryFunction = { newAttemptCount: Int -> importFiles(importRequest, newAttemptCount) }
        handleError(response, attemptCount, retryFunction)
      }
    }
  }

}