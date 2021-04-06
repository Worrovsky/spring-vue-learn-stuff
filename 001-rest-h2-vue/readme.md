<!-- MarkdownTOC autolink="true" -->

- [Backend](#backend)
    - [Выполнение кода при запуске приложения через CommandLineRunner](#%D0%92%D1%8B%D0%BF%D0%BE%D0%BB%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5-%D0%BA%D0%BE%D0%B4%D0%B0-%D0%BF%D1%80%D0%B8-%D0%B7%D0%B0%D0%BF%D1%83%D1%81%D0%BA%D0%B5-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-%D1%87%D0%B5%D1%80%D0%B5%D0%B7-commandlinerunner)
    - [Архитектура приложения](#%D0%90%D1%80%D1%85%D0%B8%D1%82%D0%B5%D0%BA%D1%82%D1%83%D1%80%D0%B0-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F)
    - [Построение REST API](#%D0%9F%D0%BE%D1%81%D1%82%D1%80%D0%BE%D0%B5%D0%BD%D0%B8%D0%B5-rest-api)
    - [ToDo](#todo)
- [Frontend](#frontend)
    - [Инициализации проекта](#%D0%98%D0%BD%D0%B8%D1%86%D0%B8%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8-%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B0)
    - [ToDo](#todo-1)

<!-- /MarkdownTOC -->

# Backend

## Выполнение кода при запуске приложения через CommandLineRunner

* Вариант 1:
    - создаем класс с аннотацией `@Configuration` (по сути нужен `@Component`)
        + можно прямо в `@SpringBootApplication`
    - в нем создаем бин, возвращающий `CommandLineRunner`
* Вариант 2:
    - создаем класс, реализующий `CommandLineRunner`
    - делаем его бином (например `@Component`)

TODO: ApplicationRunner: сравнение, те же способы
    
## Архитектура приложения

[Service Layer in Spring MVC](https://medium.com/stackavenue/why-to-use-service-layer-in-spring-mvc-5f4fc52643c0)

Разделение ответственности:

* DAO отвечает за операции с БД
* DBService использует DAO и реализует бизнес-логику

DB <- DAO/Repository <- DBService

Структура ([напр. Pet Clinic](https://github.com/arnaldop/enhanced-pet-clinic))

    model
        `-- User.java
    repository
        '-- UserRepository.java
    service
        |-- AppService.java
        `-- AppServiceImpl.java
    

## Построение REST API 

Примеры:
[spring.io](https://spring.io/guides/tutorials/rest/)
[baeldung](https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration)

Философия:
[best practice](https://habr.com/ru/post/181988/)
[REST API для сервера](https://habr.com/ru/post/144011/)

Общее 

* над контроллером общий адрес (напр. "/api/v3)
* адрес во множественном числе
  
Операции над данными

* список: GET `/products`
* одна запись: GET `/products/{id}` + `@PathVariable`
* обновление записи: POST `/products` тело через `@RequestBody`
* добавление записи: PUT `/products/{id}`  
* удаление: DELETE `/products/{id}` + `@RequestBody` и `@PathVariable`
  

Варианты возвращаемых кодов ответов:

* всегда 200, в теле - ошибки
* простой набор: 200 - все ок, 400 - ошибка на клиенте, 500 - ошибка на сервере
* расширенный 201, 302, 404, 401

Возвращаемые данные:

* GET - собственно данные
* PUT, POST - созданные/обновленные данные
* DELETE - ничего


## ToDo

Почему при создании нового нельзя установить id в БД???
Swagger посмотреть
 
# Frontend

## Инициализации проекта

Создание

    vue create frontend --no-git

Добавление vuetify

    vue add vuetify

Проверка

    npm run serve





Подключить **webpack**

    cd frontend
    npm i webpack webpack-cli -D
    touch webpack.config.js


## ToDo

Есть `vue init -h`, что-то по создание из шаблонов с git

