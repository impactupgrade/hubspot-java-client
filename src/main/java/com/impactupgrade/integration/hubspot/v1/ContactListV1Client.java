package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.v1.model.ContactArray;
import com.impactupgrade.integration.hubspot.v1.model.ContactListArray;
import com.impactupgrade.integration.hubspot.v1.model.VidsRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Deprecated
public class ContactListV1Client extends AbstractV1Client {

  private final Logger log = LogManager.getLogger(ContactListV1Client.class);

  private final WebTarget listsTarget = target.path("contacts/v1/lists");

  public ContactListV1Client(String apiKey) {
    super(apiKey);
  }

  @Deprecated
  public ContactListArray getAll() throws InterruptedException {
    return getAll(0);
  }

  private ContactListArray getAll(long offset) throws InterruptedException {
    ContactListArray contactListArray = _getAll(0, offset);
    if (contactListArray.isHasMore()) {
      // iterate through all remaining pages
      contactListArray.getLists().addAll(getAll(contactListArray.getOffset()).getLists());
    }

    return contactListArray;
  }

  private ContactListArray _getAll(int count, long offset) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete getAll by attempt {}", count);
      return new ContactListArray();
    }
    Response response = listsTarget
        .queryParam("count", 250)
        .queryParam("offset", offset)
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get();

    if (response.getStatus() == 429) {
      log.warn("getAll attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _getAll(count + 1, offset);
    }

    return response.readEntity(ContactListArray.class);
  }

  @Deprecated
  public ContactArray getContactsInList(long listId, Collection<String> customProperties) throws InterruptedException {
    return getContactsInList(listId, customProperties, 0);
  }

  private ContactArray getContactsInList(long listId, Collection<String> customProperties, long offset) throws InterruptedException {
    Collection<String> properties = new ArrayList<>();
    properties.add("firstname");
    properties.add("lastname");
    properties.add("email");
    properties.add("phone");
    properties.add("mobilephone");
    properties.add("hs_language");
    if (customProperties != null) {
      properties.addAll(customProperties);
    }
    ContactArray contactArray = _getContactsInList(0, listId, properties, offset);

    if (contactArray.isHasMore()) {
      // iterate through all remaining pages
      contactArray.getContacts().addAll(getContactsInList(listId, customProperties, contactArray.getOffset()).getContacts());
    }

    return contactArray;
  }

  private ContactArray _getContactsInList(int count, long listId, Collection<String> properties, long offset) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete getContactsInList by attempt {}", count);
      return new ContactArray();
    }
    Response response = listsTarget
        .path(listId + "/contacts/all")
        .queryParam("count", 100)
        .queryParam("vidOffset", offset)
        .queryParam("property", properties.toArray())
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get();

    if (response.getStatus() == 429) {
      log.warn("getContactsInList attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _getContactsInList(count + 1, listId, properties, offset);
    }

    return response.readEntity(ContactArray.class);
  }

  @Deprecated
  public boolean addContactToList(long listId, Long... contactVids) throws InterruptedException {
    return _addContactToList(0, listId, contactVids);
  }

  private boolean _addContactToList(int count, long listId, Long... contactVids) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete addContactToList by attempt {}", count);
      return false;
    }
    VidsRequest vidsRequest = new VidsRequest();
    vidsRequest.setVids(Arrays.asList(contactVids));

    Response response = listsTarget
        .path(listId + "/add")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(vidsRequest, MediaType.APPLICATION_JSON));
    log.info("addContactToList response: {}", response.readEntity(String.class));

    if (response.getStatus() == 429) {
      log.warn("addContactToList attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _addContactToList(count + 1, listId, contactVids);
    }
    return response.getStatus() == 200;
  }

  @Deprecated
  public boolean removeContactFromList(long listId, Long... contactVids) throws InterruptedException {
    return _removeContactFromList(0, listId, contactVids);
  }

  private boolean _removeContactFromList(int count, long listId, Long... contactVids) throws InterruptedException {
    if (count == 6) {
      log.error("unable to complete removeContactFromList by attempt {}", count);
      return false;
    }
    VidsRequest vidsRequest = new VidsRequest();
    vidsRequest.setVids(Arrays.asList(contactVids));

    Response response = listsTarget
        .path(listId + "/remove")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(vidsRequest, MediaType.APPLICATION_JSON));
    log.info("removeContactFromList response: {}", response.readEntity(String.class));

    if (response.getStatus() == 429) {
      log.warn("removeContactFromList attempt {} failed due to API rate limit; retrying in 5s", count);
      Thread.sleep(5000);
      return _addContactToList(count + 1, listId, contactVids);
    }

    return response.getStatus() == 200;
  }
}
