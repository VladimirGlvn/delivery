package me.vgolovnin.ddd.delivery.adapters.postgres

import com.gruelbox.transactionoutbox.DefaultInvocationSerializer
import com.gruelbox.transactionoutbox.DefaultPersistor
import com.gruelbox.transactionoutbox.Dialect
import com.gruelbox.transactionoutbox.TransactionOutbox
import com.gruelbox.transactionoutbox.spring.SpringInstantiator
import com.gruelbox.transactionoutbox.spring.SpringTransactionManager
import dev.ceviz.Mediator
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatusChangeEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(SpringTransactionManager::class, SpringInstantiator::class)
internal class OutboxConfiguration {

    @Bean
    fun transactionOutbox(
        springTransactionManager: SpringTransactionManager,
        springInstantiator: SpringInstantiator,
    ): TransactionOutbox =
        TransactionOutbox.builder()
            .instantiator(springInstantiator)
            .transactionManager(springTransactionManager)
            .persistor(
                DefaultPersistor.builder()
                    .dialect(Dialect.POSTGRESQL_9)
                    .serializer(
                        DefaultInvocationSerializer.builder()
                            .serializableTypes(setOf(OrderStatusChangeEvent::class.java)).build()
                    )
                    .build()
            )
            .build()

    @Bean
    fun outbox(transactionOutbox: TransactionOutbox, mediator: Mediator): Outbox =
        OutboxAdapter(mediator, transactionOutbox)
}