package com.impactupgrade.integration.hubspot;

import com.impactupgrade.integration.hubspot.model.ContactArray;
import com.impactupgrade.integration.hubspot.model.ContactListArray;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ListsClient extends AbstractClient {

  private final WebTarget listsTarget = target.path("contacts/v1/lists");

  public ListsClient(String apiKey) {
    super(apiKey);
  }

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
        .request(MediaType.APPLICATION_JSON)
        .get(ContactArray.class);

    if (contactArray.isHasMore()) {
      // iterate through all remaining pages
      contactArray.getContacts().addAll(getContactsInList(contactArray.getOffset()).getContacts());
    }

    return contactArray;
  }
}
