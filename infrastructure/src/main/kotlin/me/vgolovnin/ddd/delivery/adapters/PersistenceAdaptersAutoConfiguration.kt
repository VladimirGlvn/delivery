package me.vgolovnin.ddd.delivery.adapters

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@AutoConfiguration
@ComponentScan(basePackages = ["me.vgolovnin.ddd.delivery.adapters.postgres"])
@EnableJdbcRepositories(basePackages = ["me.vgolovnin.ddd.delivery.adapters.postgres"])
internal class PersistenceAdaptersAutoConfiguration