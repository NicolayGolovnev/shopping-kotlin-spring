package ru.golovnev.shopping.web.model

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.golovnev.shopping.app.model.ModelService
import ru.golovnev.shopping.web.model.ModelMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/model")
class ModelController(
    private val modelService: ModelService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") modelId: UUID) =
        modelService.findById(modelId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<ModelDto> =
        modelService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        modelService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody modelDto: ModelDto): ModelDto =
        modelService.save(modelDto).toDto()
}