package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.v1.model.ContactArray;
import com.impactupgrade.integration.hubspot.v1.model.ContactListArray;
import com.impactupgrade.integration.hubspot.v1.model.VidsRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Deprecated
public class ContactListV1Client extends AbstractV1Client {

  private final WebTarget listsTarget = target.path("contacts/v1/lists");

  public ContactListV1Client(String apiKey) {
    super(apiKey);
  }

  @Deprecated
  public ContactListArray getAll() {
    return getAll(0);
  }

  private ContactListArray getAll(long offset) {
    ContactListArray contactListArray = listsTarget
        .queryParam("hapikey", apiKey)
        .queryParam("count", 250)
        .queryParam("offset", offset)
        .request(MediaType.APPLICATION_JSON)
        .get(ContactListArray.class);

    if (contactListArray.isHasMore()) {
      // iterate through all remaining pages
      contactListArray.getLists().addAll(getAll(contactListArray.getOffset()).getLists());
    }

    return contactListArray;
  }

  @Deprecated
  public ContactArray getContactsInList(long listId) {
    return getContactsInList(listId, 0);
  }

  private ContactArray getContactsInList(long listId, long offset) {
    ContactArray contactArray = listsTarget
        .path(listId + "/contacts/all")
        .queryParam("hapikey", apiKey)
        .queryParam("count", 100)
        .queryParam("vidOffset", offset)
        .queryParam("property", "firstname")
        .queryParam("property", "lastname")
        .queryParam("property", "email")
        .queryParam("property", "phone")
        .queryParam("property", "mobilephone")
        .queryParam("property", "hs_language")
        .request(MediaType.APPLICATION_JSON)
        .get(ContactArray.class);

    if (contactArray.isHasMore()) {
      // iterate through all remaining pages
      contactArray.getContacts().addAll(getContactsInList(listId, contactArray.getOffset()).getContacts());
    }

    return contactArray;
  }

  @Deprecated
  public boolean addContactToList(long listId, Long... contactVids) {
    VidsRequest vidsRequest = new VidsRequest();
    vidsRequest.setVids(Arrays.asList(contactVids));

    Response response = listsTarget
        .path(listId + "/add")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(vidsRequest, MediaType.APPLICATION_JSON));
    System.out.println(response.readEntity(String.class));
    return response.getStatus() == 200;
  }

  @Deprecated
  public boolean removeContactFromList(long listId, Long... contactVids) {
    VidsRequest vidsRequest = new VidsRequest();
    vidsRequest.setVids(Arrays.asList(contactVids));

    Response response = listsTarget
        .path(listId + "/remove")
        .queryParam("hapikey", apiKey)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(vidsRequest, MediaType.APPLICATION_JSON));
    System.out.println(response.readEntity(String.class));
    return response.getStatus() == 200;
  }
}
