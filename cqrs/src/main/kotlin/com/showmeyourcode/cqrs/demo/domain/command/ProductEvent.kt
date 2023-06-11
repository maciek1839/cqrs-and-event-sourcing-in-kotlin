package com.showmeyourcode.cqrs.demo.domain.command

import com.showmeyourcode.cqrs.demo.event.Event

class ProductEvent {

    data class ProductCreated(val eventSource: Any, val newProduct: ProductCommand) : Event(eventSource)

    data class ProductAvailabilityChanged(val eventSource: Any, val newProduct: ProductCommand) : Event(eventSource)
}
