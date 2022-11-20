package ru.golovnev.shopping.data.manufacturer

import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.domain.manufacturer.Manufacturer
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Manufacturer\"")
class ManufacturerEntity(
    @Id
    @Column(name = "\"ManufacturerId\"")
    override val id: UUID,

    @Column(name = "\"Name\"")
    override val name: String,

    @Column(name = "\"Country\"")
    override val country: String,

    @Column(name = "\"Site\"")
    override val site: String?,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"", updatable = false, insertable = false)
    override val priceList: List<PriceListEntity> = listOf(),

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"", updatable = false, insertable = false)
    override val model: List<ModelEntity> = listOf()
) : Manufacturer