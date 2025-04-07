package me.vgolovnin.ddd.delivery.api.adapters.job

import dev.ceviz.Mediator
import kotlinx.coroutines.runBlocking
import me.vgolovnin.ddd.delivery.core.application.usecases.command.AssignOrderCommand
import me.vgolovnin.ddd.delivery.core.application.usecases.command.NoSuitableCouriersException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AssignOrdersJob(
    private val mediator: Mediator
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedRate = 1000)
    fun execute() = runBlocking {
        try {
            log.debug("Assigning orders")
            mediator.send(AssignOrderCommand)
        } catch (e: NoSuitableCouriersException) {
            log.warn("No suitable couriers were found to assign orders to.")
        }
    }
}