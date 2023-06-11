package com.showmeyourcode.cqrs.cqs.demo.command.changeavailability

import com.showmeyourcode.cqrs.cqs.demo.infra.CommandHandler
import com.showmeyourcode.cqrs.cqs.demo.repository.InMemoryProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ChangeProductAvailabilityHandler(private val repository: InMemoryProductRepository) : CommandHandler<Unit, ChangeProductAvailabilityCommand> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(command: ChangeProductAvailabilityCommand) {
        log.info("Handling 'ChangeProductAvailabilityHandler' command...")
        repository.updateAvailability(command.id, command.newAvailability)
    }
}
