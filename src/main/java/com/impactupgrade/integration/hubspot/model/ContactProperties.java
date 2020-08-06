package com.impactupgrade.integration.hubspot.model;

public class ContactProperties implements AbstractModel {

  private HasValue<String> firstname;
  private HasValue<String> lastname;
  private HasValue<String> email;
  private HasValue<String> phone;
  private HasValue<String> home_phone;
  private HasValue<String> mobile_phone;
  private HasValue<String> work_phone;
  private HasValue<String> preferred_phone;
  private HasValue<String> address;
  private HasValue<String> city;
  private HasValue<String> state;
  private HasValue<String> zip;
  private HasValue<String> country;

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

  public HasValue<String> getHome_Phone() {
    return home_phone;
  }

  public void setHome_Phone(HasValue<String> home_phone) {
    this.home_phone = home_phone;
  }

  public HasValue<String> getWork_Phone() {
    return work_phone;
  }

  public void setWork_Phone(HasValue<String> work_phone) {
    this.work_phone = work_phone;
  }

  public HasValue<String> getMobile_Phone() {
    return mobile_phone;
  }

  public void setMobile_Phone(HasValue<String> mobile_phone) {
    this.mobile_phone = mobile_phone;
  }

  public HasValue<String> getPreferred_Phone() {
    return preferred_phone;
  }

  public void setPreferred_Phone(HasValue<String> preferred_phone) {
    this.preferred_phone = preferred_phone;
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
        ", home_phone=" + home_phone +
        ", work_phone=" + work_phone +
        ", mobile_phone=" + mobile_phone +
        ", preferred_phone=" + preferred_phone +
        ", address=" + address +
        ", city=" + city +
        ", state=" + state +
        ", zip=" + zip +
        ", country=" + country +
        '}';
  }
}
