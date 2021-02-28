package com.impactupgrade.integration.hubspot.v1.model;

public class ContactList implements AbstractModel {

  private long listId;
  private String name;

  public long getListId() {
    return listId;
  }

  public void setListId(long listId) {
    this.listId = listId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ContactList{" +
        "listId=" + listId +
        ", name='" + name + '\'' +
        '}';
  }
}
