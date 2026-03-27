package ma.youcode.regalis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RegalisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegalisApplication.class, args);
    }

}
