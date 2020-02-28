package com.impactupgrade.integration.hubspot;

import com.impactupgrade.integration.hubspot.model.Contact;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ContactsClient extends AbstractClient {

  private final WebTarget contactsTarget = target.path("contacts/v1");

  public ContactsClient(String apiKey) {
    super(apiKey);
  }

  public Contact getByEmail(String email) {
    return contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
  }
}
