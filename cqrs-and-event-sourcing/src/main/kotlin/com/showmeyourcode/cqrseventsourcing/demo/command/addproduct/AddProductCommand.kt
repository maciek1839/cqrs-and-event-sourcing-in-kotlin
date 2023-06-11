package com.showmeyourcode.cqrseventsourcing.demo.command.addproduct

import com.showmeyourcode.cqrseventsourcing.demo.domain.Command
import java.util.*

class AddProductCommand(val name: String, val availability: Int) :
    Command<AddProductCommandResult>

class AddProductCommandResult(val id: UUID)
