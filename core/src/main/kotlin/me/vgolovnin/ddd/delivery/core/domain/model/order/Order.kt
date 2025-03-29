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
    status: OrderStatus = CREATED,
    courierId: UUID? = null,
) {
    var status: OrderStatus = status
        private set

    var courierId: UUID? = courierId
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    object Faults {
        object AlreadyAssigned : Fault
        object NotAssignedYet : Fault
    }
}