<!-- MarkdownTOC autolink="true" uri_encoding="false" -->

- [1. DevTools](#1-devtools)
- [2. Thymeleaf и валидация формы](#2-thymeleaf-и-валидация-формы)
    - [2.1 Параметр BindingResult](#21-Параметр-bindingresult)
    - [2.2 Валидация через аннотации](#22-Валидация-через-аннотации)
    - [2.3 Создание формы в Thymeleaf](#23-Создание-формы-в-thymeleaf)
    - [2.4 Отражение ошибок в Thymeleaf](#24-Отражение-ошибок-в-thymeleaf)
- [3. H2 Console и Spring Security](#3-h2-console-и-spring-security)
- [4. Registration flow](#4-registration-flow)
- [5. RedirectAttriibutes](#5-redirectattriibutes)
- [6. Remember Me](#6-remember-me)
    - [6.1 Основы](#61-Основы)
    - [6.2 Вариант с хешем](#62-Вариант-с-хешем)
    - [6.3 Persistent Token](#63-persistent-token)
    - [6.4 Настройка Remember Me с Persistent Token](#64-Настройка-remember-me-с-persistent-token)

<!-- /MarkdownTOC -->

[baeldung github] (https://github.com/eugenp/learn-spring-security/tree/module2)

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

**Настройка страницы логина**:

* свой адрес задать `http.formLogin().loginPage("/login").permitAll()`
* можно указать свой адрес обработки `http.formLogin().loginProcessingUrl("/doLogin")`. В форме такой же адрес указать.
* контроллер создать для этого адреса, возвращающий страницу-шаблон
* настроить страницу-шаблон:
    - обычно форма с методом POST `<form id="userForm" th:action="@{/doLogin}" method="post">`
    - параметры формы настроить. По умолчанию ожидает `username` и `password`. Можно настроить свой `http.formLogin().usernameParameter("my-username")` и в шаблоне `<input id="username" name="my-username"..>`
    - доступ к ошибкам `th:if="${param.error}"`


**Подключение **UserDetailsService****:

* создать класс, реализующий **UserDetailsService**
* метод интерфейса должен возвращать объект **userdetails.User** или ичключение **UserNotFoundException**. Поиск можно выполнять в БД например.
* класс подключаем через конфигурацию `auth.userDetailsService(userDetailsService)`

**Использование **PasswordEncoder****:

* регистрируем как бин
* используем в двух местах:
    - при создании новых пользователей для сохранения в БД хеша пароля
    - передаем в настройку `auth.passwordEncoder(passwordEncoder())`

**Подтверждение регистрации**:

* используется поле **UserDetails.enabled**
* при создании устанавливается в `false`
* для такого пользователя Spring Security не выполняет аутентификацию
* требуется, чтобы пользователь каким-нибудь образом подтвердил регистрацию, тогда поле `enabled` устанавливается в `true`.
* подтверждение выполняется через создание токена (`String token = UUID.randomUUID().toString`), добавлением даты протухания и сохранением в БД этого токена. Токен привязан к пользователю.
* далее формируется ссылка на основе токена `String confirmationUrl = "registrationConfirm?token=" + token.getToken();` и передается пользователю. Обычно на почту отсылается.
* Контроллер, обрабатывающий ссылку, извлекает токен из url, читает токен из БД, проверяет дату действия и если все хорошо, устанавливает `enabled` у пользователя в `true`. Не забыть разрешить доступ к ссылкам без аутентификации в конфигурации.

**Смена пароля**

* на странице логина размещаем ссылку `Forgot password?` на `/forgotPassword`
* контроллер обработки `GET /forgorPassword`:
    - возвращает представление `forgotPassword`, которое содержит поле для ввода имени пользователя (email)
* обработчик нажатия кнопки на форме `POST /user/resetPassword`:
    - получает пользователя по имени
    - создает токен для сброса пароля (токен связан с пользователем) и сохраняет токен в БД
    - формирует ссылку для сброса пароля (ссылка содержит id пользователя и токен `user/changePassword?id=1&token=6f35f6d9bdaf732da7...`)
    - если есть ошибки (пользователь неопределен) - перенаправляет на страницу логина с сообщением об ошибке
* контроллер обработки ссылки сброса пароля `GET user/changePassword&?id=...&token=...`:
    - извлекает id токена из url
    - получает токен из БД
    - проверяет, что токен не просрочен
    - получает пользователя из токена и сверяет его id с id из url
    - если всё ок - открывает представление `resetPassword`, в которое передает атрибутом токен
* форма `resetPassword.html` имеет два поля для пароля и подтверждения и скрытое поля для хранения токена
* обработчик нажатия кнопки на форме `POST /user/savePassword`:
    - проверяет пароль и подтверждение пароля
    - получает токен из БД
    - из токена пользователя
    - меняет пользователю пароль и сохраняет пользователя в БД
    - перенаправляет на начальную страницу логина

## 5. RedirectAttriibutes

Способ передать данные в представление при редиректах например для шаблона post/redirect/get.

Первый обработчик (метода post например) добавляет атрибуты в объект **RedirectAttriibutes**, затем выполняется редирект, в представлении есть доступ к атрибутам

    @PostMapping(...)
    public ModelAndView(..., RedirectAttriibutes ra) {
        ra.addAttribute("foo", "bar");
        ra.addFlashAttribute("someAttrName", value);
    }

метод **addAttribute()** добавляет атрибуты в параметры url `localhost:8080/login?foo=bar`. Соответственно можно только простые типы передавать. Плюс разрешение `.mvcMatchers("/login").permitAll()` нужно.

метод **addFlashAttribute()** добавляет во внутреннее хранилище. Можно любой сериализуемый тип передавать.

## 6. Remember Me

### 6.1 Основы

[docs](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-rememberme)

[статья ru](https://sysout.ru/sessii-i-remember-me-autentifikatsiya/)

[статья en](https://www.javadevjournal.com/spring-security/spring-security-remember-me/)

Для "запоминания" пользователя при повторных запросах без необходимости ввода пароля.

Сессии также дают схожий эффект, но есть отличия:

* время жизни сессии - 30 мин от последнего запроса
* сессиями управляет контейнер сервлетов и хранятся в куче контейнера, соответственно с остановкой контейнера, сессии пропадают
* сессии позволяют хранить разные данные, привязанные к ним
* реализованы через куки `JSESSIONID`

Механизм Remember Me:

* также работает через куки
* в токене куки хранится только имя пользователя, другие данные нельзя
* по умолчанию срок действия токена - 2 недели
* два варианта: Hash-Based Token и Persistent Token.

Для подробного анализа можно посмотреть **RememberMeAuthenticationFilter**

### 6.2 Вариант с хешем

В куки в явном виде включается имя пользователя. Пароль вместе с именем и секретным ключом хешируется.

    base64(username + ":" + expirationTime + ":" +
    md5Hex(username + ":" + expirationTime + ":" + password + ":" + key))

Восстановить пароль из куки нельзя, но сервер, получая куки, извлекает имя пользователя, получает пароль вычисляет хеш и сравнивает с полученным из куки. Почему пароль включен в хеш: если кука утекла, пользователь может сменить пароль и куки перестанет быть валидным.

Ключ надо задавать. Если не задан назначается автоматически при старте приложения и тогда при перезапуске токены станут невалидными.

**Настройка**

    http
        .rememberMe()

Далее разные возможности:

* **tokenValiditySeconds()** - срок действия токена
* **key()** - задание ключа для хеша
* **alwaysRemember()** - всегда использовать независимо от флага на форме
* **remember()** - всегда использовать независимо от флага на форме
* **rememberMeCookieName()** - задать имя куки
* **rememberMeParameter()** - задать имя параметра на форме (по умолчанию `remember-me`)
* и др.

Для работы нужен `UserDetailsService`. Если есть - подтянет. Иначе можно явно задать

    http.rememberMe().userDetailsService(...);

### 6.3 Persistent Token

После успешного логина создается токен **PersistentRememberMeToken** и сохраняется в БД. Токен содержит имя пользователя, дату создания, серию (случайное значение) и собственно токен (также случайное значение). См. **PersistentTokenBasedRememberMeServices::onLoginSuccess**.

Устанавливается куки. Причем куки содержит только серию и собственно токен. В отличие от Hash-Based Token не включает имя пользователя или пароль. Имя пользователя определяется через токен из хранилища.

При последующих запрос пользователь будет залогинен за счет сессий (`SecurityContextHolder.getContext().getAuthentication()` возвращает аутентифицированного пользователя). Если убрать куки `JSESSIONID` будет попытка авторизоваться через куки `remember-me`.

Авторизация выполняется так. Из куки получаем серию. По серии получаем токен из хранилища. Сравниваем значение токена из хранилища со значением токена из куки. Если они равны и токен в хранилище еще валидный, авторизуем пользователя и обновляем токен в хранилище: серия остается прежней, токен получает новое значение. Соответственно прежняя куки со старым токеном перестает работать. В ответе устанавливаем куки для нового токена.

[программная статья, ссылка есть в официальных доках, но там запрет](https://gist.github.com/oleg-andreyev/9dcef18ca3687e12a071648c1abff782)

Проблема с кражей куки: злоумышленник может зайти с украденной кукой, при этом токен обновится. Пользователь повторно зайти не сможет, будет вынужденн ввести пароль. Токен еще раз обновится и злоумышленник больше не сможет войти. При этом пользователь скорее всего не увидит, что было проникновение. Единственный способ - показывать время последнего входа. Серии улучшают ситуацию: если при попытке авторизации серии совпадают, но токены различаются, значит был выполнен вход с украденной куки. При этом вход с разных устройств через логин/пароль возможен, потому что серии будут различаться. Есть соответствующее исключение **CookieTheftException**.

**Но вообще желательно для авторизации через токен/куки не разрешать важные операции типа смены пароля или денежные операции.**

Итого преимущества Persisten Token перед Hash-Based:

* токен не содержит ни пароля, ни имени пользователя
* проще обнаружить кражу токена и отменить украденный токен: достаточно чтобы пользователь зашел с использованием токена после входа злоумышленника. При этом серия будет одинакова, но токен не будет совпадать, что сразу говорит о входе с украденным токеном. При этом кто это зашел - обычный пользователь или злоумышленник - неясно.

### 6.4 Настройка Remember Me с Persistent Token

Основной метод:

    http
        .rememberMe().tokenRepository(persistentTokenRepository())

Метод **persistentTokenRepository()** должен возвращать реализацию **PersistentTokenRepository**. Для отладки можно использовать в памяти. В проде - JDBC реализацию.

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
         JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
         db.setDataSource(dataSource);
         return db;
    }

В зависимостях может понадобится классы Spring Data.

Остальные методы настройки аналогичны случаю Hash-Based (**key()** не используется)
