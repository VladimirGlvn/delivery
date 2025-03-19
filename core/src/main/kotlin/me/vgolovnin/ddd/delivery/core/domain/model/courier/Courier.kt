package me.vgolovnin.ddd.delivery.core.domain.model.courier

import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location.Companion.distanceBetween
import java.util.*
import kotlin.math.ceil

class Courier(
    val name: String,
    transportName: String,
    transportSpeed: Int,
    location: Location,
) {
    val id: UUID = UUID.randomUUID()
    var isFree: Boolean = true
    private val transport = Transport(transportName, transportSpeed)

    var location: Location = location
        private set

    fun timeTo(target: Location) = ceil(distanceBetween(location, target).toDouble() / transport.speed).toInt()

    fun moveTo(target: Location) {
        location = with(transport) { go(location to target) }
    }
}