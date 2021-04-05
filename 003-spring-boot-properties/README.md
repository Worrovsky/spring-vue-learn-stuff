# Проект для тестирования различных способов задания свойств

[spring boot docs/how-to](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#howto-properties-and-configuration)

## Через аннотацию `@Value`

[baeldung](https://www.baeldung.com/spring-value-annotation)

* над полем бина ставим аннотацию ` @Value("${nameFromProperties}")`
* задаем в файле свойств под имененем `nameFromProperties`

Особенности:

* конвенции именования - точное, как задано в аннотации, так и файле свойств
* типы: String, boolean, int

## Конфигурационный класс с аннотацией `@ConfigurationProperties`

[baeldung](https://www.baeldung.com/configuration-properties-in-spring-boot)

* создать класс с полями-свойствами, пометить аннотацией `@ConfigurationProperties(prefix = "email")`
* для свойств создать геттеры/сеттеры
* зарегистрировать в конфигурации:
    - добавить к классу-конфигурации (или `@SprinBootApplication`) аннотацию `@EnableConfigurationProperties(EmailProps.class)`
    - или над самим классом со свойствами добавить `@Configuration`
* определить

Особенности:

* конвенции именования - разные: точные, камед-case, дефис, подчеркивание и т. п., но с префиксом