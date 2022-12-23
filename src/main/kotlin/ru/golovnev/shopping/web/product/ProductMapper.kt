package ru.golovnev.shopping.web.product

import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.domain.product.Product
import java.util.*

object ProductMapper {
    fun Product.toDto(): ProductDto =
        ProductDto(
            id = this.id,
            name = this.name
        )

    fun List<Product>.toDto(): List<ProductDto> =
        this.map { it.toDto() }

    fun ProductDto.toEntity(): ProductEntity =
        ProductEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать название товара"
            )
        )
}