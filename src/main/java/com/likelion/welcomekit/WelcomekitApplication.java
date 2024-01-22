package com.likelion.welcomekit;

import com.likelion.welcomekit.Domain.Entity.ProjectSetting;
import com.likelion.welcomekit.Service.ProjectSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WelcomekitApplication implements CommandLineRunner {

	@Autowired
	private ProjectSettingService projectSettingService;

	public static void main(String[] args) {
		SpringApplication.run(WelcomekitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		projectSettingService.initialProjectSettingDB();
	}
}