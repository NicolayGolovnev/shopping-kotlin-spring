package ru.golovnev.shopping.data.order

import org.hibernate.annotations.DynamicUpdate
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.domain.order.Order
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Order\"")
@DynamicUpdate
class OrderEntity(
    @Id
    @Column(name = "\"OrderId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Date\"")
    override var date: LocalDate = LocalDate.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PriceListId\"")
    override var priceList: PriceListEntity,

    @Column(name = "\"Count\"")
    override var count: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ClientId\"")
    override var client: ClientEntity
) : Order