package me.vgolovnin.ddd.delivery.adapters.grpc

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "geo")
data class GeoServiceConfigurationProperties(
    val host: String,
)
