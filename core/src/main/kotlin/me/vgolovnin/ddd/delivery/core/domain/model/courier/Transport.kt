package me.vgolovnin.ddd.delivery.core.domain.model.courier

import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import java.util.*
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign

class Transport(
    val name: String,
    val speed: Int,
    private val movementStrategy: MovementStrategy = MovementStrategy.Default,
    val id: UUID = UUID.randomUUID(),
) {

    companion object {
        private val allowedSpeedRange = 1..3
    }

    init {
        require(name.isNotBlank()) { "Name must not be blank" }
        require(speed in allowedSpeedRange) { "Speed must be in range $allowedSpeedRange" }
    }

    infix fun go(route: Pair<Location, Location>) = with(movementStrategy) { route.moveWith(speed) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transport

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    interface MovementStrategy {

        infix fun Pair<Location, Location>.moveWith(step: Int): Location

        object Default : MovementStrategy {

            override infix fun Pair<Location, Location>.moveWith(step: Int): Location {
                val x = calcReached(first.x, second.x, step)
                val y = calcReached(first.y, second.y, step - abs(first.x - x))
                return Location(x, y)
            }

            private fun calcReached(from: Int, to: Int, step: Int) =
                (to - from).let { from + min(step, abs(it)) * it.sign }
        }
    }

}