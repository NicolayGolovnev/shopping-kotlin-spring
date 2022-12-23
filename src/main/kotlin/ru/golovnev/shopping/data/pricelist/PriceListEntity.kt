package ru.golovnev.shopping.data.pricelist

import org.hibernate.annotations.DynamicUpdate
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.domain.pricelist.PriceList
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"PriceList\"")
@DynamicUpdate
class PriceListEntity(
    @Id
    @Column(name = "\"PriceListId\"")
    override val id: UUID = UUID.randomUUID(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"PriceListId\"")
    override val orders: List<OrderEntity> = listOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ProductId\"")
    override var product: ProductEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ModelId\"")
    override var model: ModelEntity,

    @Column(name = "\"Price\"")
    override var price: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"")
    override var manufacturer: ManufacturerEntity
) : PriceList