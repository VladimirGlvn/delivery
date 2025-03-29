package me.vgolovnin.ddd.delivery.core.utils

interface UnitOfWork {
    operator fun invoke(work: () -> Unit)
}
