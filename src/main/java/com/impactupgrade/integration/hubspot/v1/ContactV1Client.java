package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.exception.HubSpotException;
import com.impactupgrade.integration.hubspot.v1.builder.ContactBuilder;
import com.impactupgrade.integration.hubspot.v1.exception.DuplicateContactException;
import com.impactupgrade.integration.hubspot.v1.model.Contact;
import com.impactupgrade.integration.hubspot.v1.model.ContactArray;
import com.impactupgrade.integration.hubspot.v1.model.ContactRequest;
import com.impactupgrade.integration.hubspot.v1.model.internal.ErrorResponse;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Deprecated
public class ContactV1Client extends AbstractV1Client {

  private final WebTarget contactsTarget = target.path("contacts/v1");

  public ContactV1Client(String apiKey) {
    super(apiKey);
  }

  @Deprecated
  public Contact getById(String id) {
    return contactsTarget
        .path("contact/vid/" + id + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
  }

  @Deprecated
  public Contact getByEmail(String email) {
    return contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
  }

  @Deprecated
  public String getByEmailBatch(List<String> emails) {
    return contactsTarget
        .path("contact/emails/batch/")
        .queryParam("email", emails.toArray())
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(String.class);
  }

  @Deprecated
  public ContactArray search(String q) {
    return contactsTarget
        .path("search/query")
        .queryParam("q", q)
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(ContactArray.class);
  }

  @Deprecated
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

  @Deprecated
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

  @Deprecated
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
