package me.piepers.uploadexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class UploadExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadExampleApplication.class, args);
	}

}
