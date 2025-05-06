package hoselabs.re_tap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReTapApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReTapApplication.class, args);
    }

}
