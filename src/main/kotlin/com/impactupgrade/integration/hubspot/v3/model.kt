package com.impactupgrade.integration.hubspot.v3

import com.fasterxml.jackson.annotation.JsonProperty
import com.impactupgrade.integration.hubspot.v1.model.AbstractModel

data class Search(val filterGroups: List<FilterGroup>)
data class FilterGroup(val filters: List<Filter>) : AbstractModel
data class Filter(val propertyName: String, val operator: String, val value: String) : AbstractModel

data class Contact(val id: String, val properties: ContactProperties) : AbstractModel

// TODO: Assuming these all work, but test it
data class ContactProperties(
  val firstname: String,
  val lastname: String,
  val email: String,
  val phone: String,
  // TODO: are the *_phone fields standard?
  @JsonProperty("home_phone") val homePhone: String,
  @JsonProperty("mobile_phone") val mobilePhone: String,
  @JsonProperty("work_phone") val workPhone: String,
  @JsonProperty("preferred_phone") val preferredPhone: String,
  val address: String,
  val city: String,
  val state: String,
  val zip: String,
  val country: String,
  @JsonProperty("associatedcompanyid") val companyId: String,
) : AbstractModel

data class ContactResults(val total: Int, val results: List<Contact>)