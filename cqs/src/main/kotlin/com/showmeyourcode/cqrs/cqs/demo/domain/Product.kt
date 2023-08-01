package com.showmeyourcode.cqrs.cqs.demo.domain

import java.util.*

data class Product(val name: String, val availability: Int, val id: UUID? = null)
