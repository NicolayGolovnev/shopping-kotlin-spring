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
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override var name: String,

    @Column(name = "\"Country\"")
    override var country: String,

    @Column(name = "\"Site\"")
    override var site: String? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ManufacturerId\"")
    override val priceList: List<PriceListEntity> = listOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ManufacturerId\"")
    override val models: List<ModelEntity> = listOf()
) : Manufacturer