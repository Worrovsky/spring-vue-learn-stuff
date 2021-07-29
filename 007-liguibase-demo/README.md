<!-- MarkdownTOC autolink="true" uri_encoding="false" -->

- [1. Ссылки](#1-Ссылки)
- [2. Про Hibernate](#2-Про-hibernate)
- [3. Настройка](#3-Настройка)
- [4. Todo](#4-todo)

<!-- /MarkdownTOC -->


## 1. Ссылки

[habr](https://habr.com/ru/post/460377/)

[habr 2](https://habr.com/ru/post/436994/)

[habr: sql vs xml](https://habr.com/ru/post/466651/)

## 2. Про Hibernate

Рекомендуется создавать структура БД вручную, не полагаясь на Hibernate. 

Поэтому 

    spring.jpa.hibernate.ddl-auto=validate

Или в крайнем случае

    spring.jpa.hibernate.ddl-auto=none


## 3. Настройка

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

**Вариант № 1**

Стандартный подход: в главном changelog-файле прописаны changeset'ы. 

Описывать changeset'ы тоже можно по разному:

    // xml
    <changeSet context="legacy" author="author (generated)" id="1">
        <createTable tableName="test">
            <column name="c1" type="VARCHAR(255)"/>
        </createTable>
    </changeSet>

    // встроенный sql
    <changeSet context="legacy" author="author" id="1-domain-some-domain">
        <sql>
            CREATE DOMAIN public.some_domain AS bigint;
            ALTER DOMAIN public.some_domain OWNER TO test;
        </sql>
    </changeSet>

    // файлы с sql
    <changeSet context="legacy" author="author" id="1-user">
         <sqlFile dbms="postgresql" path="sql/some.sql"  relativeToChangelogFile="true" />
    </changeSet>

**Вариант № 2**

Есть changelog-файл, вместо changeset'ов - отдельные файлы (xml или sql), в changelog включаются через тег `include` [docs](https://docs.liquibase.com/concepts/advanced/include.html)

Порядок применения - по порядку включения в changelog-файле.

Минус: в таблице DATABASECHANGELOG колонки Id и Author заполнены заглушками.

Можно исправить так (здесь заданы имя пользователя и Ид (Ид можно одинаковые)), но порядок все равно определяется именем файла

    --liquibase formatted sql
    --changeset some_user:1  

**Вариант № 3**

Как и в варианте № 2 changeset'ы хранятся в отдельных файлах. Но в changelog каждый файл не прописывается, вместо этого используется тег `includeAll`

[Пример тут](https://www.liquibase.org/blog/liquibase-without-changelogs) пример когда каждое изменение хранится в отдельном файле. 

Порядок применения: по имени файла, поэтому можно иерархию по типу 0001-...

Минус: в таблице DATABASECHANGELOG колонки Id и Author заполнены заглушками.

Можно исправить так, но порядок все равно определяется именем файла

    --liquibase formatted sql
    --changeset some_user:1  
    create sequence hibernate_sequence start with 1 increment by 1;


**Итого оптимальный вариант**

Лучшим кажется вариант с `includeAll` и миграциями в отдельных sql-файлах. Файлы раскиданы по папкам и структурированы, например YYYY-MM-DD-TASK_NNN. Также файлы содержат имя пользователя и Ид (Ид - обязателен, но можно произвольный)`--changeset some_user:1`


Liquibase можно отключить в файле свойств:

    spring.liquibase.enabled=false

## 4. Todo

* запуск через Maven
* разные условия для профилей prod/dev