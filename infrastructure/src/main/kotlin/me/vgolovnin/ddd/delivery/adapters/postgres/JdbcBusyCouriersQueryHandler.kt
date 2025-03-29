package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.application.usecases.*

internal class JdbcBusyCouriersQueryHandler(
    private val courierRepository: JdbcCourierRepository
) : BusyCouriersQueryHandler {

    override fun handle(query: BusyCouriersQuery): BusyCouriersResponse =
        courierRepository.findAllByFree(false).map(::toView)
            .let { BusyCouriersResponse(it) }

    private fun toView(courierRecord: CourierRecord): Courier = with(courierRecord) {
        Courier(id, name, Location(locationX, locationY), transportName)
    }
}