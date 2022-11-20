package ru.golovnev.shopping.data.pricelist

import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.domain.pricelist.PriceList
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"PriceList\"")
class PriceListEntity(
    @Id
    @Column(name = "\"PriceListId\"")
    override val id: UUID = UUID.randomUUID(),

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PriceListId\"", updatable = false, insertable = false)
    override val orders: List<OrderEntity> = listOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ProductId\"")
    override val product: ProductEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ModelId\"")
    override val model: ModelEntity,

    @Column(name = "\"Price\"")
    override val price: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"")
    override val manufacturer: ManufacturerEntity
) : PriceList