package org.hua.greece_coronavirus_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GreeceCoronavirusTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeceCoronavirusTrackerApplication.class, args);
    }

}
