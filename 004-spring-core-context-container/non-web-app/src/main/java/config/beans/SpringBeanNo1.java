package config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanNo1 implements Greetable{

    public void sayHello() {
        System.out.println("Hello from " + getClass().getName());
    }

}
