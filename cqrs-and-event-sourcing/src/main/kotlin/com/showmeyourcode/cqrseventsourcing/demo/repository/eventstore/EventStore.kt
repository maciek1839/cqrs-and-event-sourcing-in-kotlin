package com.showmeyourcode.cqrseventsourcing.demo.repository.eventstore

import com.showmeyourcode.cqrseventsourcing.demo.domain.ProductID
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.Event

interface EventStore {
    fun save(event: Event)

    fun saveAll(events: List<Event>) {
        events.forEach(this::save)
    }

    fun allFor(productNumber: ProductID): List<Event>

    fun exists(productNumber: ProductID): Boolean

    fun deleteAll()
}
