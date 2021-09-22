package com.impactupgrade.integration.hubspot.crm.v3

fun normalizePhoneNumber(phone: String): String {
  // Hubspot doesn't seem to support country codes when phone numbers are used to search. Strip it off.
  return phone.replace("+1", "")
}