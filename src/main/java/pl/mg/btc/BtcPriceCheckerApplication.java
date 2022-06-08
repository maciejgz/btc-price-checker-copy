package pl.mg.btc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BtcPriceCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtcPriceCheckerApplication.class, args);
    }

}
