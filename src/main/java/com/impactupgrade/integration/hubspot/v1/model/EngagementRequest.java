package com.impactupgrade.integration.hubspot.v1.model;

public class EngagementRequest implements AbstractModel  {

  private long id;
  private Engagement engagement;
  private EngagementAssociations associations;
  private EngagementMetadata metadata;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Engagement getEngagement() {
    return engagement;
  }

  public void setEngagement(Engagement engagement) {
    this.engagement = engagement;
  }

  public EngagementAssociations getAssociations() {
    return associations;
  }

  public void setAssociations(EngagementAssociations associations) {
    this.associations = associations;
  }

  public EngagementMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(EngagementMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public String toString() {
    return "EngagementRequest{" +
        "id=" + id +
        ", engagement=" + engagement +
        ", associations=" + associations +
        ", metadata=" + metadata +
        '}';
  }
}
