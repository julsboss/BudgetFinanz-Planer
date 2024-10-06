package mosbach.dhbw.de;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
@ComponentScan(basePackages = "mosbach.dhbw.de.mymonthlybudget")
public class TasksBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksBackendApplication.class, args);
    }

}
