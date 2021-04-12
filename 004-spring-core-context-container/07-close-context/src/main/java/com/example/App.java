package com.example;

import com.example.spring.no1.BeanNo1;
import com.example.spring.no2.BeanNo2;
import com.example.spring.no2.ConfigComponentScanNo2;
import com.example.spring.no3.BeanNo3;
import com.example.spring.no1.ConfigComponentScanNo1;
import com.example.spring.no3.ConfigComponentScanNo3;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctxNoClosing = new AnnotationConfigApplicationContext();
        ctxNoClosing.register(ConfigComponentScanNo1.class);
        ctxNoClosing.refresh();

        BeanNo1 b1 = ctxNoClosing.getBean(BeanNo1.class);

        AnnotationConfigApplicationContext ctxWithCloseMethod = new AnnotationConfigApplicationContext();
        ctxWithCloseMethod.register(ConfigComponentScanNo2.class);
        ctxWithCloseMethod.refresh();

        BeanNo2 b2 = ctxWithCloseMethod.getBean(BeanNo2.class);
        ctxWithCloseMethod.close();

        AnnotationConfigApplicationContext ctxWithHook = new AnnotationConfigApplicationContext();
        ctxWithHook.register(ConfigComponentScanNo3.class);
        ctxWithHook.refresh();
        ctxWithHook.registerShutdownHook();

        BeanNo3 b3 = ctxWithHook.getBean(BeanNo3.class);

    }
}
