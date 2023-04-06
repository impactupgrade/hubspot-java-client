package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.v1.builder.ContactBuilder;
import com.impactupgrade.integration.hubspot.v1.exception.DuplicateContactException;
import com.impactupgrade.integration.hubspot.v1.exception.HubSpotException;
import com.impactupgrade.integration.hubspot.v1.model.Contact;
import com.impactupgrade.integration.hubspot.v1.model.ContactArray;
import com.impactupgrade.integration.hubspot.v1.model.ContactRequest;
import com.impactupgrade.integration.hubspot.v1.model.internal.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Deprecated
public class ContactV1Client extends AbstractV1Client {

  private final Logger log = LogManager.getLogger(ContactV1Client.class);

  private final WebTarget contactsTarget = target.path("contacts/v1");

  public ContactV1Client(String apiKey) {
    super(apiKey);
  }

  @Deprecated
  public Contact getById(String id) throws InterruptedException {
    return _getById(0, id);
  }

  private Contact _getById(int count, String id) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete getById by attempt {}", count);
      return new Contact();
    }
    Response response = contactsTarget
        .path("contact/vid/" + id + "/profile")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get();

    if (response.getStatus() == 429) {
      log.warn("getById attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _getById(count + 1, id);
    }

    return response.readEntity(Contact.class);
  }

  @Deprecated
  public Contact getByEmail(String email) throws InterruptedException {
    return _getByEmail(0, email);
  }

  private Contact _getByEmail(int count, String email) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete getByEmail by attempt {}", count);
      return new Contact();
    }
    Response response = contactsTarget
        .path("contact/email/" + email + "/profile")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get();

    if (response.getStatus() == 429) {
      log.warn("getByEmail attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _getByEmail(count + 1, email);
    }

    return response.readEntity(Contact.class);
  }

  @Deprecated
  public String getByEmailBatch(List<String> emails) throws InterruptedException {
    return _getByEmailBatch(0 ,emails);
  }

  private String _getByEmailBatch(int count, List<String> emails) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete getByEmailBatch by attempt {}", count);
      return "";
    }
    Response response = contactsTarget
        .path("contact/emails/batch/")
        .queryParam("email", emails.toArray())
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get();

    if (response.getStatus() == 429) {
      log.warn("getByEmailBatch attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _getByEmailBatch(count + 1, emails);
    }

    return response.readEntity(String.class);
  }

  @Deprecated
  public ContactArray search(String q) throws InterruptedException {
    return _search(0, q);
  }

  private ContactArray _search(int count, String q) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete search by attempt {}", count);
      return new ContactArray();
    }
    Response response = contactsTarget
        .path("search/query")
        .queryParam("q", q)
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get();

    if (response.getStatus() == 429) {
      log.warn("search attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _search(count + 1, q);
    }

    return response.readEntity(ContactArray.class);
  }

  @Deprecated
  public Contact insert(ContactBuilder contactBuilder) throws DuplicateContactException, HubSpotException, InterruptedException {
    return _insert(0, contactBuilder);
  }

  private Contact _insert(int count, ContactBuilder contactBuilder) throws DuplicateContactException, HubSpotException, InterruptedException {
    if (count == 6) {
      log.error("unable to complete insert by attempt {}", count);
      return new Contact();
    }
    Response response = contactsTarget
        .path("contact")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(contactBuilder.build(), MediaType.APPLICATION_JSON));

    if (response.getStatus() == 409) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new DuplicateContactException(error.getIdentityProfile().getVid());
    } else if (response.getStatus() != 200) {
      ErrorResponse error = response.readEntity(ErrorResponse.class);
      throw new HubSpotException(error.getMessage());
    } else if (response.getStatus() == 429) {
      log.warn("insert attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _insert(count + 1, contactBuilder);
    } else {
      return response.readEntity(Contact.class);
    }
  }

  @Deprecated
  // TODO: Breaking change, but rename this to updateByEmail
  public Contact update(ContactBuilder contactBuilder, String email) throws InterruptedException {
    return _updateByEmail(0, contactBuilder, email);
  }

  private Contact _updateByEmail(int count, ContactBuilder contactBuilder, String email) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete updateByEmail by attempt {}", count);
      return new Contact();
    }
    ContactRequest contactRequest = contactBuilder.build();

    Response response = contactsTarget
        .path("contact/email/" + email + "/profile")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(contactRequest, MediaType.APPLICATION_JSON));

    if (response.getStatus() == 429) {
      log.warn("updateByEmail attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _updateByEmail(count + 1, contactBuilder, email);
    }

    return response.readEntity(Contact.class);
  }

  @Deprecated
  public Contact updateById(ContactBuilder contactBuilder, String id) throws InterruptedException {
    return _updateById(0, contactBuilder, id);
  }

  private Contact _updateById(int count, ContactBuilder contactBuilder, String id) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete updateById by attempt {}", count);
      return new Contact();
    }
    ContactRequest contactRequest = contactBuilder.build();

    Response response = contactsTarget
        .path("contact/vid/" + id + "/profile")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(contactRequest, MediaType.APPLICATION_JSON));

    if (response.getStatus() == 429) {
      log.warn("updateById attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _updateById(count + 1, contactBuilder, id);
    }

    return response.readEntity(Contact.class);
  }
}
