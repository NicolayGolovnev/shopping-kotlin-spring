package ru.golovnev.shopping.web.client

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.golovnev.shopping.app.client.ClientService
import ru.golovnev.shopping.web.client.ClientMapper.toDto
import ru.golovnev.shopping.web.client.ClientMapper.toEntity
import java.util.*

@RestController
@RequestMapping("/api/client")
class ClientController(
    private val clientService: ClientService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") clientId: UUID) =
        clientService.findById(clientId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<ClientDto> =
        clientService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        clientService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody clientDto: ClientDto): ClientDto =
        clientService.save(clientDto).toDto()
}