<!-- MarkdownTOC autolink="true" uri_encoding="false" -->

- [1. Собственный AuthenticationProvider](#1-Собственный-authenticationprovider)
- [2. Несколько AuthenticationProvider. Настройка ProviderManager.](#2-Несколько-authenticationprovider-Настройка-providermanager)
- [3. Варианты хранения данных пользователей](#3-Варианты-хранения-данных-пользователей)

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




