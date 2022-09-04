package com.impactupgrade.integration.hubspot

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Calendar

data class BatchRead(
  val inputs: List<HasId>,
  val properties: List<String>
)

data class Search(
  val filterGroups: List<FilterGroup>,
  val properties: List<String>,
  val after: Int = 0,
  // always use the max
  val limit: Int = 100
)
data class FilterGroup(val filters: List<Filter>)
data class Filter(val propertyName: String, val operator: String, var value: String? = null)

data class Next(var after: String? = null)
data class Paging(var next: Next? = null)

@JsonIgnoreProperties(ignoreUnknown = true)
data class HasId(val id: String)

data class AssociationInsertBatch(val inputs: List<AssociationInsertInput>)
data class AssociationInsertInput(val from: HasId, val to: HasId, val type: String)

data class AssociationSearchBatch(val inputs: List<HasId>)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AssociationSearchResults(val results: List<AssociationSearchResult>)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AssociationSearchResult(val from: HasId, val to: List<HasId>)

data class CompanyProperties(
  var name: String? = null,
  var address: String? = null,
  var city: String? = null,
  var state: String? = null,
  var zip: String? = null,
  var country: String? = null,
  var description: String? = null,
  @JsonProperty("hubspot_owner_id") var ownerId: String? = null,
) {
  @get:JsonAnyGetter @JsonAnySetter val otherProperties: MutableMap<String, Object> = mutableMapOf()
}

data class Company(var id:String? = null, val properties: CompanyProperties)

data class CompanyResults(val total: Int, val results: List<Company>, var paging: Paging? = null)

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
  @JsonProperty("hubspot_owner_id") var ownerId: String? = null,
  @JsonProperty("hs_language") var language: String? = null,
) {
  @get:JsonAnyGetter @JsonAnySetter val otherProperties: MutableMap<String, Object> = mutableMapOf()
}

data class Contact(var id:String? = null, val properties: ContactProperties)

data class ContactResults(val total: Int, val results: List<Contact>, var paging: Paging? = null)

// TODO: campaign
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
  @JsonProperty("hubspot_owner_id") var ownerId: String? = null,
) {
  @get:JsonAnyGetter @JsonAnySetter val otherProperties: MutableMap<String, Object> = mutableMapOf()
}

data class Deal(var id: String? = null, val properties: DealProperties)

data class DealBatchResults(val results: List<Deal>)

data class DealResults(val total: Int, val results: List<Deal>, var paging: Paging? = null)

data class FormField(val name: String, val value: String)
data class FormContext(
  var hutk: String? = null,
  var pageId: String? = null,
  var pageUri: String? = null,
  var pageName: String? = null
)
data class Form(val fields: List<FormField>, var context: FormContext? = null)

data class PropertyOption(
  var label: String? = null,
  var value: String? = null,
  var displayOrder: Int? = null,
  var hidden: Boolean? = null
)

data class Property(
  var name: String? = null,
  var label: String? = null,
  var type: String? = null,
  var fieldType: String? = null,
  var groupName: String? = null,

  var description: String? = null,
  var options: List<PropertyOption>? = null,
  var updatedAt: Calendar? = null,
  var createdAt: Calendar? = null,
  var createdUserId: String? = null,
  var updatedUserId: String? = null,
  var displayOrder: Int? = null,
  var calculated: Boolean? = null,
  var externalOptions: Boolean? = null,
  var archived: Boolean? = null,
  var hasUniqueValue: Boolean? = null,
  var hidden: Boolean? = null,
  var showCurrencySymbol: Boolean? = null,
  var formField: Boolean? = null,
  @get:JsonAnyGetter @JsonAnySetter val modificationMetadata: Map<String, Any> = mutableMapOf()
)

data class PropertyBatchCreateRequest(
  val inputs: List<Property>? = null
)

data class PropertiesResponse(var status: String? = null, val results: List<Property>)

data class ApiError(var status: String? = null, var message: String? = null, var correlationId: String? = null, var category: String? = null)

class HubSpotException(message: String) : Exception(message)