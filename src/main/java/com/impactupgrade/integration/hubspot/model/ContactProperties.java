package com.impactupgrade.integration.hubspot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactProperties implements AbstractModel {

  private HasValue<String> firstname;
  private HasValue<String> lastname;
  @JsonProperty("email") 
  private HasValue<String> email;
  @JsonProperty("phone") 
  private HasValue<String> phone;
  @JsonProperty("home_phone")
  private HasValue<String> homePhone;
  @JsonProperty("mobile_phone") 
  private HasValue<String> mobilePhone;
  @JsonProperty("work_phone") 
  private HasValue<String> workPhone;
  @JsonProperty("preferred_phone") 
  private HasValue<String> preferredPhone;
  @JsonProperty("address") 
  private HasValue<String> address;
  @JsonProperty("city") 
  private HasValue<String> city;
  @JsonProperty("state") 
  private HasValue<String> state;
  @JsonProperty("zip") 
  private HasValue<String> zip;
  @JsonProperty("country") 
  private HasValue<String> country;

  public HasValue<String> getFirstName() {
    return firstname;
  }

  public void setFirstname(HasValue<String> firstname) {
    this.firstname = firstname;
  }

  public HasValue<String> getLastName() {
    return lastname;
  }

  public void setLastname(HasValue<String> lastname) {
    this.lastname = lastname;
  }

  public HasValue<String> getEmail() {
    return email;
  }

  public void setEmail(HasValue<String> email) {
    this.email = email;
  }

  public HasValue<String> getPhone() {
    return phone;
  }

  public void setPhone(HasValue<String> phone) {
    this.phone = phone;
  }

  public HasValue<String> getHomePhone() {
    return homePhone;
  }

  public void setHome_Phone(HasValue<String> home_phone) {
    this.homePhone = homePhone;
  }

  public HasValue<String> getWorkPhone() {
    return workPhone;
  }

  public void setWork_Phone(HasValue<String> work_phone) {
    this.workPhone = workPhone;
  }

  public HasValue<String> getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(HasValue<String> mobile_phone) {
    this.mobilePhone = mobilePhone;
  }

  public HasValue<String> getPreferredPhone() {
    return preferredPhone;
  }

  public void setPreferredPhone(HasValue<String> preferred_phone) {
    this.preferredPhone = preferredPhone;
  }

  public HasValue<String> getAddress() {
    return address;
  }

  public void setAddress(HasValue<String> address) {
    this.address = address;
  }

  public HasValue<String> getCity() {
    return city;
  }

  public void setCity(HasValue<String> city) {
    this.city = city;
  }

  public HasValue<String> getState() {
    return state;
  }

  public void setState(HasValue<String> state) {
    this.state = state;
  }

  public HasValue<String> getZip() {
    return zip;
  }

  public void setZip(HasValue<String> zip) {
    this.zip = zip;
  }

  public HasValue<String> getCountry() {
    return country;
  }

  public void setCountry(HasValue<String> country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "ContactProperties{" +
        "firstname=" + firstname +
        ", lastname=" + lastname +
        ", email=" + email +
        ", phone=" + phone +
        ", home_phone=" + homePhone +
        ", work_phone=" + workPhone +
        ", mobile_phone=" + mobilePhone +
        ", preferred_phone=" + preferredPhone +
        ", address=" + address +
        ", city=" + city +
        ", state=" + state +
        ", zip=" + zip +
        ", country=" + country +
        '}';
  }
}
