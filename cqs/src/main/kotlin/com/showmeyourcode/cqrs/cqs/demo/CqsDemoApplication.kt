package com.showmeyourcode.cqrs.cqs.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CqsDemoApplication

fun main(args: Array<String>) {
    runApplication<CqsDemoApplication>(*args)
}
