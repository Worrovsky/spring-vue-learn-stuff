[habr](https://habr.com/ru/post/460377/)

Рекомендуется создавать структура БД вручную, не полагаясь на Hibernate. 

Поэтому 

    spring.jpa.hibernate.ddl-auto=validate

Или в крайнем случае

    spring.jpa.hibernate.ddl-auto=none