package com.showmeyourcode.cqrs.demo.command.addproduct

import com.showmeyourcode.cqrs.demo.domain.command.ProductCommand
import com.showmeyourcode.cqrs.demo.domain.command.ProductEvent
import com.showmeyourcode.cqrs.demo.event.EventPublisher
import com.showmeyourcode.cqrs.demo.infra.CommandHandler
import com.showmeyourcode.cqrs.demo.repository.command.ProductCommandRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AddProductHandler(
    private val repository: ProductCommandRepository,
    private val publisher: EventPublisher,
) : CommandHandler<AddProductCommandResult, AddProductCommand> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(command: AddProductCommand): AddProductCommandResult {
        log.info("Handling 'AddProductCommand' command...")
        val newProduct = repository.save(ProductCommand(name = command.name, availability = command.availability))

        publisher.publish(ProductEvent.ProductCreated(this, newProduct))

        return AddProductCommandResult(newProduct.id)
    }
}
