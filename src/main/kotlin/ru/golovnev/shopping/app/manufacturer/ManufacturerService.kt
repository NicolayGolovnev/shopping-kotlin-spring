package ru.golovnev.shopping.app.manufacturer

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerJpaRepository
import ru.golovnev.shopping.domain.manufacturer.Manufacturer
import ru.golovnev.shopping.web.manufacturer.ManufacturerDto
import ru.golovnev.shopping.web.manufacturer.ManufacturerMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ManufacturerService(
    private val manufacturerJpaRepository: ManufacturerJpaRepository
) {
    fun save(manufacturerDto: ManufacturerDto): Manufacturer =
        manufacturerDto.id?.let {
            manufacturerJpaRepository.findByIdOrNull(it)?.let { manufacturer ->
                saveManufacturer(
                    manufacturer.apply {
                        manufacturerDto.name?.let { name -> this.name = name }
                        manufacturerDto.country?.let { country -> this.country = country }
                        manufacturerDto.site?.let { site -> this.site = site }
                    }
                )
            }
        } ?: saveManufacturer(manufacturerDto.toEntity())

    private fun saveManufacturer(manufacturer: Manufacturer): Manufacturer =
        manufacturerJpaRepository.save(manufacturer as ManufacturerEntity)

    fun deleteById(manufacturerId: UUID) =
        manufacturerJpaRepository.deleteById(manufacturerId)

    fun findAll(): List<Manufacturer> =
        manufacturerJpaRepository.findAll()

    fun findById(manufacturerId: UUID): Manufacturer =
        manufacturerJpaRepository.findByIdOrNull(manufacturerId)
            ?: throw EntityNotFoundException("Изготовитель с УИД $manufacturerId не найден")
}