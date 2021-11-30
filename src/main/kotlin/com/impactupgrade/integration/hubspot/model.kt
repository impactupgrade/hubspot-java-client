package com.impactupgrade.integration.hubspot.crm.v3

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Calendar

@JsonIgnoreProperties(ignoreUnknown = true)
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
data class Filter(val propertyName: String, val operator: String, val value: String)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Next(var after: String? = null)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CompanyProperties(
  var name: String? = null,
  var address: String? = null,
  var city: String? = null,
  var state: String? = null,
  var zip: String? = null,
  var country: String? = null,
  var description: String? = null,
  @JsonProperty("hubspot_owner_id") var ownerId: String? = null,
  @get:JsonAnyGetter @JsonAnySetter val otherProperties: Map<String, Any> = mutableMapOf(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Company(var id:String? = null, val properties: CompanyProperties)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompanyResults(val total: Int, val results: List<Company>)

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
  @JsonProperty("hubspot_owner_id") var ownerId: String? = null,
  @get:JsonAnyGetter @JsonAnySetter val otherProperties: Map<String, Any> = mutableMapOf(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Contact(var id:String? = null, val properties: ContactProperties)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactResults(val total: Int, val results: List<Contact>)

// TODO: campaign
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
  @JsonProperty("hubspot_owner_id") var ownerId: String? = null,
  @get:JsonAnyGetter @JsonAnySetter val otherProperties: Map<String, Any> = mutableMapOf(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Deal(var id: String? = null, val properties: DealProperties)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DealBatchResults(val results: List<Deal>)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DealResults(val total: Int, val results: List<Deal>, var paging: Paging? = null)

data class FormField(val name: String, val value: String)
data class FormContext(
  var hutk: String? = null,
  var pageId: String? = null,
  var pageUri: String? = null,
  var pageName: String? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Form(val fields: List<FormField>, var context: FormContext? = null)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiError(var status: String? = null, var message: String? = null, var correlationId: String? = null, var category: String? = null)