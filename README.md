# Учебный проект курса [Kotlin Backend Developer](https://otus.ru/lessons/kotlin/?int_source=courses_catalog&int_term=programming): Сервис по работе с заметками
Поток курса 2023-02.

## Краткое описание
TigerNotes - это сервис по работе с заметками. Позволяет создавать, выбирать, искать заметки. Возможность добавлять дату и почту для отправления напоминания.

## Визуальная схема фронтенда

![Макет фронта](imgs/design-layout.png)

## Документация

1. Маркетинг
    1. [Заинтересанты](./docs/01-marketing/02-stakeholders.md)
    2. [Целевая аудитория](./docs/01-marketing/01-target-audience.md)
    3. [Конкурентный анализ](./docs/01-marketing/03-concurrency.md)
    4. [Анализ экономики](./docs/01-marketing/04-economy.md)
    5. [Пользовательские истории](./docs/01-marketing/05-user-stories.md)
2. DevOps
    1. [Схема инфраструктуры](./docs/02-devops/01-infrastruture.md)
    2. [Схема мониторинга](./docs/02-devops/02-monitoring.md)
3. Тесты
   1. [User Story 1: Создание заметки](./docs/03-testing/user-story-0001.md)
   2. [User Story 2: Создание заметки с напоминанием](./docs/03-testing/user-story-0002.md)
4. Архитектура
   1. [Архитектурная диаграмма](./docs/04-architecture/01-arch.md)
   2. [Схема интеграции](./docs/04-architecture/02-integration.md)
   3. [API](./docs/04-architecture/03-api.md)

# Структура проекта

## Транспортные модели, API
1. [Описание API в форме OpenAPI-спецификаций](./specs/specs-note.yaml)
2. [Генерация транспортных моделей с KMP](./tn-api-kmp)
3. [Модуль с общими классами для модулей проекта. В частности, там располагаются внутренние модели и контекст](./tn-common)
4. [Мапер между внутренними моделями и моделями API](./tn-mappers)

## Фреймворки и транспорты
1. [Приложение на Ktor Native](./tn-app-ktor)
2. [Микросервис на Kafka](./tn-app-kafka)

## Модули бизнес-логики
1. [Модуль бизнес-логики приложения](./tn-app-biz)
2. [Стабы для ответов сервиса](./tn-stubs)