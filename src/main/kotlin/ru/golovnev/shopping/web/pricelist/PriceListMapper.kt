package ru.golovnev.shopping.web.pricelist

import org.hibernate.Hibernate
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.domain.pricelist.PriceList
import ru.golovnev.shopping.web.manufacturer.ManufacturerMapper.toDto
import ru.golovnev.shopping.web.manufacturer.ManufacturerMapper.toEntity
import ru.golovnev.shopping.web.model.ModelMapper.toDto
import ru.golovnev.shopping.web.model.ModelMapper.toEntity
import ru.golovnev.shopping.web.product.ProductDto
import ru.golovnev.shopping.web.product.ProductMapper.toDto
import ru.golovnev.shopping.web.product.ProductMapper.toEntity
import java.util.*

object PriceListMapper {
    fun PriceList.toDto(): PriceListDto =
        PriceListDto(
            id = this.id,
            product = this.product.takeIf { Hibernate.isInitialized(it) } ?.toDto(),
            model = this.model.takeIf { Hibernate.isInitialized(it) } ?.toDto(),
            price = this.price,
            manufacturer = this.manufacturer.takeIf { Hibernate.isInitialized(it) } ?.toDto()
        )

    fun List<PriceList>.toDto(): List<PriceListDto> =
        this.map { it.toDto() }

    fun PriceListDto.toEntity(): PriceListEntity =
        PriceListEntity(
            id = this.id ?: UUID.randomUUID(),
            product = this.product?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать один из товаров"
                ),
            model = this.model?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать одну из моделей изготовителя"
                ),
            price = this.price ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать цену товара"
            ),
            manufacturer = this.manufacturer?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать одно из изготовителей"
                )
        )
}