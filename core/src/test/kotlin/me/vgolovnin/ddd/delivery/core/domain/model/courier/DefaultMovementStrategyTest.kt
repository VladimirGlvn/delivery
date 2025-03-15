package me.vgolovnin.ddd.delivery.core.domain.model.courier

import io.kotest.matchers.shouldBe
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Transport.MovementStrategy.Default.moveWith
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class DefaultMovementStrategyTest {

    @ParameterizedTest
    @MethodSource("routes")
    fun `move to the target x by given step`(initial: Location, target: Location, step: Int, expected: Location) {
        initial to target moveWith step shouldBe expected
    }

    companion object {
        @JvmStatic
        fun routes(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(Location(5, 3), Location(5, 3), 9, Location(5, 3)),
                // Move right
                Arguments.of(Location(3, 3), Location(6, 3), 3, Location(6, 3)),
                Arguments.of(Location(3, 3), Location(6, 3), 2, Location(5, 3)),
                Arguments.of(Location(3, 3), Location(4, 3), 2, Location(4, 3)),
                // Move left
                Arguments.of(Location(6, 3), Location(3, 3), 3, Location(3, 3)),
                Arguments.of(Location(6, 3), Location(3, 3), 2, Location(4, 3)),
                Arguments.of(Location(4, 3), Location(3, 3), 2, Location(3, 3)),
                //Move down
                Arguments.of(Location(2, 5), Location(2, 7), 2, Location(2, 7)),
                Arguments.of(Location(2, 5), Location(2, 7), 1, Location(2, 6)),
                Arguments.of(Location(2, 5), Location(2, 7), 3, Location(2, 7)),
                //Move up
                Arguments.of(Location(2, 5), Location(2, 3), 2, Location(2, 3)),
                Arguments.of(Location(2, 5), Location(2, 3), 1, Location(2, 4)),
                Arguments.of(Location(2, 5), Location(2, 3), 3, Location(2, 3)),
                //Move right down
                Arguments.of(Location(2, 2), Location(4, 5), 5, Location(4, 5)),
                Arguments.of(Location(2, 2), Location(4, 5), 4, Location(4, 4)),
                Arguments.of(Location(2, 2), Location(5, 5), 2, Location(4, 2)),
                Arguments.of(Location(2, 2), Location(4, 5), 6, Location(4, 5)),
                //Move right up
                Arguments.of(Location(2, 6), Location(3, 4), 3, Location(3, 4)),
                //Move left up
                Arguments.of(Location(4, 6), Location(2, 4), 3, Location(2, 5)),
                //Move left down
                Arguments.of(Location(4, 6), Location(3, 7), 4, Location(3, 7)),
            )
        }
    }
}