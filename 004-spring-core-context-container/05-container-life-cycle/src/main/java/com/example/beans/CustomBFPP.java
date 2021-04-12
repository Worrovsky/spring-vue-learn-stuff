package com.example.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomBFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println(getClass().getName() + ": postProcessBeanFactory start:");
        Arrays.stream(configurableListableBeanFactory.getBeanDefinitionNames()).forEach(System.out::println);

        BeanDefinition definition = configurableListableBeanFactory.getBeanDefinition("serviceBean");
        System.out.println(getClass().getName() + ": postProcessBeanFactory done!");
    }
}
