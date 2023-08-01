package com.showmeyourcode.cqrs.cqs.demo.controller

import com.showmeyourcode.cqrs.cqs.demo.command.addproduct.AddProductCommand
import com.showmeyourcode.cqrs.cqs.demo.command.addproduct.AddProductCommandResult
import com.showmeyourcode.cqrs.cqs.demo.command.changeavailability.ChangeProductAvailabilityCommand
import com.showmeyourcode.cqrs.cqs.demo.infra.CommandHandlerProvider
import com.showmeyourcode.cqrs.cqs.demo.infra.QueryHandlerProvider
import com.showmeyourcode.cqrs.cqs.demo.query.getproductavailability.GetProductAvailabilityQuery
import com.showmeyourcode.cqrs.cqs.demo.query.getproductavailability.GetProductAvailabilityQueryResult
import com.showmeyourcode.cqrs.cqs.demo.query.getproducts.GetProductsQuery
import com.showmeyourcode.cqrs.cqs.demo.query.getproducts.GetProductsQueryResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@RestController
class ProductController(
    private val commandHandler: CommandHandlerProvider,
    private val queryHandlerProvider: QueryHandlerProvider,
) {

    companion object {
        const val ADD_PRODUCT_PATH = "/addProduct"
        const val CHANGE_PRODUCT_AVAILABILITY_PATH = "/changeProductAvailability"
        const val GET_PRODUCT_AVAILABILITY_PATH = "/getProductAvailability"
        const val GET_PRODUCTS_PATH = "/getProducts"
    }

    @PostMapping(ADD_PRODUCT_PATH)
    fun addProduct(@RequestBody command: AddProductCommand): Mono<AddProductCommandResult> {
        return Mono.just(commandHandler.handleAddProduct(command))
    }

    @PostMapping(CHANGE_PRODUCT_AVAILABILITY_PATH)
    fun changeProductAvailability(@RequestBody command: ChangeProductAvailabilityCommand): Mono<ServerResponse> {
        commandHandler.changeProductAvailability(command)
        return ServerResponse.noContent().build()
    }

    @PostMapping(GET_PRODUCT_AVAILABILITY_PATH)
    fun getProductAvailability(@RequestBody query: GetProductAvailabilityQuery): Mono<GetProductAvailabilityQueryResult> {
        return Mono.just(queryHandlerProvider.getProductAvailability(query))
    }

    @PostMapping(GET_PRODUCTS_PATH)
    fun getProducts(@RequestBody query: GetProductsQuery): Mono<GetProductsQueryResult> {
        return Mono.just(queryHandlerProvider.getProducts(query))
    }
}
