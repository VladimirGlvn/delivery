package me.vgolovnin.ddd.delivery.core.domain.model.courier

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("Courier should")
class CourierTest {

    @Test
    fun `be free when just created`() {
        val courier = createCourier()
        courier.isFree shouldBe true
    }

    @ParameterizedTest
    @MethodSource("time calculation data")
    fun `calculate time to order location`(
        courierLocation: Location,
        targetLocation: Location,
        speed: Int,
        expectedTime: Int
    ) {
        val courier = createCourier(courierLocation, speed)

        val time = courier.timeTo(targetLocation)

        time shouldBe expectedTime
    }

    @Test
    fun `move to given location`() {
        val courier = createCourier(location = Location(1, 1), speed = 1)
        val targetLocation = Location(5, 5)
        val initialTimeToTarget = courier.timeTo(targetLocation)

        courier.moveTo(targetLocation)

        assertSoftly(courier) {
            timeTo(targetLocation) shouldBeLessThan initialTimeToTarget
            location shouldBe Location(2, 1)
        }
    }

    private fun createCourier(location: Location = Location.random(), speed: Int = 2) =
        Courier(name = "Zoran", transportName = "tram", transportSpeed = speed, location)

    companion object {
        @JvmStatic
        fun `time calculation data`(): Stream<Arguments> = Stream.of(
            Arguments.of(Location(5, 5), Location(5, 6), 1, 1),
            Arguments.of(Location(5, 5), Location(5, 7), 1, 2),
            Arguments.of(Location(5, 5), Location(7, 5), 1, 2),
            Arguments.of(Location(5, 7), Location(5, 5), 1, 2),
            Arguments.of(Location(7, 5), Location(5, 5), 1, 2),
            Arguments.of(Location(5, 5), Location(5, 5), 1, 0),
            Arguments.of(Location(5, 5), Location(7, 8), 1, 5),
            Arguments.of(Location(5, 5), Location(5, 5), 2, 0),
            Arguments.of(Location(1, 1), Location(5, 5), 2, 4),
            Arguments.of(Location(1, 1), Location(4, 5), 2, 4),
            Arguments.of(Location(1, 1), Location(5, 6), 2, 5),
        )
    }
}