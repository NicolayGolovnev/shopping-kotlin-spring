package ru.golovnev.shopping.web.manufacturer

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.golovnev.shopping.app.manufacturer.ManufacturerService
import ru.golovnev.shopping.web.manufacturer.ManufacturerMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/manufacturer")
class ManufacturerController(
    private val manufacturerService: ManufacturerService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") manufacturerId: UUID) =
        manufacturerService.findById(manufacturerId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<ManufacturerDto> =
        manufacturerService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        manufacturerService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody manufacturerDto: ManufacturerDto): ManufacturerDto =
        manufacturerService.save(manufacturerDto).toDto()
}