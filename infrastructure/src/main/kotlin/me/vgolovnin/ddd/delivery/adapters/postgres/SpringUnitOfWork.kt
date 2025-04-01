package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork
import org.springframework.transaction.support.TransactionOperations

internal class SpringUnitOfWork(
    private val transactionOperations: TransactionOperations,
) : UnitOfWork {

    override fun invoke(work: () -> Unit) {
        transactionOperations.executeWithoutResult {
            work()
        }
    }
}