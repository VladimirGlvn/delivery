package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("orders")
internal data class OrderRecord(
    @Id
    @Column("id")
    val identifier: UUID,
    val locationX: Int,
    val locationY: Int,
    val status: OrderStatus,
    val courierId: UUID?,
) : Persistable<UUID> {
    override fun getId(): UUID = identifier

    @Transient var new: Boolean = false
    override fun isNew(): Boolean = new
}
