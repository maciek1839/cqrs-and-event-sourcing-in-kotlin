package com.showmeyourcode.cqrs.cqs.demo.command.changeavailability

import com.showmeyourcode.cqrs.cqs.demo.infra.Command
import java.util.*

class ChangeProductAvailabilityCommand(
    val id: UUID,
    val newAvailability: Int,
) : Command<Unit>
