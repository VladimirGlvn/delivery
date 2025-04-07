package me.vgolovnin.ddd.delivery.adapters.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaOperations

@AutoConfiguration
internal class KafkaMessageBusAutoConfiguration {

    @Bean
    fun messageBus(kafkaOperations: KafkaOperations<String, String>, jsonMapper: ObjectMapper) =
        KafkaMessageBus(kafkaOperations, jsonMapper)

}