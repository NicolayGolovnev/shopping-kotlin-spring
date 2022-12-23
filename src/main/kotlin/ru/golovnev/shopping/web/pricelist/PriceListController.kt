package ru.golovnev.shopping.web.pricelist

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.golovnev.shopping.app.pricelist.PriceListService
import ru.golovnev.shopping.web.pricelist.PriceListMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/pricelist")
class PriceListController(
    private val priceListService: PriceListService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") priceListId: UUID) =
        priceListService.findById(priceListId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<PriceListDto> =
        priceListService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        priceListService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody priceListDto: PriceListDto): PriceListDto =
        priceListService.save(priceListDto).toDto()
}