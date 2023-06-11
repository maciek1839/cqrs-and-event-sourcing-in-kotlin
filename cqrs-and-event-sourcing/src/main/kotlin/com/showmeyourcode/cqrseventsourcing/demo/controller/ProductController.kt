package com.showmeyourcode.cqrseventsourcing.demo.controller

import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductCommand
import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductCommandResult
import com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability.ChangeProductAvailabilityCommand
import com.showmeyourcode.cqrseventsourcing.demo.domain.CommandHandlerProvider
import com.showmeyourcode.cqrseventsourcing.demo.domain.QueryHandlerProvider
import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityQueryResult
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsQueryResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@RestController
class ProductController(
    private val commandHandler: CommandHandlerProvider,
    private val queryHandlerProvider: QueryHandlerProvider
) {

    @PostMapping("/addProduct")
    fun addProduct(@RequestBody command: AddProductCommand): Mono<AddProductCommandResult> {
        // TODO: consider returning ID of a new product
        commandHandler.handleAddProduct(command)
        return Mono.empty()
    }

    @PostMapping("/changeProductAvailability")
    fun buyAdditionalCover(@RequestBody command: ChangeProductAvailabilityCommand): Mono<ServerResponse> {
        commandHandler.changeProductAvailability(command)
        return ServerResponse.noContent().build()
    }

    @PostMapping("/getProductAvailability")
    fun buyAdditionalCover(@RequestBody query: GetProductAvailabilityQuery): Mono<GetProductAvailabilityQueryResult> {
        return Mono.just(queryHandlerProvider.getProductAvailability(query))
    }

    @PostMapping("/getProducts")
    fun buyAdditionalCover(@RequestBody query: GetProductsQuery): Mono<GetProductsQueryResult> {
        return Mono.just(queryHandlerProvider.getProducts(query))
    }
}
