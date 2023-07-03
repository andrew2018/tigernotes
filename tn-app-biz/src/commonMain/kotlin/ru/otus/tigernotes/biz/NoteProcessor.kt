package ru.otus.tigernotes.biz

import com.crowdproj.kotlin.cor.rootChain
import ru.otus.tigernotes.biz.groups.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.TnCommand

class NoteProcessor(val settings: TnCorSettings) {
    suspend fun exec(ctx: TnContext) = BusinessChain.exec(ctx.apply { this.settings = this@NoteProcessor.settings })

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Инициализация статуса")
            createOperation("Создание заявки", TnCommand.CREATE)
            readOperation("Получить заявку", TnCommand.READ)
            updateOperation("Изменить заявку", TnCommand.UPDATE)
            deleteOperation("Удалить заявку", TnCommand.DELETE)
            searchOperation("Поиск заявок", TnCommand.SEARCH)
        }.build()
    }
}
