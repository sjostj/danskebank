package dk.jost.danskebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("dk.jost.danskebank")
public class DanskebankApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanskebankApplication.class, args);
	}

}
