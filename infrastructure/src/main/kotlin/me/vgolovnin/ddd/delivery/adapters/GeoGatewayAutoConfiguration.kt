package me.vgolovnin.ddd.delivery.adapters

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.ComponentScan

@AutoConfiguration
@ComponentScan(basePackages = ["me.vgolovnin.ddd.delivery.adapters.grpc"])
internal class GeoGatewayAutoConfiguration