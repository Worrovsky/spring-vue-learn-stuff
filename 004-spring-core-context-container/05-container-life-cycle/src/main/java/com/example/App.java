package com.example;

import com.example.beans.AppConfig;
import com.example.beans.ServiceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        ServiceBean bean = ctx.getBean(ServiceBean.class);
        bean.doWork();

        ctx.close();
    }
}
