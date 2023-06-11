package com.showmeyourcode.cqrseventsourcing.demo.domain

import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductCommand
import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductHandler
import com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability.ChangeProductAvailabilityCommand
import com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability.ChangeProductAvailabilityHandler
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CommandHandlerProvider constructor(private val applicationContext: ApplicationContext) {

    fun handleAddProduct(cmd: AddProductCommand) {
        return applicationContext.getBean(AddProductHandler::class.java).handle(cmd)
    }

    fun changeProductAvailability(cmd: ChangeProductAvailabilityCommand) {
        return applicationContext.getBean(ChangeProductAvailabilityHandler::class.java).handle(cmd)
    }
}
