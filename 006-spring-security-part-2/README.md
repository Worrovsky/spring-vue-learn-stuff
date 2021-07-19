<!-- MarkdownTOC autolink="true" uri_encoding="false" -->

- [1. Собственный AuthenticationProvider](#1-Собственный-authenticationprovider)
- [2. Несколько AuthenticationProvider. Настройка ProviderManager.](#2-Несколько-authenticationprovider-Настройка-providermanager)
- [3. Варианты хранения данных пользователей](#3-Варианты-хранения-данных-пользователей)
- [4. Работа с сессиями](#4-Работа-с-сессиями)
    - [4.1 Разные настройки](#41-Разные-настройки)
    - [4.2 Информация о сессии](#42-Информация-о-сессии)
    - [4.3 Защита от атаки Session Fixation](#43-Защита-от-атаки-session-fixation)

<!-- /MarkdownTOC -->


[github baeldung](https://github.com/eugenp/learn-spring-security/tree/lssc-module8-upgraded)

## 1. Собственный AuthenticationProvider

Что нужно сделать:

* создать класс, реализующий интерфейс **AuthenticationProvider**. В нем два метода.
* подключить этот класс в конфигурацию

Интерфейс **AuthenticationProvider** содержит два метода.

Первый метод - проверка, что этот **AuthenticationProvider** способен работать с указанным классом-реализацией **Authentication** (например **UsernamePasswordAuthenticationToken**): 

    public boolean supports(Class<?> aClass) { .. }

Второй собственно выполняет аутентификацию:

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {...}

Разные моменты:

* исключение **AuthenticationException** - общее неперхватываемое. Есть более конкретные дочерние, например **BadCredentialsException**
* логика работы `authenticate()`:
    - извлечь credentials, пользователя
    - проверить их (возможно через какую-то стороннюю систему)
    - вызвать исключение, если что-то не так
    - или создать новый экземпляр **Authentication**
        + при создании нельзя явно вызывать `setAuthenticate(true)` - в коде запрет на это. Просто при создании устанавливается в `true`

В качестве примера можно посмотреть **DaoAuthenticationProvider**

Подключение в конфигурацию выполняется так:

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("1").authorities("ROLE_USER");
        auth.authenticationProvider(authenticationProvider);
    }


## 2. Несколько AuthenticationProvider. Настройка ProviderManager. 

Можно добавлять несколько AuthenticationProvider:

    auth.authenticationProvider(provider1).authenticationProvider(provider2);

Добавляются в `list`. Проверяются в том порядке, в котором добавлены. Если один провайдер аутентифицирует пользователя, остальные не проверяются.

Можно создать ProviderManager для более тонкой настройки.

    ProviderManager provider = new ProviderManager(blankDaoProvider);
    auth.parentAuthenticationManager(provider);

Можно указать стирать credentials или нет.



## 3. Варианты хранения данных пользователей

**InMemory**:

    auth.inMemoryAuthentication()
                .withUser("user").password("1").authorities("ROLE_USER");

Для тестов, хранит данные в списке в памяти

**JDBC**

    auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
                .withUser("bob").password("1").authorities("ROLE-USER");

Здесь нужен **DataSource**, можно любым способом (напр. достаточно зависимости H2 + Jdbc Starter)


**JPA**

Добавляем **зависимость** на БД и JPA

    <dependency>
        <groupId> {...}Id>spring-boot-starter-data-jpa</artifactId>
        <version>${spring.boot.version}</version>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>1.4.200</version>
    </dependency>

Создаем **сущность и репозиторий** для нее.

    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        public User findByName(String name);
    }

Создаем **UserDetailsService**

    public class UserDetailsService003 implements UserDetailsService {
        @Autowired
        private UserRepository repository;
        @Override
        public UserDetails loadUserByUsername(String username) {...}


**Конфигурируем Spring Security**

       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());


## 4. Работа с сессиями

### 4.1 Разные настройки

Настройка **максимального количества сессий** на пользователя:

    http.sessionManagement().maximumSessions(1);

При этом если пользователь логинится еще раз, предыдущая сессия станет невалидной (увидит это при попытке получить данные с куки старой сессии, просто как сообщение на странице).

Можно **задать url**, куда будет перенаправлять, если сессия истекла:

    http.sessionManagment().expiredUrl("/expireSession");

Соответственно нужен контроллер и настройка доступа к этому url.

### 4.2 Информация о сессии

Есть класс **SessionInformation**, представляющий сессию. Содержит строковый Id сессии, дату последнего запроса, данные пользователя (principal в виде строки), истекла или нет.

Есть интерфейс **SessionRegistry**, который управляет данными сессии **SessionInformation** (создает, обновляет, получает). 

Основные методы:

* **getAllPrincipals()** - возвращает все известные principal
*  **getAllSessions(Opject principal)** - возвращает сессии, связанные с principal
*  **getSessionInformation(String sessionId)** - получает инфо сессии 
*  **refreshLastRequest(String sessionId)** 
*  **registerNewSession(String sessionId, Object principal)**

Реализация: **SessionRegistryImpl**, хранит данные сессий и principal в **ConcurrrentMap**, также подписана на удаление сессии.

Подключение **SessionRegistry**:

* создать бин (например в классе-конфигурации)
    - `return new SessionRegistryImpl();`
* подключить к конфигурации
    - `http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());`
* использовать, например в контроллере
    - `sessionRegistry.getAllPrincipals(); ...`

### 4.3 Защита от атаки Session Fixation

[javadev](https://www.javadevjournal.com/spring-security/spring-security-session-fixation/)

Суть атаки: 

* атакующий подсовывает пользователю свою неавторизованную сессию, например в url `http://example.com?sid=...` (как через куки вопрос)
* пользователь авторизуется под этой сессией
* теперь у атакующего есть id авторизованной сессии, а т. к. Spring Security поддерживает авторизацию через сессии, значит атакующий авторизован

Основная идея защиты: смена сессии после авторизации.

Настройка в Spring:

    http.sessionManagement().sessionFixation().none();

Варианты:

* `none()` - отсутствие защиты, Spring ничего не делает, предполагается, что самостоятельно реализуют защиту
* `newSession()` - создается новая сессия, атрибуты предыдущей сессии не сохраняются
* `migrateSession()` - создается новая сессия, атрибуты предыдущей сохраняются (оптимальное решение в большинстве случаев)
* `changeSessionId()` - меняется Ид сессии через методы сервлета (> Servlet 3.1)


