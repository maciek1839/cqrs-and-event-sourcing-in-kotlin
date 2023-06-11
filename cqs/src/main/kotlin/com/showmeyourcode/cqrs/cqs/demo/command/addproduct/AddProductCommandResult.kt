package com.showmeyourcode.cqrs.cqs.demo.command.addproduct

import lombok.Getter
import lombok.Setter
import java.util.*

@Getter
@Setter
class AddProductCommandResult(val id: UUID)
