
package com.project03.spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private JdbcTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/users")
	public String select() throws Exception {
		String sql = "INSERT INTO users (username, password) VALUES ('REMOVE', 'THIS')";
		int rows = template.update(sql);
		System.out.println(rows);
		return String.format("Help %s", rows);
	}
	
	@DeleteMapping("/users")
	public String deleteUser(@RequestParam(value = "userId") String userId) {

		return "Delete user called for user ID " + userId;
	}

}
            