package com.impactupgrade.integration.hubspot.v1;

import com.impactupgrade.integration.hubspot.v1.model.ContactArray;
import com.impactupgrade.integration.hubspot.v1.model.ContactListArray;
import com.impactupgrade.integration.hubspot.v1.model.VidsRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
        .queryParam("count", 250)
        .queryParam("offset", offset)
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get(ContactListArray.class);

    if (contactListArray.isHasMore()) {
      // iterate through all remaining pages
      contactListArray.getLists().addAll(getAll(contactListArray.getOffset()).getLists());
    }

    return contactListArray;
  }

  @Deprecated
  public ContactArray getContactsInList(long listId, Collection<String> customProperties) {
    return getContactsInList(listId, customProperties, 0);
  }

  private ContactArray getContactsInList(long listId, Collection<String> customProperties, long offset) {
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
    ContactArray contactArray = listsTarget
        .path(listId + "/contacts/all")
        .queryParam("count", 100)
        .queryParam("vidOffset", offset)
        .queryParam("property", properties.toArray())
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .get(ContactArray.class);

    if (contactArray.isHasMore()) {
      // iterate through all remaining pages
      contactArray.getContacts().addAll(getContactsInList(listId, customProperties, contactArray.getOffset()).getContacts());
    }

    return contactArray;
  }

  @Deprecated
  public boolean addContactToList(long listId, Long... contactVids) {
    VidsRequest vidsRequest = new VidsRequest();
    vidsRequest.setVids(Arrays.asList(contactVids));

    Response response = listsTarget
        .path(listId + "/add")
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
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
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + apiKey)
        .post(Entity.entity(vidsRequest, MediaType.APPLICATION_JSON));
    System.out.println(response.readEntity(String.class));
    return response.getStatus() == 200;
  }
}
