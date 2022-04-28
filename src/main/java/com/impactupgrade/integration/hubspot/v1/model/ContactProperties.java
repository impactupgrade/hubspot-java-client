package com.impactupgrade.integration.hubspot.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactProperties implements AbstractModel {

  private HasValue<String> firstname;
  private HasValue<String> lastname;
  private HasValue<String> email;
  private HasValue<String> phone;
  @JsonProperty("mobilephone")
  private HasValue<String> mobilePhone;
  private HasValue<String> address;
  private HasValue<String> city;
  private HasValue<String> state;
  private HasValue<String> zip;
  private HasValue<String> country;
  @JsonProperty("associatedcompanyid")
  private HasValue<String> companyId;
  @JsonProperty("hs_language")
  private HasValue<String> language;

  public HasValue<String> getFirstname() {
    return firstname;
  }

  public void setFirstname(HasValue<String> firstname) {
    this.firstname = firstname;
  }

  public HasValue<String> getLastname() {
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

  public HasValue<String> getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(HasValue<String> mobilePhone) {
    this.mobilePhone = mobilePhone;
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

  public HasValue<String> getCompanyId() { return companyId; }

  public void setCompanyId(HasValue<String> companyId) { this.companyId = companyId; }

  public HasValue<String> getLanguage() {
    return language;
  }

  public void setLanguage(HasValue<String> language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "ContactProperties{" +
        "firstname=" + firstname +
        ", lastname=" + lastname +
        ", email=" + email +
        ", phone=" + phone +
        ", mobile_phone=" + mobilePhone +
        ", address=" + address +
        ", city=" + city +
        ", state=" + state +
        ", zip=" + zip +
        ", country=" + country +
        ", companyId=" + companyId +
        ", language=" + language +
        '}';
  }
}
