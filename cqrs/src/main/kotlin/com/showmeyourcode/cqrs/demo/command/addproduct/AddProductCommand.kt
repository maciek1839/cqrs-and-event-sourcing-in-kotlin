package com.showmeyourcode.cqrs.demo.command.addproduct

import com.showmeyourcode.cqrs.demo.infra.Command
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class AddProductCommand(val name: String, val availability: Int) : Command<AddProductCommandResult>
