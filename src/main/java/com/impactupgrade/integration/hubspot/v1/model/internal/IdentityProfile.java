package com.impactupgrade.integration.hubspot.v1.model.internal;

import com.impactupgrade.integration.hubspot.v1.model.AbstractModel;

public class IdentityProfile implements AbstractModel {

  private long vid;

  public long getVid() {
    return vid;
  }

  public void setVid(long vid) {
    this.vid = vid;
  }

  @Override
  public String toString() {
    return "IdentityProfile{" +
        "vid=" + vid +
        '}';
  }
}
