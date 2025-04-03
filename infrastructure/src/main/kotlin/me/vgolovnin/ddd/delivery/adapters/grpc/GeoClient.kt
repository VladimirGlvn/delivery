package me.vgolovnin.ddd.delivery.adapters.grpc

import geo.GeoGrpc.GeoBlockingStub
import geo.GeoOuterClass
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.GeoGateway
import org.springframework.stereotype.Component

@Component
internal class GeoClient(
    private val geoServiceStub: GeoBlockingStub
) : GeoGateway {

    override fun findStreetLocation(street: String): Location {
        val request = GeoOuterClass.GetGeolocationRequest.newBuilder()
            .setStreet(street).build()

        return geoServiceStub.getGeolocation(request).location
            .run { Location(x, y) }
    }
}