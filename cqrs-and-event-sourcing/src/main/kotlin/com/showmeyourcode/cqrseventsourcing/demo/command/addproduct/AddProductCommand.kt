package com.showmeyourcode.cqrseventsourcing.demo.command.addproduct

import com.showmeyourcode.cqrseventsourcing.demo.domain.Command
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class AddProductCommand(val name: String, val availability: Int) :
    Command<AddProductCommandResult>
