package ru.golovnev.shopping.app.manufacturer

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerJpaRepository
import ru.golovnev.shopping.domain.manufacturer.Manufacturer
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ManufacturerService(
    private val manufacturerJpaRepository: ManufacturerJpaRepository
) {
    fun save(manufacturer: Manufacturer): Manufacturer =
        manufacturerJpaRepository.save(manufacturer as ManufacturerEntity)

    fun deleteById(manufacturerId: UUID) =
        manufacturerJpaRepository.deleteById(manufacturerId)

    fun findAll(): List<Manufacturer> =
        manufacturerJpaRepository.findAll()

    fun findById(manufacturerId: UUID): Manufacturer =
        manufacturerJpaRepository.findByIdOrNull(manufacturerId)
            ?: throw EntityNotFoundException("Изготовитель с УИД $manufacturerId не найден")
}