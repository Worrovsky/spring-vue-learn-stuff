# Проект для тестирования различных способов задания свойств

[spring boot docs/how-to](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#howto-properties-and-configuration)

## Через аннотацию `@Value`

[baeldung](https://www.baeldung.com/spring-value-annotation)

* над полем бина ставим аннотацию ` @Value("${nameFromProperties}")`
* задаем в файле свойств под имененем `nameFromProperties`

Особенности:

* конвенции именования - точное, как задано в аннотации, так и файле свойств
* типы: String, boolean, int
* можно указывать значения по умолчанию
* можно получать системные переменные
* можно в параметрах указывать

Пример системной переменной в параметре

    

## Конфигурационный класс с аннотацией `@ConfigurationProperties`

[baeldung](https://www.baeldung.com/configuration-properties-in-spring-boot)

* создать класс с полями-свойствами, пометить аннотацией `@ConfigurationProperties(prefix = "email")`
* для свойств создать геттеры/сеттеры
* зарегистрировать в конфигурации:
    - добавить к классу-конфигурации (или `@SprinBootApplication`) аннотацию `@EnableConfigurationProperties(EmailProps.class)`
    - или над самим классом со свойствами добавить `@Configuration`
* определить свойства в файле свойств

Особенности:

* конвенции именования - разные: точные, камед-case, дефис, подчеркивание и т. п., но с префиксом

## `@PropertySource` и Enviroment

[docs](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/PropertySource.html)
[medium (ru)](https://medium.com/@ekaterina.khudiakova/https-medium-com-ekaterina-khudiakova-spring-environment-profiles-and-properties-c712685ea7a7)
[spring blog](https://spring.io/blog/2020/04/23/spring-tips-configuration)
[baeldung](https://www.baeldung.com/properties-with-spring)

**Environment** - интерфейс. Представляет окружение, в котором запущенно приложение: профиль и свойства (profile and properties). Родительский интерфейс - PropertyResolver.

Методы: 

* String[] getActiveProfiles()
* String getPropery(String key)

Как получить доступ:

* Через `@Autowired`: `@Autowired Environment env;`
* С помощью **ApplicationRunner**

Вот пример:

    @Bean
    ApplicationRunner applicationRunner(Environment env) {
        return args -> {
            String property = env.getProperty("somePropertyFromApplicationProperties");
        } }


Кстати почему именно в файле `application.properties` ищется - задано настройками. Можно переопределить через `--spring.config.name=...`

### Работа с профилями 

Задать активные профили:

* через коммандную строку `--spring.proviles.active=dev`
* в Idea `Edit configuration...`

Теперь файлы свойств можно разделять: `application-dev.properties`, `vapplication-prod.properties`

Можно создать файл свойств для профиля по умолчанию `application-default.properties`
