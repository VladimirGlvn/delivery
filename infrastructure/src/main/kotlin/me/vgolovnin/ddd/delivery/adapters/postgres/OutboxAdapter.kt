package me.vgolovnin.ddd.delivery.adapters.postgres

import com.gruelbox.transactionoutbox.TransactionOutbox
import dev.ceviz.Mediator
import kotlinx.coroutines.runBlocking
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatusChangeEvent

internal open class OutboxAdapter(
    private val mediator: Mediator,
    private val outbox: TransactionOutbox,
) : Outbox {

    override fun send(message: OrderStatusChangeEvent)   {
        outbox.schedule(this::class.java).sendInternal(message)
    }

    protected open fun sendInternal(message: OrderStatusChangeEvent) = runBlocking {
        mediator.send(message)
    }
}