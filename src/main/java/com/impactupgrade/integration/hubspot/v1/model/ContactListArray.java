package com.impactupgrade.integration.hubspot.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContactListArray implements AbstractModel {

  private List<ContactList> lists;
  @JsonProperty("has-more")
  private boolean hasMore;
  private long offset;

  public List<ContactList> getLists() {
    return lists;
  }

  public void setLists(List<ContactList> lists) {
    this.lists = lists;
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
    return "ContactListWrapper{" +
        "lists=" + lists +
        ", hasMore=" + hasMore +
        ", offset=" + offset +
        '}';
  }
}
