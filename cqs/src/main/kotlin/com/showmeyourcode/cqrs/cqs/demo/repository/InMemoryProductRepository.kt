package com.showmeyourcode.cqrs.cqs.demo.repository

import com.showmeyourcode.cqrs.cqs.demo.domain.Product
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryProductRepository {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val products: MutableMap<UUID, Product> = ConcurrentHashMap<UUID, Product>()

    @PostConstruct
    private fun init() {
        log.info("Initializing an in-memory database...")
        products[UUID.fromString("11111111-1111-1111-1111-111111111111")] =
            Product(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "ExampleInMemoryProductName1",
                1000,
            )
        products[UUID.fromString("22222222-2222-2222-2222-222222222222")] =
            Product(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "ExampleInMemoryProductName2",
                1000,
            )
    }

    fun addProduct(product: Product): UUID {
        val id = UUID.randomUUID()
        log.info("A new UUID generated: $id")
        products[id] = Product(id, product.name, product.availability)
        return id
    }

    fun updateAvailability(id: UUID, newAvailability: Int) {
        val p = products[id]
        if (p?.id != null) {
            products[id] = Product(p.id, p.name, p.availability)
        }
    }

    fun getProduct(productId: UUID): Product? {
        return products[productId]
    }

    fun getAll(): MutableCollection<Product> {
        return products.values
    }
}
