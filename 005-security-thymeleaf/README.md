WIP

https://github.com/eugenp/learn-spring-security/tree/module2

## 1. DevTools

[habr](https://habr.com/ru/post/479382/)

Добавление зависимости:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>

Добавлена как опциональная, т. е. при добавлении проекта в другие, эта зависимость не будет добавлена.

Основные возможности:

* автоматический перезапуск приложения при изменениях в classpath
    - выполняется быстрее чем обычный перезапуск

Триггер изменений в classpath зависит от IDE:

* Eclipce: при сохранении
* Idea Ultimate: Run/Edit configuration... поставить флаги для Spring Boot
* Idea CE: Build Project (Ctrl + F9) или Recompile (Ctrl + Shift + F9) 

## 2. Thymeleaf и валидация формы

[кратко о Thymeleaf](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html)

[пример Baeldung](https://www.baeldung.com/spring-thymeleaf-error-messages)
[пример Getting Started](https://spring.io/guides/gs/validating-form-input/)

### 2.1 Параметр BindingResult

**BindingResult** - параметр метода контроллера. Через него можно получать ошибки, внедренные другими компонентами (например валидацией) или добавлять свои.

Два типа ошибок: связанные с объектом **ObjectError** и с полем объекта **FieldError**.

    @PostMapping("/person")
    public ModelAndView postPerson(Person person, BindingResult result) {
        FieldError fieldError = new FieldError("person", "age"
                , "programmatically added error for 'Age' of 'Person'");
        result.addError(fieldError);
        ...
    }

### 2.2 Валидация через аннотации

Один из способов проверки - валидация через аннотации `javax.validation` (зависимость `spring-boot-starter-validation`) - **@Valid** и **@Min**, **@NotEmpty** и др.

Важно: порядок параметров метода контроллера важен: объект с аннотацией **@Valid**, следующим обязательно **BindingResult**. Иначе - исключение. Сделано так для возможности валидации нескольких объектов, на каждый объект - свой `BindingResult`

Для аннотаций над полями можно задавать сообщения. Если не заданы, будут показаны сообщения по умолчанию. Есть I18n.

### 2.3 Создание формы в Thymeleaf

Форма обычно связывается с каким-либо объектом

    <form th:object="${person}" method="post">

Этот объект передается в параметры метода контроллера или в возвращаемом значении из контроллера. Обращать внимание на соответствие имен объекта в методах и шаблоне.

    @GetMapping("/person")
    public ModelAndView getPerson(Person person) {
        return new ModelAndView("person", "person", person);
    }

Имея объект, можем получить доступ к его полям:

    <input type="text" id="date" name="date" th:value="*{date}"/>

Или сокращенная запись с помощью **th:field** - из интеграции Thymeleaf + Spring. [Подробнее тут](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html#creating-a-form). Обычно включает в себя атрибуты `id`, `value`, `name`. Но зависит от типа поля ввода.
    
    <input type="text" th:field="*{date}"/>

### 2.4 Отражение ошибок в Thymeleaf

Объект **BindingResult** передается шаблонизатору. В макете к нему есть доступ через объект **fields**. Это класс **Fields** из пакета `org.thymeleaf.spring5` (интеграция Thymeleaf + Spring). Имеет доступ к ошибкам глобальным или ошибкам поля, добавленным в **BindingResult**.

Почему-то не работает с методом GET. Добавляем ошибку программно в BindingResult в контроллере. Возвращаем ModelAndView, но в шаблоне не видно ошибок. Точно такой же код для POST работает.

    th:if="${#fields.hasErrors('name')}"

Другой атрибут **th:error** - просто выводит список ошибок по полю. Вот примерные аналоги

    <li th:each="err : ${#fields.errors('date')}" th:text="${err}" />
    <p th:if="${#fields.hasErrors('date')}" th:errors="*{date}">Wrong date</p>

[Документация Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html#validation-and-error-messages)






## 3. H2 Console и Spring Security

Настройка консоли (файл `application.properties`):

    spring.h2.console.enabled=true
    spring.h2.console.path=/h2

При наличии DevTools разрешена по умолчанию, url `h2-console`

Можно настроить желаемые координаты БД:

    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.username=sa
    spring.datasource.password=


Для совместной работы с Spring Security:

* зависимость H2 подключить с scope=compile (по умолчанию так и будет)
* разрешить доступ к url консоли
* отключить csrf
* отключить ограничение на работу с фреймами (консоль использует фреймы) [docs про фреймы](https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/appendix-namespace.html#nsa-frame-options)

Образец конфигурации:

    http
          .authorizeRequests()
            .mvcMatchers("/h2/**").permitAll();
    http.csrf().disable();
    http.headers().frameOptions().disable();


## 4. Registration flow

Настройка страницы логина:

* свой адрес задать `http.formLogin().loginPage("/login").permitAll()`
* можно указать свой адрес обработки `http.formLogin().loginProcessingUrl("/doLogin")`. В форме такой же адрес указать.
* контроллер создать для этого адреса, возвращающий страницу-шаблон
* настроить страницу-шаблон:
    - обычно форма с методом POST `<form id="userForm" th:action="@{/doLogin}" method="post">`
    - параметры формы настроить. По умолчанию ожидает `username` и `password`. Можно настроить свой `http.formLogin().usernameParameter("my-username")` и в шаблоне `<input id="username" name="my-username"..>`
    - доступ к ошибкам `th:if="${param.error}"`


Подключение **UserDetailsService**:

* создать класс, реализующий **UserDetailsService**
* метод интерфейса должен возвращать объект **userdetails.User** или ичключение **UserNotFoundException**. Поиск можно выполнять в БД например.
* класс подключаем через конфигурацию `auth.userDetailsService(userDetailsService)`

Использование **PasswordEncoder**

* регистрируем как бин
* используем в двух местах:
    - при создании новых пользователей для сохранения в БД хеша пароля
    - передаем в настройку `auth.passwordEncoder(passwordEncoder())`