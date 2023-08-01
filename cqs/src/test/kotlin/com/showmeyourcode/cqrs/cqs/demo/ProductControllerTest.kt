package com.showmeyourcode.cqrs.cqs.demo

import com.showmeyourcode.cqrs.cqs.demo.command.addproduct.AddProductCommand
import com.showmeyourcode.cqrs.cqs.demo.command.addproduct.AddProductCommandResult
import com.showmeyourcode.cqrs.cqs.demo.command.changeavailability.ChangeProductAvailabilityCommand
import com.showmeyourcode.cqrs.cqs.demo.controller.ProductController
import com.showmeyourcode.cqrs.cqs.demo.domain.Product
import com.showmeyourcode.cqrs.cqs.demo.query.getproductavailability.GetProductAvailabilityQuery
import com.showmeyourcode.cqrs.cqs.demo.query.getproductavailability.GetProductAvailabilityQueryResult
import com.showmeyourcode.cqrs.cqs.demo.query.getproducts.GetProductsQuery
import com.showmeyourcode.cqrs.cqs.demo.query.getproducts.GetProductsQueryResult
import com.showmeyourcode.cqrs.cqs.demo.repository.InMemoryProductRepository
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.util.*

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@WebFluxTest
@ComponentScan(basePackages = ["com.showmeyourcode.cqrs.cqs"])
class ProductControllerTest(
    @Autowired private val webClient: WebTestClient,
    @Autowired private val repository: InMemoryProductRepository,
) {

    @AfterEach
    fun cleanup() {
        repository.deleteAll()
    }

    @Test
    fun shouldPerformAddProductCommand() {
        val addProductCmd = AddProductCommand("ExampleProduct", 100)
        webClient.post()
            .uri(ProductController.ADD_PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(addProductCmd))
            .exchange()
            .expectStatus().isOk
            .expectBody(AddProductCommandResult::class.java)
            .value {
                it.id.shouldNotBeNull()
            }
    }

    @Test
    fun shouldPerformChangeProductAvailabilityCommandWhenProductIsFound() {
        val product = Product("Example Product", 1)
        val productId = repository.addProduct(product)

        val changeAvailability = ChangeProductAvailabilityCommand(
            productId,
            100,
        )
        webClient.post()
            .uri(ProductController.CHANGE_PRODUCT_AVAILABILITY_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(changeAvailability))
            .exchange()
            .expectStatus().isOk

        repository.getProduct(productId)?.availability shouldBe changeAvailability.newAvailability
    }

    @Test
    fun shouldPerformChangeProductAvailabilityCommandWhenProductIsNotFound() {
        val changeAvailability = ChangeProductAvailabilityCommand(
            UUID.fromString("d76e796b-d809-4adf-abbe-34734eecf8d4"),
            100,
        )
        webClient.post()
            .uri(ProductController.CHANGE_PRODUCT_AVAILABILITY_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(changeAvailability))
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun shouldPerformGetProductAvailabilityQueryWhenProductIsFound() {
        val product = Product("Example Product", 1665432)
        val productId = repository.addProduct(product)

        val getAvailability = GetProductAvailabilityQuery(productId)
        webClient.post()
            .uri(ProductController.GET_PRODUCT_AVAILABILITY_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(getAvailability))
            .exchange()
            .expectStatus().isOk
            .expectBody(GetProductAvailabilityQueryResult::class.java)
            .value {
                it.availability shouldBe product.availability
            }
    }

    @Test
    fun shouldPerformGetProductAvailabilityQueryWhenProductIsNotFound() {
        val getAvailability = GetProductAvailabilityQuery(UUID.fromString("11b0673f-e1d6-4dea-8525-ce2e45946fab"))
        webClient.post()
            .uri(ProductController.GET_PRODUCT_AVAILABILITY_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(getAvailability))
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun shouldPerformGetProductsQueryWhenProductsAreAvailable() {
        val product = Product("Example Product", 1665432)
        repository.addProduct(product)

        val getProducts = GetProductsQuery(10)

        webClient.post()
            .uri(ProductController.GET_PRODUCTS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(getProducts))
            .exchange()
            .expectStatus().isOk
            .expectBody(GetProductsQueryResult::class.java)
            .value {
                it.products.size shouldBe 1
            }
    }

    @Test
    fun shouldPerformGetProductsQueryWhenProductsAreNotAvailable() {
        val getProducts = GetProductsQuery()

        webClient.post()
            .uri(ProductController.GET_PRODUCTS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(getProducts))
            .exchange()
            .expectStatus().isOk
    }
}
