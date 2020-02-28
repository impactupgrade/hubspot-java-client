package com.impactupgrade.integration.hubspot.model;

public class ContactProperties implements AbstractModel {

  private HasValue<String> firstname;
  private HasValue<String> lastname;
  private HasValue<String> email;
  private HasValue<String> phone;

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

  @Override
  public String toString() {
    return "ContactProperties{" +
        "firstname=" + firstname +
        ", lastname=" + lastname +
        ", email=" + email +
        ", phone=" + phone +
        '}';
  }
}
