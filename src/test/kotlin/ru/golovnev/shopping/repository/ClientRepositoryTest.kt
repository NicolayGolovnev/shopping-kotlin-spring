package ru.golovnev.shopping.repository

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.data.client.ClientJpaRepository
import ru.golovnev.shopping.data.client.ClientRepositoryImpl
import ru.golovnev.shopping.domain.client.Client
import ru.golovnev.shopping.factory.ClientMockFactory
import ru.golovnev.shopping.web.client.ClientDto
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ClientRepositoryTest {
    @Autowired
    lateinit var clientRepositoryImpl: ClientRepositoryImpl
    @Autowired
    lateinit var clientJpaRepository: ClientJpaRepository

    private lateinit var firstClient: ClientEntity
    private lateinit var secondClient: ClientEntity

    @BeforeEach
    private fun setup() {
        firstClient = ClientMockFactory.createClient(
            name = "FirstClientTets",
            telephone = 89991112233,
            relaxed = true
        )
        secondClient = ClientMockFactory.createClient(
            name = "SecondClientTets",
            telephone = 89992223344,
            relaxed = true
        )
    }

    @Test
    fun `save full entity test`() {
        clientRepositoryImpl.save(firstClient)
        var savedClient: ClientEntity? = null

        assertDoesNotThrow { savedClient = clientJpaRepository.findById(firstClient.id).orElseThrow() }
        assertEquals(firstClient.id, savedClient?.id)
        assertEquals(firstClient.name, savedClient?.name)
        assertEquals(firstClient.telephone, savedClient?.telephone)
    }

    /**
     * Данный тест не совсем корректный, так как в Котлине присутствует null safety, то есть
     * еще на этапе приведения к типу сущности мы получим ошибку - до сохранения мы не дойдем
     *
     * Все последующие тесты на проверки сущности с путыми полями не имеет смысла - эти поля
     * точно должны быть инициализированы на слое бизнес-логики (app) - сервиса
     */
    @Test
    fun `throw when save empty client dto`() {
        var clientDto = mockk<ClientDto>(relaxed = true) dto@{
            every { this@dto.id } returns null
            every { this@dto.name } returns null
            every { this@dto.telephone } returns null
        }

        assertThrows<TypeCastException> {
            clientRepositoryImpl.save(clientDto as? ClientEntity ?: throw TypeCastException())
        }
    }

    @Test
    fun `delete by Id`() {
        clientJpaRepository.save(firstClient)

        assertDoesNotThrow { clientRepositoryImpl.deleteById(firstClient.id) }
        val clientsInDatabase = 0
        val clients: List<ClientEntity> = clientJpaRepository.findAll()
        assertEquals(clients.size, clientsInDatabase)
    }

    @Test
    fun `throw when delete by empty id`() {
        val id: UUID? = null

        assertThrows<Exception> { clientRepositoryImpl.deleteById(id ?: throw Exception()) }
    }

    @Test
    fun `find all entity`() {
        clientJpaRepository.deleteAll()
        clientJpaRepository.save(firstClient)
        clientJpaRepository.save(secondClient)
        val jpaClients: List<ClientEntity> = clientJpaRepository.findAll()
        var checkClients: List<Client>? = null

        assertDoesNotThrow { checkClients = clientRepositoryImpl.findAll() }
        checkClients?.let {
            // Ожидаем, что списки приходят одинаковые
            for ((index, checkClient) in it.withIndex()) {
                assertEquals(jpaClients[index].id, checkClient.id)
                assertEquals(jpaClients[index].name, checkClient.name)
                assertEquals(jpaClients[index].telephone, checkClient.telephone)
            }
        }
    }

    @Test
    fun `find by id`() {
        val id: UUID = firstClient.id
        clientJpaRepository.save(firstClient)
        var findClient: Client? = null

        assertDoesNotThrow { findClient = clientRepositoryImpl.findById(id) }
        assertEquals(findClient?.id, firstClient.id)
        assertEquals(findClient?.name, firstClient.name)
        assertEquals(findClient?.telephone, firstClient.telephone)
    }

    @Test
    fun `throw when find by empty id`() {
        val id: UUID? = null

        assertThrows<Exception> { clientRepositoryImpl.findById(id ?: throw Exception()) }
    }

    @Test
    @Transactional
    fun `update entity`() {
        clientJpaRepository.save(firstClient)
        val changedName: String = "ChangedNameTest"
        every { firstClient.name } returns changedName

        assertDoesNotThrow { clientRepositoryImpl.update(firstClient) }
        var changedClient: Client? = null
        assertDoesNotThrow { changedClient = clientJpaRepository.findById(firstClient.id).get() }
        assertEquals(changedClient?.id!!, firstClient.id)
        assertEquals(changedClient?.name!!, firstClient.name)
        assertEquals(changedClient?.telephone!!, firstClient.telephone)
    }
}