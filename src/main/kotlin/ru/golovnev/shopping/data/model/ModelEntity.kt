package ru.golovnev.shopping.data.model

import org.hibernate.annotations.DynamicUpdate
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.domain.model.Model
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Model\"")
@DynamicUpdate
class ModelEntity(
    @Id
    @Column(name = "\"ModelId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override var name: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ModelId\"")
    override val priceList: List<PriceListEntity> = listOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"")
    override var manufacturer: ManufacturerEntity
) : Model