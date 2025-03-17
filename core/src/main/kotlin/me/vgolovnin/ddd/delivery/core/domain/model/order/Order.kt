package me.vgolovnin.ddd.delivery.core.domain.model.order

import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus.*
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import java.util.*

class Order(
    val id: UUID,
    val location: Location,
) {
    var status: OrderStatus = CREATED
        private set

    var courierId: UUID? = null
        private set

    fun assignTo(courier: Courier) {
        check(status != COMPLETED) { "Completed order cannot be assigned" }

        courierId = courier.id
        status = ASSIGNED
    }

    fun complete() {
        check(status == ASSIGNED || status == COMPLETED) { "Order cannot be completed if it is not assigned" }
        status = COMPLETED
    }

}