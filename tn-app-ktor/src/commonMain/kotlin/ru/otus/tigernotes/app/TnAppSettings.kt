package ru.otus.tigernotes.app

import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnCorSettings

data class TnAppSettings(
    val appUrls: List<String>,
    val corSettings: TnCorSettings,
    val processor: NoteProcessor,
)
