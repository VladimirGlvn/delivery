package me.vgolovnin.ddd.delivery.core.ports

import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location

interface GeoGateway {

    fun findStreetLocation(street: String): Location
}