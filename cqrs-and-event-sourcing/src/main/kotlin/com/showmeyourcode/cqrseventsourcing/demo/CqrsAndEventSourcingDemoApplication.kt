package com.showmeyourcode.cqrseventsourcing.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CqrsAndEventSourcingDemoApplication

fun main(args: Array<String>) {
    runApplication<CqrsAndEventSourcingDemoApplication>(*args)
}
