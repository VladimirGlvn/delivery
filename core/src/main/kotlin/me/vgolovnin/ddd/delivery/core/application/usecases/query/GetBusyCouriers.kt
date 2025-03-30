package me.vgolovnin.ddd.delivery.core.application.usecases.query

import java.util.*

object BusyCouriersQuery

data class BusyCouriersResponse(val couriers: List<Courier>)

data class Courier(val id: UUID, val name: String, val location: Location, val transport: String)

interface BusyCouriersQueryHandler {
    fun handle(query: BusyCouriersQuery): BusyCouriersResponse
}