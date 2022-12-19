package ru.golovnev.shopping.factory

import io.mockk.every
import io.mockk.mockk
import ru.golovnev.shopping.data.client.ClientEntity
import java.util.*

object ClientMockFactory {
    fun createClient(
        name: String = "Test",
        telephone: Long? = 89997776655,
        relaxed: Boolean = true
    ): ClientEntity =
        mockk(relaxed = relaxed) client@{
            every { this@client.id } returns UUID.randomUUID()
            every { this@client.name } returns name
            every { this@client.telephone } returns telephone
            every { this@client.orders } returns listOf()
        }
}