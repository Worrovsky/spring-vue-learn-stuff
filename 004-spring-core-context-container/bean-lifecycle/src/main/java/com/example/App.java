package com.example;

import com.example.config.ConfigComponentScan;
import com.example.config.beans.SuperService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ConfigComponentScan.class);
        ctx.refresh();

        SuperService service = ctx.getBean(SuperService.class);
        service.doWork();

        ctx.close();

    }
}
