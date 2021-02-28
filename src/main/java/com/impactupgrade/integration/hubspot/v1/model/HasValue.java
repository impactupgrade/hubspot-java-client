package com.impactupgrade.integration.hubspot.v1.model;

public class HasValue<T> implements AbstractModel {

  private T value;

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
