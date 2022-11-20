package ru.golovnev.shopping.data.client

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface ClientJpaRepository : JpaRepository<ClientEntity, UUID> {
    @Query(
        nativeQuery = true,
        value = "SELECT * FROM public.\"Client\""
    )
    fun nativeFindAll(): List<ClientEntity>

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM public.\"Client\" c WHERE c.\"ClientId\" = :id"
    )
    fun nativeFindById(@Param(value = "id") id: UUID): ClientEntity

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        nativeQuery = true,
        value = "INSERT INTO public.\"Client\" (\"ClientId\", \"Name\", \"Telephone\") " +
                "VALUES (:clientId, :clientName, :clientTelephone)"
    )
    fun nativeSave(
        @Param(value = "clientId") id: UUID,
        @Param(value = "clientName") name: String,
        @Param(value = "clientTelephone") telephone: Long?
    )

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        nativeQuery = true,
        value = "UPDATE public.\"Client\" SET \"Name\" = :clientName, \"Telephone\" = :clientTelephone " +
                "WHERE \"ClientId\" = :clientId"
    )
    fun nativeUpdate(
        @Param(value = "clientId") id: UUID,
        @Param(value = "clientName") name: String,
        @Param(value = "clientTelephone") telephone: Long?
    )

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        nativeQuery = true,
        value = "DELETE FROM public.\"Client\" WHERE \"ClientId\" = :id"
    )
    fun nativeDeleteById(@Param(value = "id") id: UUID)
}