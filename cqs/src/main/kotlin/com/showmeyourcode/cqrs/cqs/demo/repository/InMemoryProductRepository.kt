package com.showmeyourcode.cqrs.cqs.demo.repository

import com.showmeyourcode.cqrs.cqs.demo.domain.Product
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryProductRepository {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val products: MutableMap<UUID, Product> = ConcurrentHashMap<UUID, Product>()

    @PostConstruct
    private fun init() {
        log.info("Initializing an in-memory database...")
        val id1 = UUID.fromString("11111111-1111-1111-1111-111111111111")
        products[id1] =
            Product(
                "ExampleInMemoryProductName1",
                1000,
                id1,
            )
        val id2 = UUID.fromString("22222222-2222-2222-2222-222222222222")
        products[id2] =
            Product(
                "ExampleInMemoryProductName2",
                1000,
                id2,
            )
    }

    fun addProduct(product: Product): UUID {
        val id = UUID.randomUUID()
        log.info("A new UUID generated: $id")
        products[id] = Product(product.name, product.availability, id)
        return id
    }

    fun updateAvailability(id: UUID, newAvailability: Int) {
        val p = products[id]
        if (p != null) {
            products[id] = Product(p.name, newAvailability, p.id)
        }
    }

    fun getProduct(productId: UUID): Product? {
        return products[productId]
    }

    fun getAll(): MutableCollection<Product> {
        return products.values
    }

    fun deleteAll() {
        products.clear()
    }
}
