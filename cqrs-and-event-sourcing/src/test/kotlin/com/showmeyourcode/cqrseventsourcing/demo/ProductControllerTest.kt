package com.showmeyourcode.cqrseventsourcing.demo

import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductCommand
import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductCommandResult
import com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability.ChangeProductAvailabilityCommand
import com.showmeyourcode.cqrseventsourcing.demo.controller.ProductController
import com.showmeyourcode.cqrseventsourcing.demo.domain.query.ProductQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityQueryResult
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsQueryResult
import com.showmeyourcode.cqrseventsourcing.demo.repository.eventstore.EventStore
import com.showmeyourcode.cqrseventsourcing.demo.repository.query.ProductQueryRepository
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
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
@AutoConfigureDataJpa
@ComponentScan(basePackages = ["com.showmeyourcode.cqrseventsourcing.demo"])
class ProductControllerTest(
    @Autowired private val webClient: WebTestClient,
    @Autowired private val eventStore: EventStore,
    @Autowired private val queryRepository: ProductQueryRepository,
) {

    @AfterEach
    fun cleanup() {
        eventStore.deleteAll()
        queryRepository.deleteAll()
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
        val product = ProductQuery(UUID.randomUUID(), "Example Product", 1234567)
        queryRepository.save(product)

        val changeAvailability = ChangeProductAvailabilityCommand(
            product.id,
            100,
        )
        webClient.post()
            .uri(ProductController.CHANGE_PRODUCT_AVAILABILITY_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(changeAvailability))
            .exchange()
            .expectStatus().isOk

        // todo: assert that the availability is changed
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
        val product = ProductQuery(UUID.randomUUID(), "Example Product", 1234567)
        queryRepository.save(product)

        val getAvailability = GetProductAvailabilityQuery(product.id)
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
        val product = ProductQuery(UUID.randomUUID(), "Example Product", 1234567)
        queryRepository.save(product)

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
