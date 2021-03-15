package ch.fhnw.apm.app;

import ch.fhnw.apm.app.storage.ClusterStorage;
import ch.fhnw.apm.app.storage.Storage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApmApp {

    public static void main(String[] args) {
        SpringApplication.run(ApmApp.class, args);
    }

    @Bean
    Storage storage() {
        return new ClusterStorage("apmKeyValueStore");
    }
}
