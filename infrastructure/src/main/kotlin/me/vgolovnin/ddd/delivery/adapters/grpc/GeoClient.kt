package me.vgolovnin.ddd.delivery.adapters.grpc

import geo.GeoGrpc
import geo.GeoOuterClass
import io.grpc.ManagedChannelBuilder
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.GeoGateway
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
internal class GeoClient : GeoGateway {

    override fun findStreetLocation(street: String): Location {
        val channel = ManagedChannelBuilder.forTarget("localhost:5004").usePlaintext().build()
        val stub = GeoGrpc.newBlockingStub(channel)

        val request = GeoOuterClass.GetGeolocationRequest.newBuilder().setStreet("123").build()

        val response = stub.getGeolocation(request)
        val location = response.location
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)

        return Location(location.x, location.y)
    }
}