package me.vgolovnin.ddd.delivery.core.domain.model.courier

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

const val ALLOWED_SPEED_MIN = 1
const val ALLOWED_SPEED_MAX = 3

@DisplayName("Transport should")
class TransportTest {

    private val allowedSpeedRange = ALLOWED_SPEED_MIN..ALLOWED_SPEED_MAX

    @Test
    fun `allow creation with non empty name`() {
        assertDoesNotThrow { Transport("tram", allowedSpeedRange.random()) }
    }

    @Test
    fun `decline creation when name is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            Transport("", allowedSpeedRange.random())
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [ALLOWED_SPEED_MIN - 1, ALLOWED_SPEED_MAX + 1])
    fun `not allow speed less than 1 and greater than 3`(speed: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            Transport("tram", speed)
        }
    }

    @Test
    fun `move according to the movement strategy`() {
        val movementPoints = listOf(Location(7, 8), Location(9, 10), Location(5, 2))

        val movementStrategy = object : Transport.MovementStrategy {
            val movementRoute = movementPoints.iterator()
            override fun Pair<Location, Location>.moveWith(step: Int) = movementRoute.next()
        }
        val transport = Transport("tram", allowedSpeedRange.random(), movementStrategy)

        with(transport) {
            go(Location(1, 1) to Location(2, 2)) shouldBe movementPoints[0]
            go(Location(2, 2) to Location(3, 3)) shouldBe movementPoints[1]
            go(Location(3, 3) to Location(4, 4)) shouldBe movementPoints[2]
        }
    }

    @Test
    fun `be equal to transport having the same id`() {
        val transport1 = Transport(name = "any name", speed = allowedSpeedRange.random())
        transport1 shouldBe Transport(name = "other name", speed = 1, id = transport1.id)
    }

    @Test
    fun `not be equal to transport having another id`() {
        val transport1 = Transport(name = "any name", speed = allowedSpeedRange.random())
        transport1 shouldNotBe Transport(name = transport1.name, speed = transport1.speed)
    }
}
