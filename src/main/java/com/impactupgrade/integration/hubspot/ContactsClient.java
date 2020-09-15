package com.impactupgrade.integration.hubspot;

import com.impactupgrade.integration.hubspot.builder.ContactBuilder;
import com.impactupgrade.integration.hubspot.exception.DuplicateContactException;
import com.impactupgrade.integration.hubspot.exception.HubSpotException;
import com.impactupgrade.integration.hubspot.model.Contact;
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

  public Contact getByEmail(String email) {
    return contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
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

  public Contact update(ContactBuilder contactBuilder, String email) {

    ContactRequest req = contactBuilder.build();

    Response response = contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));

    return response.readEntity(Contact.class);
  }

  public static void main(String[] args) {
    System.out.println(new ContactsClient("56fac03b-0260-411b-9f8d-ad9c77746da9").getByEmail("brett.e.meyer@gmail.com"));
  }
}
