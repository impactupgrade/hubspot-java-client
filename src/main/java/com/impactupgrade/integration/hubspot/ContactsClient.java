package com.impactupgrade.integration.hubspot;

import com.impactupgrade.integration.hubspot.builder.ContactBuilder;
import com.impactupgrade.integration.hubspot.exception.DuplicateContactException;
import com.impactupgrade.integration.hubspot.exception.HubSpotException;
import com.impactupgrade.integration.hubspot.model.Contact;
import com.impactupgrade.integration.hubspot.model.ContactArray;
import com.impactupgrade.integration.hubspot.model.ContactRequest;
import com.impactupgrade.integration.hubspot.model.internal.ErrorResponse;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ContactsClient extends AbstractClient {

  private final WebTarget contactsTarget = target.path("contacts/v1");

  public ContactsClient(String apiKey) {
    super(apiKey);
  }

  public Contact getById(String id) {
    return contactsTarget
        .path("contact/vid/" + id + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
  }

  public Contact getByEmail(String email) {
    return contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
  }

  public ContactArray search(String q) {
    return contactsTarget
        .path("search/query")
        .queryParam("q", q)
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(ContactArray.class);
  }

  public Contact insert(ContactBuilder contactBuilder) throws DuplicateContactException, HubSpotException {
    Response response = contactsTarget
        .path("contact")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(contactBuilder.build(), MediaType.APPLICATION_JSON));

    if (response.getStatus() == 409) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new DuplicateContactException(error.getIdentityProfile().getVid());
    } else if (response.getStatus() != 200) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new HubSpotException(error.getMessage());
    } else {
      return response.readEntity(Contact.class);
    }
  }

  // TODO: Breaking change, but rename this to updateByEmail
  public Contact update(ContactBuilder contactBuilder, String email) {

    ContactRequest req = contactBuilder.build();

    Response response = contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));

    return response.readEntity(Contact.class);
  }

  public Contact updateById(ContactBuilder contactBuilder, String id) {

    ContactRequest req = contactBuilder.build();

    Response response = contactsTarget
        .path("contact/vid/" + id + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));
    return response.readEntity(Contact.class);
  }
}
