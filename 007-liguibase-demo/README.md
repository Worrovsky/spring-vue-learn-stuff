[habr](https://habr.com/ru/post/460377/)

[habr 2](https://habr.com/ru/post/436994/)

[habr: sql vs xml](https://habr.com/ru/post/466651/)

Рекомендуется создавать структура БД вручную, не полагаясь на Hibernate. 

Поэтому 

    spring.jpa.hibernate.ddl-auto=validate

Или в крайнем случае

    spring.jpa.hibernate.ddl-auto=none


## 2. Настройка

**Добавляем зависимость**:

    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>


**Создаем файлы** с миграциями/скриптами:

[пример тут](https://docs.liquibase.com/tools-integrations/springboot/springboot.html)

[описание формата changelog-файла](https://docs.liquibase.com/concepts/basic/changelog.html) (! аккуратно на `xsi:schemaLocation=...`: нечетное количество, ошибку давать будет)

Создаем главный файл:

    resources/db/changelog/db.changelog-master.xml

Для работы нужна зависимость shakeyaml (Spring Boot дает)

Его же указываем в свойствах:

    spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

Дальше нужно создавать скрипты-changeset'ы и прописывать их в changelog'е. Тут есть разные варианты.

Стандартный подход: в главном changelog-файле прописаны changeset'ы.

[Вот](https://www.liquibase.org/blog/liquibase-without-changelogs) пример когда каждое изменение хранится в отдельном файле. В changelog'е просто указан импорт этих файлов. Порядок - по имени файла.




Liquibase можно отключить в файле свойств:

    spring.liquibase.enabled=false
