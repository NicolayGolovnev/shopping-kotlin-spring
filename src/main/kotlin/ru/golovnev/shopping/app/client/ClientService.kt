package ru.golovnev.shopping.app.client

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.data.client.ClientJpaRepository
import ru.golovnev.shopping.domain.client.Client
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ClientService(
    private val clientJpaRepository: ClientJpaRepository
) {
    fun save(client: Client): Client =
        clientJpaRepository.save(client as ClientEntity)

    fun deleteById(clientId: UUID) =
        clientJpaRepository.deleteById(clientId)

    fun findById(clientId: UUID): Client =
        clientJpaRepository.findByIdOrNull(clientId)
            ?: throw EntityNotFoundException("Пользователь с УИД $clientId не найден")

    fun findAll(): List<Client> =
        clientJpaRepository.findAll()
}