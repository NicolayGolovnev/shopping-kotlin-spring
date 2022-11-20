package ru.golovnev.shopping.data.model

import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.domain.model.Model
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Model\"")
class ModelEntity(
    @Id
    @Column(name = "\"ModelId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override val name: String,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ModelId\"", updatable = false, insertable = false)
    override val priceList: List<PriceListEntity> = listOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"")
    override val manufacturer: ManufacturerEntity
) : Model