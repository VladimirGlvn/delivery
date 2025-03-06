package me.vgolovnin.ddd.delivery.core.domain.sharedkernel

import kotlin.math.abs
import kotlin.random.Random

private const val COORDINATE_MIN = 1
private const val COORDINATE_MAX = 10

data class Location(val x: Int, val y: Int) {

    init {
        require(x >= COORDINATE_MIN) { "x must be greater than or equal to $COORDINATE_MIN" }
        require(y >= COORDINATE_MIN) { "y must be greater than or equal to $COORDINATE_MIN" }
        require(x <= COORDINATE_MAX) { "x must be less than or equal to $COORDINATE_MAX" }
        require(y <= COORDINATE_MAX) { "y must be less than or equal to $COORDINATE_MAX" }
    }

    companion object {
        fun distanceBetween(first: Location, second: Location): Int = abs(first.x - second.x) + abs(first.y - second.y)

        fun random() = Location(
            Random.nextInt(COORDINATE_MIN, COORDINATE_MAX + 1),
            Random.nextInt(COORDINATE_MIN, COORDINATE_MAX + 1)
        )
    }
}

