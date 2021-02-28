package com.impactupgrade.integration.hubspot.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Contact implements AbstractModel {

  private long vid;
  @JsonProperty("canonical-vid")
  private long canonicalVid;
  @JsonProperty("portal-id")
  private long portalId;
  private ContactProperties properties;
  @JsonProperty("list-memberships")
  private List<ContactListMembership> listMemberships;

  // TODO: Remove? Specific to an Impact Upgrade client or two...
  @JsonProperty("salesforcecontactid")
  private String salesforceContactId;
  @JsonProperty("salesforceaccountid")
  private String salesforceAccountId;

  public long getVid() {
    return vid;
  }

  public void setVid(long vid) {
    this.vid = vid;
  }

  public long getCanonicalVid() {
    return canonicalVid;
  }

  public void setCanonicalVid(long canonicalVid) {
    this.canonicalVid = canonicalVid;
  }

  public long getPortalId() {
    return portalId;
  }

  public void setPortalId(long portalId) {
    this.portalId = portalId;
  }

  public ContactProperties getProperties() {
    return properties;
  }

  public void setProperties(ContactProperties properties) {
    this.properties = properties;
  }

  public List<ContactListMembership> getListMemberships() {
    return listMemberships;
  }

  public void setListMemberships(List<ContactListMembership> listMemberships) {
    this.listMemberships = listMemberships;
  }

  public String getSalesforceContactId() {
    return salesforceContactId;
  }

  public void setSalesforceContactId(String salesforceContactId) {
    this.salesforceContactId = salesforceContactId;
  }

  public String getSalesforceAccountId() {
    return salesforceAccountId;
  }

  public void setSalesforceAccountId(String salesforceAccountId) {
    this.salesforceAccountId = salesforceAccountId;
  }

  @Override
  public String toString() {
    return "Contact{" +
        "vid=" + vid +
        ", canonicalVid=" + canonicalVid +
        ", portalId=" + portalId +
        ", properties=" + properties +
        ", listMemberships=" + listMemberships +
        ", salesforceContactId='" + salesforceContactId + '\'' +
        ", salesforceAccountId='" + salesforceAccountId + '\'' +
        '}';
  }
}
