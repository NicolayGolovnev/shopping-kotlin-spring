package ru.golovnev.shopping.domain.client

import java.util.*

/** Интерфейс репозитория для работы с покупателями */
interface ClientRepository {
    fun findAll(): List<Client>

    fun findById(id: UUID): Client

    fun save(client: Client): Unit

    fun update(client: Client): Unit

    fun deleteById(id: UUID): Unit
}