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
    override var name: String,

    @Column(name = "\"Telephone\"")
    override var telephone: Long? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ClientId\"")
    @JsonIgnore
    override val orders: List<OrderEntity> = listOf()
) : Client
