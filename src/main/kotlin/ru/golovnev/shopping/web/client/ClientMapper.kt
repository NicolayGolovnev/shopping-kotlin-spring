package ru.golovnev.shopping.web.client

import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.domain.client.Client
import java.util.*

object ClientMapper {
    fun Client.toDto(): ClientDto =
        ClientDto(
            id = this.id,
            name = this.name,
            telephone = this.telephone
        )

    fun List<Client>.toDto(): List<ClientDto> =
        this.map { it.toDto() }

    fun ClientDto.toEntity(): ClientEntity =
        ClientEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать ФИО клиента"
            ),
            telephone = this.telephone
        )
}