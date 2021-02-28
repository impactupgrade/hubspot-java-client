package com.impactupgrade.integration.hubspot.v1.builder;

import com.impactupgrade.integration.hubspot.v1.model.ContactRequest;
import com.impactupgrade.integration.hubspot.v1.model.PropertyRequest;

import java.util.ArrayList;
import java.util.List;

// TODO: Could we instead use Contact/ContactProperties?
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

  public ContactBuilder homePhone(String homePhone) {
    properties.add(new PropertyRequest("home_phone", homePhone));
    return this;
  }

  public ContactBuilder mobilePhone(String mobilePhone) {
    properties.add(new PropertyRequest("mobile_phone", mobilePhone));
    return this;
  }

  public ContactBuilder workPhone(String workPhone) {
    properties.add(new PropertyRequest("work_phone", workPhone));
    return this;
  }

  public ContactBuilder preferredPhone(String preferredPhone) {
    properties.add(new PropertyRequest("preferred_phone", preferredPhone));
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
