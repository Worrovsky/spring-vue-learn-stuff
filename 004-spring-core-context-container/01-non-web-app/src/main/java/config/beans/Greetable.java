package config.beans;

public interface Greetable {

    public default void sayHello() {
        System.out.println("Hello from " + getClass().getName());
    }
}
