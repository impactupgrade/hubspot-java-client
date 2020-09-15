package com.impactupgrade.integration.hubspot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactListMembership implements AbstractModel {

  @JsonProperty("static-list-id")
  private Integer listId;

  public Integer getListId() {
    return listId;
  }

  public void setListId(Integer listId) {
    this.listId = listId;
  }

  @Override
  public String toString() {
    return "ContactListMembership{" +
        "listId=" + listId +
        '}';
  }
}
