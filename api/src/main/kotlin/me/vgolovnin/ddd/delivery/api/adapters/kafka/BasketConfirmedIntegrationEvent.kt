package me.vgolovnin.ddd.delivery.api.adapters.kafka

import com.fasterxml.jackson.annotation.JsonProperty

data class BasketConfirmedIntegrationEvent(
    @JsonProperty("BasketId") val basketId: String,
    @JsonProperty("Address") val address: Address,
) {
    data class Address(
        @JsonProperty("Country") val country: String,
        @JsonProperty("City") val city: String,
        @JsonProperty("Street") val street: String,
        @JsonProperty("House") val house: String,
        @JsonProperty("Apartment") val apartment: String
    )
}
