package com.example.spring;

import javax.annotation.PreDestroy;

public interface BeanLifeCycle {

    @PreDestroy
    public default void preDestroy() {
        System.out.println(getClass().getName() + ": pre destroy done!");
    }
}
