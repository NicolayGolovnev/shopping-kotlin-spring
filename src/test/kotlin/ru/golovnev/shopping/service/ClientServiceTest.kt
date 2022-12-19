package ru.golovnev.shopping.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import ru.golovnev.shopping.app.client.ClientService
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.data.client.ClientJpaRepository
import ru.golovnev.shopping.domain.client.Client
import ru.golovnev.shopping.factory.ClientMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ClientServiceTest {
    @InjectMockKs
    lateinit var clientService: ClientService

    @MockK
    lateinit var clientJpaRepository: ClientJpaRepository

    @Test
    fun `find by id`() {
        val client = ClientMockFactory.createClient(name = "Bob")
        val id: UUID = client.id

        every { clientJpaRepository.findByIdOrNull(id) } returns client

        assertDoesNotThrow {
            val findingClient = clientService.findById(id)
            assertEquals(client.name, findingClient.name)
        }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { clientJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { clientService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val clientEntity: Client = ClientEntity(name = "Pop", telephone = null)
        every { clientJpaRepository.save(clientEntity as ClientEntity) } returns ClientMockFactory.createClient(
            name = clientEntity.name, telephone = clientEntity.telephone
        )

        assertDoesNotThrow {
            val client = clientService.save(clientEntity)
            assertEquals(clientEntity.name, client.name)
        }
    }

    @Test
    fun `find all`() {
        every { clientJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { clientService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { clientService.deleteById(id) } returns Unit

        assertDoesNotThrow { clientService.deleteById(id) }
    }
}