package com.showmeyourcode.cqrs.cqs.demo.command.addproduct

import com.showmeyourcode.cqrs.cqs.demo.domain.Product
import com.showmeyourcode.cqrs.cqs.demo.infra.CommandHandler
import com.showmeyourcode.cqrs.cqs.demo.repository.InMemoryProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AddProductHandler(
    private val repository: InMemoryProductRepository,
) : CommandHandler<AddProductCommandResult, AddProductCommand> {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun handle(command: AddProductCommand): AddProductCommandResult {
        log.info("Handling 'AddProductCommand' command...")
        return AddProductCommandResult(repository.addProduct(Product(command.name, command.availability)))
    }
}
