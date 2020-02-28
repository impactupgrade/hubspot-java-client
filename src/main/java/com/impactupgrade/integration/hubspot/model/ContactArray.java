package com.impactupgrade.integration.hubspot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContactArray implements AbstractModel {

  private List<Contact> contacts;
  @JsonProperty("has-more")
  private boolean hasMore;
  @JsonProperty("vid-offset")
  private long offset;

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

  public long getOffset() {
    return offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  @Override
  public String toString() {
    return "ContactArray{" +
        "contacts=" + contacts +
        ", hasMore=" + hasMore +
        ", offset=" + offset +
        '}';
  }
}
