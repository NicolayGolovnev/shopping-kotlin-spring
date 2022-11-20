package ru.golovnev.shopping.data.client

import com.fasterxml.jackson.annotation.JsonIgnore
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.domain.client.Client
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Client\"")
class ClientEntity(
    @Id
    @Column(name = "\"ClientId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override val name: String,

    @Column(name = "\"Telephone\"")
    override val telephone: Long,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ClientId\"", updatable = false, insertable = false)
    @JsonIgnore
    override val orders: List<OrderEntity> = listOf()
) : Client
