package me.vgolovnin.ddd.delivery.adapters.grpc

import geo.GeoGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import java.util.concurrent.TimeUnit

@AutoConfiguration
@ComponentScan(basePackages = ["me.vgolovnin.ddd.delivery.adapters.grpc"])
@EnableConfigurationProperties(GeoServiceConfigurationProperties::class)
internal class GeoServiceAutoConfiguration(properties: GeoServiceConfigurationProperties) : DisposableBean {

    private val channel: ManagedChannel = ManagedChannelBuilder.forTarget(properties.host).usePlaintext().build()

    @Bean
    fun geoServiceStub(): GeoGrpc.GeoBlockingStub = GeoGrpc.newBlockingStub(channel)

    override fun destroy() {
        channel.shutdown().awaitTermination(3, TimeUnit.SECONDS)
    }
}