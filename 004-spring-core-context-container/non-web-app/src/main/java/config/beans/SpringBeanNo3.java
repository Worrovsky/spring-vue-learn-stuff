package config.beans;

import org.springframework.stereotype.Component;

@Component
public class SpringBeanNo3 implements Greetable{

    public void sayHello() {
        System.out.println("Hello from " + getClass().getName());
    }

}
