package me.vgolovnin.ddd.delivery.api.adapters.job

import dev.ceviz.Mediator
import kotlinx.coroutines.runBlocking
import me.vgolovnin.ddd.delivery.core.application.usecases.command.UpdateCourierLocationsCommand
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MoveCouriersJob(
    private val mediator: Mediator
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedRate = 2000)
    fun execute() = runBlocking {
        log.debug("Moving couriers")
        mediator.send(UpdateCourierLocationsCommand)
    }
}