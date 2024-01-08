package com.security.userauthenticationandauthorization;

import com.security.userauthenticationandauthorization.config.auth.AuthenticationService;
import com.security.userauthenticationandauthorization.config.auth.RegisterRequest;
import com.security.userauthenticationandauthorization.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.security.userauthenticationandauthorization.user.Role.ADMIN;
import static com.security.userauthenticationandauthorization.user.Role.MANAGER;

@SpringBootApplication
public class UserAuthenticationAndAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationAndAuthorizationApplication.class, args);
	}

		@Bean //used in creation of users with roles
		public CommandLineRunner commandLineRunner (AuthenticationService authenticationService){
			return args -> {
				var admin = RegisterRequest.builder()
						.firstname("ADMIN")
						.lastname("Admin")
						.email("admin@gmail.com")
						.password("password")
						.role(ADMIN)
						.build();
				//shd it be getToken or getAccessToken
				System.out.println("Admin Token: " + authenticationService.register(admin).getToken()); //token generatedby admin
//when app starts, we will have 2 users with 2 roles
				var manager = RegisterRequest.builder()
						.firstname("MANAGER")
						.lastname("Manager")
						.email("manager@gmail.com")
						.password("password")
						.role(MANAGER)
						.build();
				//shd it be getToken or getAccessToken
				System.out.println("Manager Token: " + authenticationService.register(manager).getToken());

			};
		}
	}


