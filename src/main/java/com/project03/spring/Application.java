
package com.project03.spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@DeleteMapping("/users")
	public String deleteUser(@RequestParam(value = "userId") String userId) {
		// TODO: Ensure request has login attatched, delete user from database
		return "Delete user called for user ID " + userId;
	}

	@GetMapping("/")
	public String landing(){
		return String.format("Landing page for Project 03");
	}

	@PostMapping("/login")
	public String login(@RequestParam(value="user") String username, @RequestParam(value="pass") String password){
		// Query database for username and password; If valid,
		//TODO: Authenticate username/password and instantiate session
		return "Login called";
	}

}
            