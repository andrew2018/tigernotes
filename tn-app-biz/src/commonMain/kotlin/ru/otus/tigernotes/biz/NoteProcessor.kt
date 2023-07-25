package ru.otus.tigernotes.biz

import com.crowdproj.kotlin.cor.rootChain
import ru.otus.tigernotes.biz.general.initRepo
import ru.otus.tigernotes.biz.operations.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.TnCommand

class NoteProcessor(val settings: TnCorSettings) {
    suspend fun exec(ctx: TnContext) = BusinessChain.exec(ctx.apply { this.settings = this@NoteProcessor.settings })

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")
            createOperation("Создание заявки", TnCommand.CREATE)
            readOperation("Получить заявку", TnCommand.READ)
            updateOperation("Изменить заявку", TnCommand.UPDATE)
            deleteOperation("Удалить заявку", TnCommand.DELETE)
            searchOperation("Поиск заявок", TnCommand.SEARCH)
        }.build()
    }
}
