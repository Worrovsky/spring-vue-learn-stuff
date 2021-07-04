<!-- MarkdownTOC autolink="true" uri_encoding="false" -->

- [1. Собственный AuthenticationProvider](#1-Собственный-authenticationprovider)

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
