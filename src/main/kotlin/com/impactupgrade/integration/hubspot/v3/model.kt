package com.impactupgrade.integration.hubspot.v3

import com.fasterxml.jackson.annotation.JsonProperty
import com.impactupgrade.integration.hubspot.v1.model.AbstractModel

data class Search(val filterGroups: List<FilterGroup>)
data class FilterGroup(val filters: List<Filter>) : AbstractModel
data class Filter(val propertyName: String, val operator: String, val value: String) : AbstractModel

data class Company(val id: String, val properties: CompanyProperties) : AbstractModel

// mutable -- used by clients for insert/update
data class CompanyProperties(
  var name: String = "",
  var address: String = "",
  var city: String = "",
  var state: String = "",
  var zip: String = "",
  var country: String = "",
) : AbstractModel

data class Contact(val id: String, val properties: ContactProperties) : AbstractModel

// mutable -- used by clients for insert/update
data class ContactProperties(
  var firstname: String = "",
  var lastname: String = "",
  var email: String = "",
  var phone: String = "",
  @JsonProperty("mobile_phone") var mobilePhone: String = "",
  var address: String = "",
  var city: String = "",
  var state: String = "",
  var zip: String = "",
  var country: String = "",
  @JsonProperty("associatedcompanyid") var companyId: String = "",
) : AbstractModel

data class ContactResults(val total: Int, val results: List<Contact>)