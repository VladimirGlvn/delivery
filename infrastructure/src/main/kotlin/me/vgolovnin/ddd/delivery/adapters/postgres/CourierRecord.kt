package me.vgolovnin.ddd.delivery.adapters.postgres

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "couriers")
data class CourierRecord(
    @Id
    @Column("id")
    val identifier: UUID,
    val name: String,
    val locationX: Int,
    val locationY: Int,
    val free: Boolean,
    val transportName: String,
    val transportSpeed: Int
) : Persistable<UUID> {
    override fun getId(): UUID = identifier

    @Transient
    var new: Boolean = false
    override fun isNew(): Boolean = new
}
