package com.example.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ServiceBean {

    public ServiceBean() {
        System.out.println(getClass().getName() + ": constructor done!");
    }

    public void doWork() {
        System.out.println(getClass().getName() + ": work done!");
    }

    @PostConstruct
    public void init() {
        System.out.println(getClass().getName() + ": post construct done!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("");
        System.out.println( getClass().getName() + ": pre destroy done!");
    }
}
