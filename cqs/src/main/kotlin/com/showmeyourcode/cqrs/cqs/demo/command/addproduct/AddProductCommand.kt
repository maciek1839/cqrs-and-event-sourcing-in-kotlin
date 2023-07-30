package com.showmeyourcode.cqrs.cqs.demo.command.addproduct

import com.showmeyourcode.cqrs.cqs.demo.infra.Command
import java.util.*

class AddProductCommand(
    val name: String,
    val availability: Int,
) : Command<AddProductCommandResult>

class AddProductCommandResult(val id: UUID)
