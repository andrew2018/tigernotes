package ru.otus.tigernotes.mappers.exceptions

import ru.otus.tigernotes.common.models.TnCommand

class UnknownTnCommand(command: TnCommand) : Throwable("Wrong command $command at mapping toTransport stage")
