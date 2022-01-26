package com.impactupgrade.integration.hubspot.form.v3

import com.impactupgrade.integration.hubspot.Form
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class FormV3Client(private val portalId: String) {

  private val log: Logger = LogManager.getLogger(FormV3Client::class.java)

  fun submit(formId: String, form: Form) {
    log.info("submitting form: {} {} {}", form)

    val response = ClientBuilder.newBuilder().build()
      .target("https://api.hsforms.com/submissions/v3/integration/submit")
      .path(portalId).path(formId)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(form, MediaType.APPLICATION_JSON))
    when (response.status) {
      200 -> {
        log.info("HubSpot API response {}: {}", response.status, response.readEntity(String::class.java))
      }
      else -> {
        log.warn("HubSpot API error {}: {}", response.status, response.readEntity(String::class.java))
      }
    }
  }
}