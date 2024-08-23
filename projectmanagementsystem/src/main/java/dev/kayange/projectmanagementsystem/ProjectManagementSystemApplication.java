package dev.kayange.projectmanagementsystem;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@SpringBootApplication
@EnableAsync
public class ProjectManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementSystemApplication.class, args);
	}

	@PostConstruct
	void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
	}
}
