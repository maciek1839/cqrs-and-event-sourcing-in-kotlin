package com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability

import com.showmeyourcode.cqrseventsourcing.demo.domain.CommandHandler
import com.showmeyourcode.cqrseventsourcing.demo.domain.Product
import com.showmeyourcode.cqrseventsourcing.demo.infra.eventbus.EventBus
import com.showmeyourcode.cqrseventsourcing.demo.repository.eventstore.EventStore
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ChangeProductAvailabilityHandler(
    private val eventStore: EventStore,
    private val eventBus: EventBus,
) :
    CommandHandler<Unit, ChangeProductAvailabilityCommand> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(command: ChangeProductAvailabilityCommand) {
        log.info("Handling 'ChangeProductAvailabilityHandler' command...")
        if (eventStore.exists(command.id)) {
            val product = Product().applyAll(eventStore.allFor(command.id))
            product.handle(command)
            val events = product.occurredEvents()
            eventBus.sendAll(events)
            eventStore.saveAll(events)
        } else {
            log.info("The operation is ignored as product doesn't exist. [productNumber={}]", command.id)
        }
    }
}
