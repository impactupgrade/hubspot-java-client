package com.impactupgrade.integration.hubspot.v3

class HubSpotV3Client(private val apiKey: String) {

  fun association(): AssociationV3Client {
    return AssociationV3Client(apiKey)
  }

  fun company(): CompanyV3Client {
    return CompanyV3Client(apiKey)
  }

  fun contact(): ContactV3Client {
    return ContactV3Client(apiKey)
  }

  fun deal(): DealV3Client {
    return DealV3Client(apiKey)
  }
}