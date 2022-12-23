package ru.golovnev.shopping.web.product

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.golovnev.shopping.app.product.ProductService
import ru.golovnev.shopping.web.product.ProductMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") productId: UUID) =
        productService.findById(productId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<ProductDto> =
        productService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        productService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody productDto: ProductDto): ProductDto =
        productService.save(productDto).toDto()
}