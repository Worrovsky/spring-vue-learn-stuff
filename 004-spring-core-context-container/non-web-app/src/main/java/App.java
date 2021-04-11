import config.BeanConfig;
import config.BeanPackageScanConfig;
import config.beans.Greetable;
import config.beans.SpringBeanNo1;
import config.beans.SpringBeanNo2;
import config.beans.SpringBeanNo3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.env.Environment;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext contextFromEmptyConstr = new AnnotationConfigApplicationContext();
        contextFromEmptyConstr.register(BeanPackageScanConfig.class);
        contextFromEmptyConstr.refresh();
        Greetable beanNo1 = contextFromEmptyConstr.getBean(SpringBeanNo1.class);
        beanNo1.sayHello();

        ApplicationContext contextFromPackage = new AnnotationConfigApplicationContext("config.beans");
        Greetable beanNo2 = contextFromPackage.getBean(SpringBeanNo2.class);
        beanNo2.sayHello();

        ApplicationContext contextFromBeanPackageConfigClass = new AnnotationConfigApplicationContext(BeanPackageScanConfig.class);
        Greetable beanNo3 = contextFromBeanPackageConfigClass.getBean(SpringBeanNo3.class);
        beanNo3.sayHello();

        ApplicationContext contextFromBeanConfigClass = new AnnotationConfigApplicationContext(BeanConfig.class);
        Greetable greetableBean = contextFromBeanConfigClass.getBean(Greetable.class);
        greetableBean.sayHello();

        ApplicationContext contextFromClasspathXml = new ClassPathXmlApplicationContext("/beanConfig.xml");
        SpringBeanNo1 beanFromXmlFromClassPath = contextFromClasspathXml.getBean(SpringBeanNo1.class);
        beanFromXmlFromClassPath.sayHello();

        String beansXmlLocationOnFilesystem = App.class.getResource("/beanConfig.xml").toExternalForm();
        ApplicationContext contextFromFileXml = new FileSystemXmlApplicationContext(beansXmlLocationOnFilesystem);
        SpringBeanNo1 beanFromFileXml = contextFromFileXml.getBean(SpringBeanNo1.class);
        beanFromFileXml.sayHello();

    }
}
