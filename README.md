# RBPO Lab 3 - Система управления выпуском версий ПО

## Что это такое

REST API для управления жизненным циклом программного продукта.
Можно создавать пользователей, продукты, задачи разработки, выпускать релизы и проводить проверки.

## Технологии

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA (Hibernate)
- PostgreSQL 16 (в Docker)
- Maven

## Как запустить

1. Запустить PostgreSQL в Docker:

docker run --name lab2-postgres -e POSTGRES_USER=lab2user -e POSTGRES_PASSWORD=lab2pass -e POSTGRES_DB=lab2db -p 5432:5432 -d postgres:16

2. Запустить приложение:

mvn clean spring-boot:run

3. Проверить что работает:

curl http://localhost:8080/api/users

## Сущности

- User - пользователь системы (разработчик, тестировщик, менеджер)
- SoftwareProduct - программный продукт (например, MyApp)
- DevelopmentTask - задача разработки (сделать фичу, исправить баг)
- Release - версия продукта (1.0.0, 2.0.0)
- ReleaseCheck - проверка релиза (сборка, тесты, код-ревью)

### Связи между сущностями

- У продукта есть ответственный (User) и текущая версия (Release)
- У задачи есть продукт и исполнитель (User)
- У релиза есть продукт
- У проверки есть релиз

## CRUD операции

Для каждой сущности есть 5 операций:
- GET /api/users - список всех
- GET /api/users/{id} - получить по id
- POST /api/users - создать
- PATCH /api/users/{id} - обновить
- DELETE /api/users/{id} - удалить

Аналогично для продуктов, задач, релизов и проверок.

## Бизнес-операции

Операции которые затрагивают сразу 2 сущности и меняют их состояние.

### 1. Назначить исполнителя на задачу (Task + User)

POST /api/business/task/assign?taskId=1&userId=2

Меняет статус задачи на IN_PROGRESS и назначает пользователя.
Нельзя назначить на завершённую задачу.

### 2. Выпустить релиз (Release + ReleaseCheck)

POST /api/business/release/publish?productId=1&version=1.0.0

Создаёт релиз и автоматически создаёт проверку сборки.
Работает только если все задачи продукта завершены (статус DONE).
Обновляет текущую версию продукта.

### 3. Переназначить ответственного (Product + User)

POST /api/business/product/reassign?productId=1&userId=2

Меняет ответственного за продукт.

### 4. Провести все проверки релиза (Release + ReleaseCheck)

POST /api/business/release/check-all?releaseId=1

Проверяет все mandatory проверки. Если все PASSED - меняет статус релиза на APPROVED.

### 5. Закрыть продукт (Product + Tasks)

POST /api/business/product/close?productId=1

Закрывает продукт (добавляет [CLOSED] к имени).
Работает только если все задачи завершены.

## Тестирование

### Postman

В репозитории есть файл RBPO-lab3.postman_collection.json.
Импортировать в Postman и запустить через Runner.
Все запросы выполняются по порядку, id сохраняются в переменные коллекции.
GET и PATCH запросы работают сразу.
Для POST запросов нужно вручную добавить заголовок Content-Type: application/json в каждом запросе.

### curl

Все запросы работают через curl:

curl http://localhost:8080/api/users

curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"username":"test","email":"test@test.com"}'

curl -X POST "http://localhost:8080/api/business/task/assign?taskId=1&userId=2"

curl -X POST "http://localhost:8080/api/business/release/publish?productId=1&version=1.0.0"

curl -X POST "http://localhost:8080/api/business/product/reassign?productId=1&userId=2"

curl -X POST "http://localhost:8080/api/business/release/check-all?releaseId=1"

curl -X POST "http://localhost:8080/api/business/product/close?productId=1"

## Структура проекта

src/main/java/com/example/lab2/
  controller/ - REST контроллеры (принимают HTTP запросы)
  service/ - бизнес-логика (сервисы + бизнес-операции)
  repository/ - JPA репозитории (работа с БД)
  entity/ - сущности (таблицы БД)
  dto/ - объекты для передачи данных

## База данных

Используется PostgreSQL в Docker.
Таблицы создаются автоматически через Hibernate (ddl-auto: update).
Тестовые данные загружаются из data.sql при запуске.

Пользователь БД: lab2user (ограниченные права, только работа с таблицами lab2db).
