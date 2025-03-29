package me.vgolovnin.ddd.delivery.core.application.usecases

import java.util.*

object BusyCouriersQuery

data class BusyCouriersResponse(val couriers: List<Courier>)

data class Courier(val id: UUID, val name: String, val location: Location, val transport: String)

data class Location(val x: Int, val y: Int)

interface BusyCouriersQueryHandler {
    fun handle(query: BusyCouriersQuery): BusyCouriersResponse
}