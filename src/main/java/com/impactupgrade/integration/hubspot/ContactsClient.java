package com.impactupgrade.integration.hubspot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsClient extends AbstractClient {

  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  private final WebTarget contactsTarget = target.path("contacts/v1");

  public ContactsClient(String apiKey) {
    super(apiKey);
  }

  public Contact getByEmail(String email) {
    Contact c = contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .get(Contact.class);
    LOGGER.log(Level.INFO, c.toString());
    return c;
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

  public Contact update(
    String email,
    String first_name,
    String last_name,
    String home_phone,
    String mobile_phone,
    String work_phone,
    String preferred_phone,
    String address,
    String city,
    String state,
    String zip,
    String country) {

    ContactBuilder cBuilder = new ContactBuilder();
    cBuilder.firstName(first_name);
    LOGGER.log(Level.INFO, first_name);
    cBuilder.lastName(last_name);
    LOGGER.log(Level.INFO, last_name);

    cBuilder.home_phone(home_phone);
    LOGGER.log(Level.INFO, home_phone);
    cBuilder.mobile_phone(mobile_phone);
    LOGGER.log(Level.INFO, mobile_phone);
    cBuilder.work_phone(work_phone);
    LOGGER.log(Level.INFO, work_phone);
    cBuilder.preferred_phone(preferred_phone);
    LOGGER.log(Level.INFO, preferred_phone);

    if (preferred_phone == "Home") {
      cBuilder.phone(home_phone);
    } else if (preferred_phone == "Mobile") {
      cBuilder.phone(mobile_phone);
    } else if (preferred_phone == "Work") {
      cBuilder.phone(work_phone);
    }

    cBuilder.address(address);
    LOGGER.log(Level.INFO, address);
    cBuilder.city(city);
    LOGGER.log(Level.INFO, city);
    cBuilder.state(state);
    LOGGER.log(Level.INFO, state);
    cBuilder.zip(zip);
    LOGGER.log(Level.INFO, zip);
    cBuilder.country(country);
    LOGGER.log(Level.INFO, country);
    ContactRequest cr = cBuilder.build();

    // Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // String contactUpdate = gson.toJson(cr);
    // LOGGER.log(Level.INFO, contactUpdate);

    Response response = contactsTarget
        .path("contact/email/" + email + "/profile")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(cr, MediaType.APPLICATION_JSON));

    LOGGER.log(Level.INFO, "Updating contact: " + first_name + " " + last_name);
      
    return response.readEntity(Contact.class);
  }

  public static void main(String[] args) {
    // new ContactsClient("56fac03b-0260-411b-9f8d-ad9c77746da9").getByEmail("brett.e.meyer@gmail.com");
  }
}
