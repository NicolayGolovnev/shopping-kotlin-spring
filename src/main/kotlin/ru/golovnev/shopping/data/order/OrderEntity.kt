package ru.golovnev.shopping.data.order

import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.domain.order.Order
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Order\"")
class OrderEntity(
    @Id
    @Column(name = "\"OrderId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Date\"")
    override val date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PriceListId\"")
    override val priceList: PriceListEntity,

    @Column(name = "\"Count\"")
    override val count: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ClientId\"")
    override val client: ClientEntity
) : Order