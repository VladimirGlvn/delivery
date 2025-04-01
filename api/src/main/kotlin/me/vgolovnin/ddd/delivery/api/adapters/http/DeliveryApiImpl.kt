package me.vgolovnin.ddd.delivery.api.adapters.http

import dev.ceviz.Mediator
import kotlinx.coroutines.runBlocking
import me.vgolovnin.ddd.delivery.core.application.usecases.command.CreateOrderCommand
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class DeliveryApiImpl(
    private val mediator: Mediator
) : DeliveryApi {

    override fun createOrder(): ResponseEntity<Unit> = runBlocking {
        val orderId = UUID.randomUUID()
        val street = RandomStringUtils.insecure().nextPrint(20)
        mediator.send(CreateOrderCommand(orderId, street))
        ResponseEntity.created( URI.create("/orders/$orderId")).build()
    }

    override fun getCouriers(): ResponseEntity<List<Courier>> {
        TODO("Not yet implemented")
    }

    override fun getOrders(): ResponseEntity<List<Order>> {
        TODO("Not yet implemented")
    }
}