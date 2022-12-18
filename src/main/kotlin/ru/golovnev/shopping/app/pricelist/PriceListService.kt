package ru.golovnev.shopping.app.pricelist

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.pricelist.PriceListJpaRepository
import ru.golovnev.shopping.domain.pricelist.PriceList
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class PriceListService(
    private val priceListJpaRepository: PriceListJpaRepository
) {
    fun save(priceList: PriceList): PriceList =
        priceListJpaRepository.save(priceList as PriceListEntity)

    fun deleteById(priceListId: UUID) =
        priceListJpaRepository.deleteById(priceListId)

    fun findAll(): List<PriceList> =
        priceListJpaRepository.findAll()

    fun findById(priceListId: UUID): PriceList =
        priceListJpaRepository.findByIdOrNull(priceListId)
            ?: throw EntityNotFoundException("Запись заказа с УИД $priceListId не найдена")
}