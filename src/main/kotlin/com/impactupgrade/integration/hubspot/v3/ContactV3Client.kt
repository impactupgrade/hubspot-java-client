package com.impactupgrade.integration.hubspot.v3

import com.impactupgrade.integration.hubspot.AbstractClient
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class ContactV3Client(apiKey: String) : AbstractClient(apiKey) {

  private val log: Logger = LogManager.getLogger(ContactV3Client::class.java)

  private val contactsTarget = target.path("crm/v3/objects/contacts")

  fun getById(id: String): Contact? {
    return contactsTarget
      .path(id)
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .get(Contact::class.java)
  }

  fun getByEmail(email: String): Contact? {
    val search = Search(listOf(FilterGroup(listOf(Filter("email", "EQ", email)))))
    val results = contactsTarget
      .path("search")
      .queryParam("hapikey", apiKey)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity<Any>(search, MediaType.APPLICATION_JSON))
      .readEntity(ContactResults::class.java)

    if (results.total == 0) {
      return null
    } else if (results.total == 1) {
      return results.results[0]
    } else {
      log.warn("{} resulted in {} contacts; returning the first", email, results.total)
      return results.results[0]
    }
  }
}