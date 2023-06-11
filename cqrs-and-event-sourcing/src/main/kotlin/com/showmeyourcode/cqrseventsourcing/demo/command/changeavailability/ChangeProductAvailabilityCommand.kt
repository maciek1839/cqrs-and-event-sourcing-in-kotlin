package com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability

import com.showmeyourcode.cqrseventsourcing.demo.domain.Command
import java.util.*

class ChangeProductAvailabilityCommand(val id: UUID, val newAvailability: Int) :
    Command<Unit>
