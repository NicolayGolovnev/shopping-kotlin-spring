package ru.golovnev.shopping.data.client

import org.springframework.stereotype.Repository
import ru.golovnev.shopping.domain.client.Client
import ru.golovnev.shopping.domain.client.ClientRepository
import java.util.*

@Repository
class ClientRepositoryImpl(
    private val clientJpaRepository: ClientJpaRepository
) : ClientRepository {
    override fun findAll(): List<Client> =
        clientJpaRepository.nativeFindAll()

    override fun findById(id: UUID): Client =
        clientJpaRepository.nativeFindById(id)

    override fun save(client: Client) {
        clientJpaRepository.nativeSave(
            id = client.id,
            name = client.name,
            telephone = client.telephone
        )
    }

    override fun update(client: Client) {
        clientJpaRepository.nativeUpdate(
            id = client.id,
            name = client.name,
            telephone = client.telephone
        )
    }

    override fun deleteById(id: UUID) =
        clientJpaRepository.nativeDeleteById(id)
}