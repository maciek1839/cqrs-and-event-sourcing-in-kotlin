package com.showmeyourcode.cqrs.cqs.demo.command.addproduct

import com.showmeyourcode.cqrs.cqs.demo.infra.Command
import lombok.Getter
import lombok.Setter

@Getter
@Setter
class AddProductCommand(val name: String, val availability: Int) : Command<AddProductCommandResult>
