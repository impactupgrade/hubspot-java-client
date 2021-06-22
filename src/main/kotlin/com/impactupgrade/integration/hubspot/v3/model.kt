package com.impactupgrade.integration.hubspot.v3

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Calendar

data class Search(
  val filterGroups: List<FilterGroup>,
  val properties: List<String> = listOf()
)
data class FilterGroup(val filters: List<Filter>)
data class Filter(val propertyName: String, val operator: String, val value: String)

data class HasId(val id: String)
data class AssociationInput(val from: HasId, val to: HasId, val type: String)
data class AssociationBatch(val inputs: List<AssociationInput>)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CompanyProperties(
  var name: String? = null,
  var address: String? = null,
  var city: String? = null,
  var state: String? = null,
  var zip: String? = null,
  var country: String? = null,
  @get:JsonAnyGetter var customProperties: Map<String, Any> = mutableMapOf(),
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Company(var id:String? = null, val properties: CompanyProperties)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompanyResults(val total: Int, val results: List<Company>)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContactProperties(
  var firstname: String? = null,
  var lastname: String? = null,
  var email: String? = null,
  var phone: String? = null,
  var mobilephone: String? = null,
  var address: String? = null,
  var city: String? = null,
  var state: String? = null,
  var zip: String? = null,
  var country: String? = null,
  var associatedcompanyid: String? = null,
  @get:JsonAnyGetter var customProperties: Map<String, Any> = mutableMapOf(),
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Contact(var id:String? = null, val properties: ContactProperties)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactResults(val total: Int, val results: List<Contact>)

// TODO: campaign
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DealProperties(
  var amount: Double? = null,
  var closedate: Calendar? = null, // TODO: expects 2019-12-07T16:50:06.678Z, so test this
  var dealname: String? = null,
  var dealstage: String? = null,
  var description: String? = null,
  var pipeline: String? = null,
  @JsonProperty("recurring_revenue_amount") var recurringRevenueAmount: Double? = null,
  @JsonProperty("recurring_revenue_deal_type") var recurringRevenueDealType: String? = null,
  @JsonProperty("recurring_revenue_inactive_date") var recurringRevenueInactiveDate: Calendar? = null,
  @JsonProperty("recurring_revenue_inactive_reason") var recurringRevenueInactiveReason: String? = null,
  var associatedcompanyid: String? = null,
  @get:JsonAnyGetter var customProperties: Map<String, Any> = mutableMapOf(),
) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Deal(var id:String? = null, val properties: DealProperties)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DealResults(val total: Int, val results: List<Deal>)