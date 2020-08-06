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

  public ContactBuilder home_phone(String home_phone) {
    properties.add(new PropertyRequest("home_phone", home_phone));
    return this;
  }

  public ContactBuilder mobile_phone(String mobile_phone) {
    properties.add(new PropertyRequest("mobile_phone", mobile_phone));
    return this;
  }

  public ContactBuilder work_phone(String work_phone) {
    properties.add(new PropertyRequest("work_phone", work_phone));
    return this;
  }

  public ContactBuilder preferred_phone(String preferred_phone) {
    properties.add(new PropertyRequest("preferred_phone", preferred_phone));
    return this;
  }
  
  public ContactBuilder address(String address) {
    properties.add(new PropertyRequest("address", address));
    return this;
  }

  public ContactBuilder city(String city) {
    properties.add(new PropertyRequest("city", city));
    return this;
  }

  public ContactBuilder state(String state) {
    properties.add(new PropertyRequest("state", state));
    return this;
  }

  public ContactBuilder zip(String zip) {
    properties.add(new PropertyRequest("zip", zip));
    return this;
  }

  public ContactBuilder country(String country) {
    properties.add(new PropertyRequest("country", country));
    return this;
  }

  public ContactRequest build() {
    ContactRequest contactRequest = new ContactRequest();
    contactRequest.setProperties(properties);
    return contactRequest;
  }
}
