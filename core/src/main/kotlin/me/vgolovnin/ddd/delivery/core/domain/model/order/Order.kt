package me.vgolovnin.ddd.delivery.core.domain.model.order

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus.*
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Fault
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

    fun assignTo(courier: Courier): Either<Fault, Unit> = either {
        ensure(status == CREATED) { Faults.AlreadyAssigned }

        courierId = courier.id
        status = ASSIGNED
    }

    fun complete(): Either<Fault, Unit> = either {
        ensure(status == ASSIGNED || status == COMPLETED) { Faults.NotAssignedYet }
        status = COMPLETED
    }

    object Faults {
        object AlreadyAssigned : Fault
        object NotAssignedYet : Fault
    }
}