package com.impactupgrade.integration.hubspot.exception;

public class DuplicateContactException extends Exception {

  private final long vid;

  public DuplicateContactException(long vid) {
    this.vid = vid;
  }

  public long getVid() {
    return vid;
  }
}
