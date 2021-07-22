package com.impactupgrade.integration.hubspot.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EngagementAssociations implements AbstractModel  {

  @JsonProperty("contactIds")
  private List<Long> contactIds;
  @JsonProperty("companyIds")
  private List<Long> companyIds;
  @JsonProperty("dealIds")
  private List<Long> dealIds;

  public List<Long> getContactIds() {
    return contactIds;
  }

  public void setContactIds(List<Long> contactIds) {
    this.contactIds = contactIds;
  }

  public List<Long> getCompanyIds() {
    return companyIds;
  }

  public void setCompanyIds(List<Long> companyIds) {
    this.companyIds = companyIds;
  }

  public List<Long> getDealIds() {
    return dealIds;
  }

  public void setDealIds(List<Long> dealIds) {
    this.dealIds = dealIds;
  }

  @Override
  public String toString() {
    return "EngagementAssociations{" +
        "contactIds=" + contactIds +
        ", companyIds=" + companyIds +
        ", dealIds=" + dealIds +
        '}';
  }
}
