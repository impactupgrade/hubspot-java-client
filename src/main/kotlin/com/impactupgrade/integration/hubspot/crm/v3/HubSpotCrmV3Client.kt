package com.impactupgrade.integration.hubspot.crm.v3

class HubSpotCrmV3Client(private val apiKey: String) {

  fun association() = AssociationCrmV3Client(apiKey)
  fun company() = CompanyCrmV3Client(apiKey)
  fun contact() = ContactCrmV3Client(apiKey)
  fun deal() = DealCrmV3Client(apiKey)
  fun imports() = ImportsCrmV3Client(apiKey)
  fun properties() = PropertiesCrmV3Client(apiKey)
}