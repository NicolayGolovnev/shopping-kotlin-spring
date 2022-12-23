package ru.golovnev.shopping.data.product

import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.domain.product.Product
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Product\"")
class ProductEntity(
    @Id
    @Column(name = "\"ProductId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override var name: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ProductId\"")
    override val priceList: List<PriceListEntity> = listOf()
) : Product