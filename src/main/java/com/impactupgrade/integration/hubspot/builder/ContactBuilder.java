package com.impactupgrade.integration.hubspot.builder;

import com.impactupgrade.integration.hubspot.model.ContactRequest;
import com.impactupgrade.integration.hubspot.model.PropertyRequest;

import java.util.ArrayList;
import java.util.List;

public class ContactBuilder {

  private final List<PropertyRequest> properties = new ArrayList<>();

  public ContactBuilder firstName(String firstName) {
    properties.add(new PropertyRequest("firstname", firstName));
    return this;
  }

  public ContactBuilder lastName(String lastName) {
    properties.add(new PropertyRequest("lastname", lastName));
    return this;
  }

  public ContactBuilder email(String email) {
    properties.add(new PropertyRequest("email", email));
    return this;
  }

  public ContactBuilder phone(String phone) {
    properties.add(new PropertyRequest("phone", phone));
    return this;
  }

  public ContactRequest build() {
    ContactRequest contactRequest = new ContactRequest();
    contactRequest.setProperties(properties);
    return contactRequest;
  }
}
