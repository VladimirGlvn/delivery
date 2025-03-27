package me.vgolovnin.ddd.delivery.core.ports

import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import java.util.*

interface CourierRepository {
    fun add(courier: Courier)
    fun update(courier: Courier)
    fun findById(id: UUID): Courier?
    fun findAllFree(): Collection<Courier>
}