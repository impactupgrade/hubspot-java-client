package com.impactupgrade.integration.hubspot.v3

import com.fasterxml.jackson.annotation.JsonProperty
import com.impactupgrade.integration.hubspot.v1.model.AbstractModel
import java.util.Calendar

data class Search(val filterGroups: List<FilterGroup>)
data class FilterGroup(val filters: List<Filter>) : AbstractModel
data class Filter(val propertyName: String, val operator: String, val value: String) : AbstractModel

data class Association(val from: String, val to: String, val type: String)

data class Company(val id: String, val properties: CompanyProperties) : AbstractModel

// mutable -- used by clients for insert/update
data class CompanyProperties(
  var name: String? = null,
  var address: String? = null,
  var city: String? = null,
  var state: String? = null,
  var zip: String? = null,
  var country: String? = null,
) : AbstractModel

data class Contact(val id: String, val properties: ContactProperties) : AbstractModel

// mutable -- used by clients for insert/update
data class ContactProperties(
  var firstname: String? = null,
  var lastname: String? = null,
  var email: String? = null,
  var phone: String? = null,
  @JsonProperty("mobile_phone") var mobilePhone: String? = null,
  var address: String? = null,
  var city: String? = null,
  var state: String? = null,
  var zip: String? = null,
  var country: String? = null,
  @JsonProperty("associatedcompanyid") var companyId: String? = null, // TODO: now have to use Associations API to *create* them, but hopefully this is still a read-only option
) : AbstractModel

data class ContactResults(val total: Int, val results: List<Contact>)

// mutable -- used by clients for insert/update
// TODO: campaign
data class DealProperties(
  var amount: Double? = null,
  var closedate: Calendar? = null, // TODO: expects 2019-12-07T16:50:06.678Z, so test this
  var dealname: String? = null,
  var dealstage: String? = null,
  var description: String? = null,
  var pipeline: String? = null,
  @JsonProperty("associatedcompanyid") var companyId: String? = null, // TODO: now have to use Associations API to *create* them, but hopefully this is still a read-only option
) : AbstractModel

data class Deal(val id: String, val properties: DealProperties) : AbstractModel