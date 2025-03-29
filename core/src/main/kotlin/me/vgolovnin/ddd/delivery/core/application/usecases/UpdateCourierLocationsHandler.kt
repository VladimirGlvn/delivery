package me.vgolovnin.ddd.delivery.core.application.usecases

import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork

class UpdateCourierLocationsHandler(
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository,
    private val unitOfWork: UnitOfWork,
) {
    fun handle(@Suppress("UNUSED_PARAMETER") command: UpdateCourierLocationsCommand) {
        orderRepository.findAllAssigned().forEach { order ->
            val courier = courierRepository.findById(order.courierId!!)
            checkNotNull(courier) { "Courier assigned to order must exist" }
            courier.moveTo(order.location)

            unitOfWork {
                if (courier.location == order.location) {
                    order.complete()
                    courier.isFree = true

                    orderRepository.update(order)
                }
                courierRepository.update(courier)
            }
        }
    }
}