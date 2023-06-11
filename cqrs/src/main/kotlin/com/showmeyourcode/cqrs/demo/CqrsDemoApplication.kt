package com.showmeyourcode.cqrs.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableJpaRepositories("com.showmeyourcode.cqrs.demo.repository.query")
@EntityScan("com.showmeyourcode.cqrs.demo.domain.query")
@EnableMongoRepositories("com.showmeyourcode.cqrs.demo.repository.command")
open class CqrsDemoApplication

fun main(args: Array<String>) {
    runApplication<CqrsDemoApplication>(*args)
}
