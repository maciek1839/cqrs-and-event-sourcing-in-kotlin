package com.showmeyourcode.cqrseventsourcing.demo.command.addproduct

import com.showmeyourcode.cqrseventsourcing.demo.domain.CommandHandler
import com.showmeyourcode.cqrseventsourcing.demo.domain.Product
import com.showmeyourcode.cqrseventsourcing.demo.infra.eventbus.EventBus
import com.showmeyourcode.cqrseventsourcing.demo.repository.eventstore.EventStore
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AddProductHandler(
    private val eventStore: EventStore,
    private val eventBus: EventBus
) : CommandHandler<AddProductCommandResult, AddProductCommand> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(command: AddProductCommand) {
        log.info("Handling 'AddProductCommand' command...")
        val product = Product()
        product.handle(command)
        val events = product.occurredEvents()
        eventBus.sendAll(events)
        eventStore.saveAll(events)
    }
}
