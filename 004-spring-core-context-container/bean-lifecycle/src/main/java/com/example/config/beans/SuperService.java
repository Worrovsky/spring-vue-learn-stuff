package com.example.config.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


public class SuperService implements InitializingBean, DisposableBean {

    private UtilService utilService;

    @Autowired
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
        System.out.println(getClass().getName() + ": set util service bean done.");
    }

    public SuperService() {
        System.out.println("");
        System.out.println(getClass().getName() + ": constructor done.");
    }

    public void doWork() {
        utilService.doWork();
        System.out.println(getClass().getName() + ": work done!");
        System.out.println("");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(getClass().getName() + ": @PostConstruct done.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(getClass().getName() + ": @PreDestroy done.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(getClass().getName() + ": interface InitializingBean::afterPropertiesSet done.");
    }

    private void initMethod() {
        System.out.println(getClass().getName() + ": @Bean(initMethod) done.");
    }

    private void destroyMethod() {
        System.out.println(getClass().getName() + ": @Bean(destroy method) done.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(getClass().getName() + ": interface DisposableBean::destroy done.");
    }
}
