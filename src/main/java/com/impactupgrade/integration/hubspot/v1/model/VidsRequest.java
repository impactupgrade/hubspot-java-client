package com.impactupgrade.integration.hubspot.v1.model;

import java.util.List;

public class VidsRequest implements AbstractModel {

  private List<Long> vids;

  public List<Long> getVids() {
    return vids;
  }

  public void setVids(List<Long> vids) {
    this.vids = vids;
  }

  @Override
  public String toString() {
    return "VidsRequest{" +
        "vids=" + vids +
        '}';
  }
}
