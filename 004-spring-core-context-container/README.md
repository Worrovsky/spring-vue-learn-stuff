# [Spring Core] Контейнер Spring, жизненный цикл 

<!-- MarkdownTOC autolink="true" -->

- [1. Создание конейнера Spring](#1-%D0%A1%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BA%D0%BE%D0%BD%D0%B5%D0%B9%D0%BD%D0%B5%D1%80%D0%B0-spring)
    - [1.1 Не веб приложение](#11-%D0%9D%D0%B5-%D0%B2%D0%B5%D0%B1-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5)
        - [1.1.1 Через аннотации \(AnnotationConfigApplicationContext\)](#111-%D0%A7%D0%B5%D1%80%D0%B5%D0%B7-%D0%B0%D0%BD%D0%BD%D0%BE%D1%82%D0%B0%D1%86%D0%B8%D0%B8-annotationconfigapplicationcontext)
        - [1.1.2 Через xml конфигурацию в classpath \(ClassPathXmlApplicationContext\)](#112-%D0%A7%D0%B5%D1%80%D0%B5%D0%B7-xml-%D0%BA%D0%BE%D0%BD%D1%84%D0%B8%D0%B3%D1%83%D1%80%D0%B0%D1%86%D0%B8%D1%8E-%D0%B2-classpath-classpathxmlapplicationcontext)
        - [1.1.3 Через xml конфигурацию в файле \(FileSystemXmlApplicationContext\)](#113-%D0%A7%D0%B5%D1%80%D0%B5%D0%B7-xml-%D0%BA%D0%BE%D0%BD%D1%84%D0%B8%D0%B3%D1%83%D1%80%D0%B0%D1%86%D0%B8%D1%8E-%D0%B2-%D1%84%D0%B0%D0%B9%D0%BB%D0%B5-filesystemxmlapplicationcontext)
    - [1.2 Веб приложение](#12-%D0%92%D0%B5%D0%B1-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5)
        - [1.2.1 Через Servlet 2 API](#121-%D0%A7%D0%B5%D1%80%D0%B5%D0%B7-servlet-2-api)
        - [1.2.2 Через Servlet 3 API через xml конфигурацию](#122-%D0%A7%D0%B5%D1%80%D0%B5%D0%B7-servlet-3-api-%D1%87%D0%B5%D1%80%D0%B5%D0%B7-xml-%D0%BA%D0%BE%D0%BD%D1%84%D0%B8%D0%B3%D1%83%D1%80%D0%B0%D1%86%D0%B8%D1%8E)
        - [1.2.3 Через Servlet 3 API через аннотации](#123-%D0%A7%D0%B5%D1%80%D0%B5%D0%B7-servlet-3-api-%D1%87%D0%B5%D1%80%D0%B5%D0%B7-%D0%B0%D0%BD%D0%BD%D0%BE%D1%82%D0%B0%D1%86%D0%B8%D0%B8)
    - [1.3 Spring Boot](#13-spring-boot)
- [2. Жизненный цикл контейнера и бинов](#2-%D0%96%D0%B8%D0%B7%D0%BD%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9-%D1%86%D0%B8%D0%BA%D0%BB-%D0%BA%D0%BE%D0%BD%D1%82%D0%B5%D0%B9%D0%BD%D0%B5%D1%80%D0%B0-%D0%B8-%D0%B1%D0%B8%D0%BD%D0%BE%D0%B2)
    - [2.1 Аннотации @PostConstruct и @PreDestroy](#21-%D0%90%D0%BD%D0%BD%D0%BE%D1%82%D0%B0%D1%86%D0%B8%D0%B8-postconstruct-%D0%B8-predestroy)
    - [2.2 Переопределение BeanFactoryPostProcessor](#22-%D0%9F%D0%B5%D1%80%D0%B5%D0%BE%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5-beanfactorypostprocessor)
    - [2.3 Переопределение BeanPostProcessor](#23-%D0%9F%D0%B5%D1%80%D0%B5%D0%BE%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5-beanpostprocessor)
- [3. Жизненный цикл бина \(более подробно\)](#3-%D0%96%D0%B8%D0%B7%D0%BD%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9-%D1%86%D0%B8%D0%BA%D0%BB-%D0%B1%D0%B8%D0%BD%D0%B0-%D0%B1%D0%BE%D0%BB%D0%B5%D0%B5-%D0%BF%D0%BE%D0%B4%D1%80%D0%BE%D0%B1%D0%BD%D0%BE)
- [4. Закрытие контейнера](#4-%D0%97%D0%B0%D0%BA%D1%80%D1%8B%D1%82%D0%B8%D0%B5-%D0%BA%D0%BE%D0%BD%D1%82%D0%B5%D0%B9%D0%BD%D0%B5%D1%80%D0%B0)

<!-- /MarkdownTOC -->


## 1. Создание конейнера Spring 

### 1.1 Не веб приложение

#### 1.1.1 Через аннотации (AnnotationConfigApplicationContext)

можно создать экземпляр **AnnotationConfigApplicationContext**

У этого класса есть методы из разных интерфейсов для чтения/создания конфигурации бинов:

* **scan(String ...basePackage)** - ищет определения бинов в указаном пакете
* **register(Class<?> ...componentClasses)** - определения на основе компонентных классов 
 - с аннотацией `@ComponentScan`, где указывается пакет для сканирования (или без указания явного, по пакету класса)
 - с аннотацией `@Configuration`, в этом классе содержатся бины
* **refresh()** - обновляет конфигурацию. 

Соответственно разные конструкторы:

* пустой
    - для дальнейшего вызова `scan()` с указанием пакета 
    - или вызова `register()` с указанием конфигурационного класса
    - и вызова `refresh()`
* с указанием конфигурационного класса
    - внутри вызывается `register()` и `refresh()`
* с указанием пакета с бинами
    - внутри вызывается `scan()` и `refresh()`

**Итого способы**:

* через указание пакетов для сканирования строкой
* через класс, указывающий на пакет (**@ComponentScan**)
* через класс, содержащий бины (**@Configuration**)


#### 1.1.2 Через xml конфигурацию в classpath (ClassPathXmlApplicationContext)

Задаем конфигурацию в xml в `/src/main/resources`

получаем контекст 
    
    ApplicationContext contextFromClasspathXml = new 
        new ClassPathXmlApplicationContext("configs.xml");

#### 1.1.3 Через xml конфигурацию в файле (FileSystemXmlApplicationContext)

Получаем ресурс
    
    String beansXmlLocationOnFilesystem = App.class.getResource("/beanConfig.xml").toExternalForm();

Получаем конфигурацию

    ApplicationContext contextFromFileXml = new FileSystemXmlApplicationContext(beansXmlLocationOnFilesystem);


### 1.2 Веб приложение

#### 1.2.1 Через Servlet 2 API

* Создаем `src/java/webapp/WEB_INF/web.xml`
    - listener указываем 
    - servlet настраиваем (через спринговский **DispatcherServlet**)
        + указываем файл конфигурации бинов `WEB-INF/beans.xml`
    - настраиваем маппинги для контроллеров
* настраиваем файл `WEB-INF/beans.xml`
    - контроллеры описываем
    - другие бины (сервисы и пр.)
* запускается в контейнере сервлетов (tomcat и т. п.)

#### 1.2.2 Через Servlet 3 API через xml конфигурацию

* Создаем класс, реализующий **WebApplicationInitializer**
    - загружает контекст из кофигурационного файла `WEB-INF/beans.xml` через **XmlWebApplicationContext**
    - регистрируем **DispatcherServlet**
    - задаем маппинги
* есть файл `WEB-INF/web.xml`, но пустой и формат отличен от версии для API v. 2
* бины, контроллер описываются в файле `WEB-INF/beans.xml`

#### 1.2.3 Через Servlet 3 API через аннотации

* Создаем класс, реализующий **WebApplicationInitializer**
    - загружает контекст через **AnnotationConfigWebApplicationContext** (аналог AnnotationConfigApplicationContext, см. 1.1.1)
    - регистрируем **DispatcherServlet**
    - задаем маппинги
* есть файл `WEB-INF/web.xml`, но пустой и формат отличен от версии для API v. 2
* бины конфигурируются одним из способов п. 1.1.1





### 1.3 Spring Boot

На себя берет дополнительную работу

Сканирует автоматически в корневом пакете

Просто подключай зависимости (стартеры)

Для CLI создавай бин, реализующий CommandLineRunner (можно на самом **@SpringBootApplication**)

## 2. Жизненный цикл контейнера и бинов 

### 2.1 Аннотации @PostConstruct и @PreDestroy

Подключить зависимость javax.annotation/javax.annotation-api

Аннотации можно ставить на методах бина, будут вызваны контейнером

### 2.2 Переопределение BeanFactoryPostProcessor

Создаем класс, реализующий **BeanFactoryPostProcessor**

Включаем в состав контейнера (любым способом, например через `@Component`)

Переопределяем метод `postProcessBeanFactory()`

Внутри есть доступ к определениям бинов

Вызывается после чтения конфигурации и создания описания бинов, до фактического создания экземпляров бинов

### 2.3 Переопределение BeanPostProcessor

Создаем класс, реализующий **BeanPostProcessor**

Включаем в состав контейнера (любым способом, например через `@Component`)
 
Два метода можно переопределить: `postProcessBeforeInitialization()` и `postProcessAfterInitialization()`. Внутри есть доступ к имени бина и экземпляру бина

Порядок вызова:

* сначала конструктор бина
* затем метод BeanPostProcessor `postProcessBeforeInitialization()`
* метод бина с `@PostConstruct`
* метод BeanPostProcessor `postProcessAfterInitialization()`

## 3. Жизненный цикл бина (более подробно)

* создается контекст
    - определения бинов создаются на основе конфигурации
    - вызывается метод интерфейса **BeanFactoryPostProcessor**
* создаются бины 
    - создаются экземпляры бинов
    - устанавливаются зависимости и свойства
    - вызывается **BeanPostProcessor::postProcessBeforeInitialization()**
    - вызывается метод с аннотацией **@PostConstruct**
    - вызывается метод **InitializingBean::afterPropertiesSet()**
        + бин должен реализовывать этот интерфейс
        + для проверки/установки свойств
    - вызывается метод из аннотации **@Bean(initMethod=...)**
    - вызывается **BeanPostProcessor::postProcessAfterInitialization()**
* бин готов к использованию
* контекст уничтожается, и вместе с ним уничтожаются бины
    - вызывается метод с аннотацией **@PreDestroy**
    - вызывается метод **DisposableBean::destroy()**
        + бин должен реализовывать этот интерфейс
    - вызывается метод из аннотации **@Bean(destroyMethod=...)**

Еще более подробно, см. [BeanFactory docs](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/BeanFactory.html)

## 4. Закрытие контейнера

Если не закрыть правильно, не будут вызваны методы жизненного цикла на уничтожение бинов (@`PreDestroy` и т. п.)

Способы закрытия: 

* не веб-приложение:
    - (рекомендуется) установить хук **ConfigurableApplicationContext::registerShutdownHook()**
        + надежнее чем `close` из-за исключений
    - вызвать **ConfigurableApplicationContext::close()**
* веб-приложение:
    - **ContextLoaderListener** автоматически закроет контекст, когда веб-контейнер остановит приложение
* SpringBoot:
    - **ApplicationContext** будет автоматически остановлен
    - Shutdown хук регистрируется автоматически
    - **ContextLoaderListener** также автоматически закрывает контекст в веб-приложении 

Кратко:

* веб, SpringBoot - закроют автоматом
* в остальных - `ctx.registerShutdownHook();`
