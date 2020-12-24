package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**This is the main class for starting the application
 * @author Akhil
 *
 */
@RestController
@SpringBootApplication
public class ApplicationStartUp {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStartUp.class, args);
	}

}
