<!-- MarkdownTOC autolink="true" -->

- [Выполнение кода при запуске приложения через CommandLineRunner](#%D0%92%D1%8B%D0%BF%D0%BE%D0%BB%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5-%D0%BA%D0%BE%D0%B4%D0%B0-%D0%BF%D1%80%D0%B8-%D0%B7%D0%B0%D0%BF%D1%83%D1%81%D0%BA%D0%B5-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-%D1%87%D0%B5%D1%80%D0%B5%D0%B7-commandlinerunner)
- [Архитектура приложения](#%D0%90%D1%80%D1%85%D0%B8%D1%82%D0%B5%D0%BA%D1%82%D1%83%D1%80%D0%B0-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F)
- [Построение REST API](#%D0%9F%D0%BE%D1%81%D1%82%D1%80%D0%BE%D0%B5%D0%BD%D0%B8%D0%B5-rest-api)
- [ToDo](#todo)

<!-- /MarkdownTOC -->


## Выполнение кода при запуске приложения через CommandLineRunner

* Создаем класс с аннотацией `@Configuration` (по сути нужен `@Component`)
* В нем создаем бин, возвращающий `CommandLineRunner`

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
 


