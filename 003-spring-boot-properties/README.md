# Проект для тестирования различных способов задания свойств

[spring boot docs/how-to](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#howto-properties-and-configuration)

## TODO

https://www.baeldung.com/properties-with-spring

## Общее про свойства

* можно комбинировать в файлах и самих свойствах
    - например в файле `spring.datasourse.url=jdbc:postgres://${DB_HOST}/${DB_NAME}` (предполагается, что созданы такие переменные окружения)
    - `@Value("${nameFromProperties}: ${userId}")`
* можно задавать значения по умолчанию через `:` (как в файлах свойств, так и в `@Value`)


Можно поключить [Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready) и просматривать свойства, профили и много еще чего

## Через аннотацию `@Value`

[baeldung](https://www.baeldung.com/spring-value-annotation)

* над полем бина ставим аннотацию ` @Value("${nameFromProperties}")`
* задаем в файле свойств под имененем `nameFromProperties`

Особенности:

* конвенции именования - точное, как задано в аннотации, так и файле свойств
* типы: String, boolean, int
* можно указывать значения по умолчанию `@Value("${HOME:defaultHomeProperty}")`
* можно получать системные переменные
* можно в параметрах указывать

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

## Enviroment

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
* С помощью **ApplicationRunner** (хотя это похоже общий механизм Spring, тут не обязательно именно ApplicationRunner)

Вот пример:

    @Bean
    ApplicationRunner applicationRunner(Environment env) {
        return args -> {
            String property = env.getProperty("somePropertyFromApplicationProperties");
        } }


Кстати почему именно в файле `application.properties` ищется - задано настройками. Можно переопределить через `--spring.config.name=...`

### Работа с профилями 

Задать активные профили:

* через командную строку `--spring.proviles.active=dev`
* в Idea `Edit configuration...`
* через свойство `spring.profile.active=prod,local`

Теперь файлы свойств можно разделять: `application-dev.properties`, `vapplication-prod.properties`

Можно создать файл свойств для профиля по умолчанию `application-default.properties`

Бины можно по профилям разделять: `@Profile("dev")`


## Собстенный источник свойств через расширение класса PropertySource

Создаем класс и реализуем метод `getProperty()`

    public class CustomProperty extends PropertySource<String> {
        @Override
        public Object getProperty(String name) {
            if (name.equalsIgnoreCase("custom-property")) {
                return "value for custom property";
            }
            return null;
        } }

Регистрируем его как источник свойств в контексте: см. `Application::main` ()

Теперь свойства, определенные в классе можно использовать как обычные


## Варианты с внешними источниками свойств

Хранение свойств на github и сервер для доступа к ним (легко, пара зависимостей). [Подробнее (youtube)](https://youtu.be/PsNNGuLi0ns?t=1995)

Использование хранилища Vautl для безопасного хранения credentials [Там же](https://youtu.be/PsNNGuLi0ns?t=2347)

## Аннотация @PropertySource

[docs api](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/PropertySource.html)

Создаем класс, помечаем этой аннотацией и `@Configuration`. Внутри аннотации указываем путь к файлу со свойствами.

Внутри класса получаем экземпляр `Environment`, и можем читать свойства из файла

    @Configuration
    @PropertySource("classpath:/com/myco/app.properties")
    public class AppConfig {
        @Autowired
        private Environment env;
        ...
            env.getProperty("acme.someProperty");
    }

Внутри `@PropertySource` можно свойства из других источников использовать. Так например разделить свойства по переменной окружения (по типу профиля - несколько файлов) `@PropertySource("classpath:/com/${my.placeholder:default/path}/app.properties")`

[см. пример](https://github.com/eugenp/REST-With-Spring/blob/module1/m1-lesson4/um-webapp/src/main/java/com/baeldung/um/spring/UmPersistenceJpaConfig.java)


