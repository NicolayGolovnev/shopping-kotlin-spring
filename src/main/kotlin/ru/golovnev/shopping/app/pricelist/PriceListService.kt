package ru.golovnev.shopping.app.pricelist

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.app.manufacturer.ManufacturerService
import ru.golovnev.shopping.app.model.ModelService
import ru.golovnev.shopping.app.product.ProductService
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.pricelist.PriceListJpaRepository
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.domain.pricelist.PriceList
import ru.golovnev.shopping.web.pricelist.PriceListDto
import ru.golovnev.shopping.web.pricelist.PriceListMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class PriceListService(
    private val priceListJpaRepository: PriceListJpaRepository,
    private val productService: ProductService,
    private val modelService: ModelService,
    private val manufacturerService: ManufacturerService
) {
    fun save(priceListDto: PriceListDto): PriceList =
        priceListDto.id?.let {
            priceListJpaRepository.findByIdOrNull(it)?.let { priceList ->
                savePriceList(
                    priceList.apply {
                        priceListDto.product?.id?.let {
                            this.product = productService.findById(it) as ProductEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД товара")

                        priceListDto.model?.id?.let {
                            this.model = modelService.findById(it) as ModelEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД модели изготовителя")

                        priceListDto.price?.let { price -> this.price = price }

                        priceListDto.manufacturer?.id?.let {
                            this.manufacturer = manufacturerService.findById(it) as ManufacturerEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД изготовителя")
                    }
                )
            }
        } ?: savePriceList(priceListDto.toEntity())

    private fun savePriceList(priceList: PriceList): PriceList =
        priceListJpaRepository.save(priceList as PriceListEntity)

    fun deleteById(priceListId: UUID) =
        priceListJpaRepository.deleteById(priceListId)

    fun findAll(): List<PriceList> =
        priceListJpaRepository.findAll()

    fun findById(priceListId: UUID): PriceList =
        priceListJpaRepository.findByIdOrNull(priceListId)
            ?: throw EntityNotFoundException("Запись заказа с УИД $priceListId не найдена")
}