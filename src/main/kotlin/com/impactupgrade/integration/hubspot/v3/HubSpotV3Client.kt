package com.impactupgrade.integration.hubspot.v3

class HubSpotV3Client(private val apiKey: String) {

  fun company(): CompanyV3Client {
    return CompanyV3Client(apiKey)
  }

  fun contact(): ContactV3Client {
    return ContactV3Client(apiKey)
  }
}