package me.vgolovnin.ddd.delivery.api.adapters.http

import dev.ceviz.Mediator
import kotlinx.coroutines.runBlocking
import me.vgolovnin.ddd.delivery.core.application.usecases.command.CreateOrderCommand
import me.vgolovnin.ddd.delivery.core.application.usecases.query.AllCouriersQuery
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
        ResponseEntity.created(URI.create("/orders/$orderId")).build()
    }

    override fun getCouriers(): ResponseEntity<List<Courier>> = runBlocking {
        val couriers = mediator.send(AllCouriersQuery).couriers
        ResponseEntity.ok(couriers.map { Courier(it.id, it.name, Location(it.location.x, it.location.y)) })
    }

    override fun getOrders(): ResponseEntity<List<Order>> {
        TODO("Not yet implemented")
    }
}