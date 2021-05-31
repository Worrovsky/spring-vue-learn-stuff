WIP

https://github.com/eugenp/learn-spring-security/tree/module2

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