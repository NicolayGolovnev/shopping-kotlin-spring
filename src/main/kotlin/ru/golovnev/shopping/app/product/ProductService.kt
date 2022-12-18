package ru.golovnev.shopping.app.product

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.data.product.ProductJpaRepository
import ru.golovnev.shopping.domain.product.Product
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ProductService(
    private val productJpaRepository: ProductJpaRepository
) {
    fun save(product: Product): Product =
        productJpaRepository.save(product as ProductEntity)

    fun deleteById(productId: UUID) =
        productJpaRepository.deleteById(productId)

    fun findAll(): List<Product> =
        productJpaRepository.findAll()

    fun findById(productId: UUID): Product =
        productJpaRepository.findByIdOrNull(productId)
            ?: throw EntityNotFoundException("Товар с УИД $productId не найден")
}