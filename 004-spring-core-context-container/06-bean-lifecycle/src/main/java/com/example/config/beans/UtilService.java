package com.example.config.beans;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class UtilService implements InitializingBean {

    public UtilService() {
        System.out.println("");
        System.out.println(getClass().getName() + ": constructor done.");
    }

    public void doWork() {
        System.out.println("");
        System.out.println(getClass().getName() + ": work done!");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(getClass().getName() + ": @PostConstruct done.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("\n" + getClass().getName() + ": @PreDestroy done.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(getClass().getName() + ": InitializingBean::afterPropertiesSet done.");
    }
}
